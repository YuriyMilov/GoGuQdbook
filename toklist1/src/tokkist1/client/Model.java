package tokkist1.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final public class Model {
  public interface MessageEventHandler {
    public void onNewMessage(User sender, List recipients, String message);
  }

  public interface UserEventHandler {
    public void onUserAdded(User user);

    public void onUserRemoved(User user);

    public void onUserUpdated(User user);
  }

  /**
   * Visitor for processing {@link ChatEvent}s.
   */
  private final class ModelUpdateVisitor extends ChatEventVisitor {
    public void visit(NewMessageEvent event) {
      fireNewMessageEvent(event.getSender(), event.getRecipients(),
          event.getMessage());
    }

    public void visit(UserAddEvent event) {
      fireUserAddEvent(event.getUser());
    }

    public void visit(UserRemoveEvent event) {
      fireUserRemoveEvent(event.getUser());
    }

    public void visit(UserUpdateEvent event) {
      fireUserUpdateEvent(event.getUser());
    }
  }

  private final class PollTimer extends Timer {
    public void run() {
      service.exchangeEvents(user, outgoingEvents, new AsyncCallback() {
        public void onFailure(Throwable caught) {
          pollTimer.schedule(DEFAULT_POLLING_MILLIS);
        }

        public void onSuccess(Object result) {
          update((List) result);
          pollTimer.schedule(DEFAULT_POLLING_MILLIS);
        }
      });
      outgoingEvents.clear();
    }
  }

  private static final int DEFAULT_POLLING_MILLIS = 1000;

  private static ChatServiceAsync getService() {
    ChatServiceAsync service = (ChatServiceAsync) GWT.create(ChatService.class);

    ((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL()
        + "chattr");

    return service;
  }

  private final Timer pollTimer = new PollTimer();

  private ChatServiceAsync service = getService();

  private final User user;
  private final HandlerCollection /* <UserEventHandler> */userEventHandlers = new HandlerCollection();
  private final HandlerCollection /* <MessageEventHandler> */messageEventHandlers = new HandlerCollection();
  private final ArrayList /* <ChatEvent> */outgoingEvents = new ArrayList();
  
  public Model(User user) {
    this.user = user;

    service.getCurrentUsers(user, new AsyncCallback() {
      public void onFailure(Throwable caught) {
        pollTimer.schedule(DEFAULT_POLLING_MILLIS);
      }

      public void onSuccess(Object result) {
        List users = (List) result;
        Iterator iter = users.iterator();
        // TODO clean this up after the event refactoring
        // HACK
        while (iter.hasNext()) {
          User user = (User) iter.next();
          
          fireUserAddEvent(user);
        }
        
        pollTimer.schedule(DEFAULT_POLLING_MILLIS);
      }
    });
    
//    pollTimer.schedule(DEFAULT_POLLING_MILLIS);    
  }

  public void addEventHandler(UserEventHandler handler) {
    userEventHandlers.add(handler);
  }

  public void addEventHandler(MessageEventHandler handler) {
    messageEventHandlers.add(handler);
  }
  
  public User getUser() {
    return user;
  }
  
  public void sendMessage(List recipients, String message) {
    outgoingEvents.add(new NewMessageEvent(user, recipients, message));
  }
  
  public void removeEventHandler(UserEventHandler handler) {
    userEventHandlers.remove(handler);
  }

  public void removeEventHandler(MessageEventHandler handler) {
    messageEventHandlers.remove(handler);
  }

  public void update(List serverEvents) {
    ChatEventVisitor visitor = new ModelUpdateVisitor();
    Iterator iter = serverEvents.iterator();
    while (iter.hasNext()) {
      ChatEvent chatEvent = (ChatEvent) iter.next();

      chatEvent.accept(visitor);
    }
  }
  
  private void fireNewMessageEvent(User sender, List recipients,
      String message) {
    for (Iterator it = messageEventHandlers.iterator(); it.hasNext();) {
      ((MessageEventHandler) it.next()).onNewMessage(sender, recipients,
          message);
    }
  }

  private void fireUserAddEvent(User user) {
    for (Iterator it = userEventHandlers.iterator(); it.hasNext();) {
      ((UserEventHandler) it.next()).onUserAdded(user);
    }
  }

  private void fireUserRemoveEvent(User user) {
    for (Iterator it = userEventHandlers.iterator(); it.hasNext();) {
      ((UserEventHandler) it.next()).onUserRemoved(user);
    }
  }

  private void fireUserUpdateEvent(User user) {
    for (Iterator it = userEventHandlers.iterator(); it.hasNext();) {
      ((UserEventHandler) it.next()).onUserUpdated(user);
    }
  }  
}
