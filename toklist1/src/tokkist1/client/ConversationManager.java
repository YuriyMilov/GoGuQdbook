package tokkist1.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import tokkist1.client.Model.MessageEventHandler;

public class ConversationManager implements tokkist1.client.Model.MessageEventHandler, PopupListener {
  private static final int FOCUSED_ZINDEX = 1;
  private static final int NORMAL_ZINDEX = 0;

  private final Model model;
  private final HashMap /* <User,ConversationPanel> */conversations = new HashMap();

  private ConversationPanel focusedConversationPanel;

  public ConversationManager(Model model) {
    this.model = model;
    model.addEventHandler(this);
  }

  public Model getModel() {
    return model;
  }

  public User getUser() {
    return model.getUser();
  }

  public void setFocusedConversation(ConversationPanel panel) {
    if (focusedConversationPanel != null) {
      DOM.setStyleAttribute(focusedConversationPanel.getElement(), "zIndex",
          Integer.toString(NORMAL_ZINDEX));
    }

    DOM.setStyleAttribute(panel.getElement(), "zIndex",
        Integer.toString(FOCUSED_ZINDEX));
    focusedConversationPanel = panel;
  }

  public ConversationPanel maybeInitiateConversationPanel(User toUser) {
    final String key = toUser.getName();
    ConversationPanel panel = (ConversationPanel) conversations.get(key);
    if (panel == null) {
      panel = new ConversationPanel(this, toUser);
      conversations.put(key, panel);
      panel.addPopupListener(this);
      panel.show();
    }
    return panel;
  }

  public void onNewMessage(User sender, List recipient, String message) {
    MessageEventHandler handler = maybeInitiateConversationPanel(sender);
    handler.onNewMessage(sender, recipient, message);
  }

  public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
    ConversationPanel panel = (ConversationPanel) sender;
    for (Iterator it = panel.getRecipients().iterator();it.hasNext();) {
      User user = (User)it.next();
      conversations.remove(user.getName());
    }
  }

  public void sendMessage(List recipients, String message) {
    model.sendMessage(recipients, message);
  }
}
