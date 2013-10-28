package tokkist1.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConversationPanel extends DialogBox implements tokkist1.client.Model.MessageEventHandler {

  private static class MessagePanel extends SimplePanel {
    private Element tbody;

    MessagePanel() {
      super(DOM.createTable());
      Element table = getElement();

      DOM.setAttribute(table, "cellPadding", "0");
      DOM.setAttribute(table, "cellSpacing", "0");

      tbody = DOM.createTBody();

      DOM.appendChild(getElement(), tbody);
    }

    Element createRow(String sender, String message) {
      Element tr = DOM.createTR();
      Element ltd = DOM.createTD();
      Element rtd = DOM.createTD();

      DOM.appendChild(tr, ltd);
      DOM.appendChild(tr, rtd);

      DOM.setInnerText(ltd, sender);
      DOM.setInnerText(rtd, message);

      DOM.setAttribute(ltd, "className", "sender");
      DOM.setAttribute(rtd, "className", "message");

      return tr;
    }

    void addMessage(User sender, String message, String styleName) {
      Element row = createRow(sender.getName(), message);
      DOM.setAttribute(row, "className", styleName);
      DOM.appendChild(tbody, row);
    }
  }

  private final static String MESSAGE_PANEL_HEIGHT = "200px";
  private final static String MESSAGE_PANEL_WIDTH = "300px";
  private final static String STYLENAME_SENT_MESSAGE = "sent";
  private final static String STYLENAME_RECEIVED_MESSAGE = "received";

  private final static Element htmlCleaner = DOM.createSpan();
  
  private final VerticalPanel panel = new VerticalPanel();
  private final MessagePanel messagePanel = new MessagePanel();
  private final ScrollPanel messageScroll = new ScrollPanel(messagePanel);
  private final TextBox entryBox = new TextBox();
  private final Hyperlink closeLink = new Hyperlink("close", "");
  private final List recipients;
  private final ConversationManager manager;

  private static String cleanText(String text) {
    DOM.setInnerText(htmlCleaner, text);
    return DOM.getInnerText(htmlCleaner);
  }
  
  private static List wrapInList(User user) {
    final ArrayList list = new ArrayList();
    list.add(user);
    return list;
  }

  public ConversationPanel(ConversationManager manager, final User recipient) {
    this(manager, wrapInList(recipient));
  }

  public ConversationPanel(final ConversationManager manager,
      final List recipients) {
    this.recipients = recipients;
    this.manager = manager;

    setWidget(panel);

    SimplePanel cmdPanel = new SimplePanel();

    panel.add(cmdPanel);
    panel.add(messageScroll);
    panel.add(entryBox);

    setHTML("<span>" + cleanText(createTitle()) + "</span>");

    cmdPanel.setStyleName("commands");
    cmdPanel.add(closeLink);

    messageScroll.setHeight(MESSAGE_PANEL_HEIGHT);
    messageScroll.setWidth(MESSAGE_PANEL_WIDTH);

    setStyleName("chattr-Conversation");
    messageScroll.setStyleName("chattr-MessagePanel");
    panel.setStyleName("content");

    sinkEvents(Event.ONMOUSEDOWN);

    entryBox.addKeyboardListener(new KeyboardListener() {
      public void onKeyDown(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyPress(Widget sender, char keyCode, int modifiers) {
      }

      public void onKeyUp(Widget sender, char keyCode, int modifiers) {
        if (keyCode == KeyboardListener.KEY_ENTER) {
          final String message = entryBox.getText();
          if (message.trim().length() > 0) {
            manager.sendMessage(recipients, message);
            messagePanel.addMessage(manager.getUser(), message,
                STYLENAME_SENT_MESSAGE);
            adjustScroll();
          }
          entryBox.setText("");
        }
      }
    });

    closeLink.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        hide();
      }
    });
  }

  private String createTitle() {
    final StringBuffer buffer = new StringBuffer();
    final Iterator it = recipients.iterator();
    if (it.hasNext()) {
      buffer.append(((User) it.next()).getName());
    }

    while (it.hasNext()) {
      buffer.append(", ");
      buffer.append(((User) it.next()).getName());
    }

    return buffer.toString();
  }

  public List getRecipients() {
    return recipients;
  }

  private void adjustScroll() {
    DeferredCommand.add(new Command() {
      public void execute() {
        messageScroll.setScrollPosition(messagePanel.getOffsetHeight()
            - messageScroll.getOffsetHeight());        
      }      
    });
  }
  
  public void onNewMessage(User sender, List recipients, String message) {
    messagePanel.addMessage(sender, message, STYLENAME_RECEIVED_MESSAGE);
    adjustScroll();
  }

  public void onBrowserEvent(Event event) {
    switch (DOM.eventGetType(event)) {
      case Event.ONMOUSEDOWN:
        manager.setFocusedConversation(this);
    }
    super.onBrowserEvent(event);
  }

  public boolean onEventPreview(Event event) {
    switch (DOM.eventGetType(event)) {
      case Event.ONMOUSEDOWN:
        Element target = DOM.eventGetTarget(event);
        if (!DOM.isOrHasChild(panel.getElement(), target)) {
          DOM.eventPreventDefault(event);
        }
    }
    return true;
  }
}
