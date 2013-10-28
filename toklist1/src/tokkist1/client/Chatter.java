package tokkist1.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Chatter implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    final RootPanel rootPanel = RootPanel.get();
    final LoginPanel loginPanel = new LoginPanel(new LoginPanel.LoginHandler() {
      public void onFailure() {
      }

      public void onSuccess(User user) {
        rootPanel.clear();
        rootPanel.add(new ContactList(new ConversationManager(new Model(user))));
      }
    });
    rootPanel.add(loginPanel);
    loginPanel.setFocus(true);
  }
}
