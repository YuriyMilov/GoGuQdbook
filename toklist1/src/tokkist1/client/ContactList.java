package tokkist1.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;

public class ContactList extends Composite implements tokkist1.client.Model.UserEventHandler {

  private class UserEntry extends FlowPanel {
    final Label nameLabel = new Label();
    final Label statusLabel = new Label();
    User user;

    UserEntry(User user) {
      add(nameLabel);
      add(statusLabel);

      updateUser(user);

      setStyleName("entry");
      nameLabel.setStyleName("entry-name");
      statusLabel.setStyleName("entry-status");

      sinkEvents(Event.ONCLICK);
    }

    void updateUser(User newUser) {
      user = newUser;
      update();
    }

    void update() {
      nameLabel.setText(user.getName());
      statusLabel.setText(user.getStatusMessage());
    }

    public void onBrowserEvent(Event event) {
      switch (DOM.eventGetType(event)) {
        case Event.ONCLICK:
          manager.setFocusedConversation(manager.maybeInitiateConversationPanel(
              user));
      }
    }
  }

  final VerticalPanel topPanel = new VerticalPanel();
  final FlowPanel userPanel = new FlowPanel();
  final Label label = new Label("Contacts");
  final ConversationManager manager;

  public ContactList(ConversationManager manager) {
    initWidget(topPanel);
    this.manager = manager;

    topPanel.add(label);
    topPanel.add(userPanel);

    label.setStyleName("header");
    setStyleName("chattr-ContactList");

    setPosition(2, 2);
    DOM.setStyleAttribute(getElement(), "position", "fixed");

    label.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        userPanel.setVisible(!userPanel.isVisible());
      }
    });

    manager.getModel().addEventHandler(this);

    addSelf();
  }

  private UserEntry findEntry(User user) {
    for (Iterator it = userPanel.iterator(); it.hasNext();) {
      UserEntry entry = (UserEntry) it.next();
      if (entry.user.getName().equals(user.getName())) {
        return entry;
      }
    }
    return null;
  }

  private void addSelf() {
    UserEntry entry = new UserEntry(manager.getUser());
    entry.addStyleName("self");
    userPanel.add(entry);
  }
  
  private void addUser(User user) {
    if (findEntry(user) == null) {
      userPanel.add(new UserEntry(user));
    }
  }

  private void removeUser(User user) {
    UserEntry entry = findEntry(user);
    if (entry != null) {
      userPanel.remove(entry);
    }
  }

  private void updateUser(User user) {
    UserEntry entry = findEntry(user);
    if (entry != null) {
      entry.updateUser(user);
    }
  }

  public void setPosition(int left, int top) {
    final Element elem = getElement();
    DOM.setStyleAttribute(elem, "top", top + "px");
    DOM.setStyleAttribute(elem, "left", left + "px");
  }

  public void onUserAdded(User user) {
    addUser(user);
  }

  public void onUserRemoved(User user) {
    removeUser(user);
  }

  public void onUserUpdated(User user) {
    updateUser(user);
  }
}
