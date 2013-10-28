package tokkist1.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginPanel extends Composite implements ClickListener,
    KeyboardListener {

  public interface LoginHandler {
    public void onSuccess(User user);

    public void onFailure();
  }

  private final VerticalPanel panel = new VerticalPanel();
  private final LabelledTextBox userNameBox = new LabelledTextBox("Your name.");
  private final LabelledTextBox messageBox = new LabelledTextBox(
      "Your tagline.");
  private final Button loginButton = new Button("Login");
  private final LoginHandler handler;

  public LoginPanel(LoginHandler handler) {
    this.handler = handler;

    initWidget(panel);

    panel.add(userNameBox);
    panel.add(messageBox);
    panel.add(loginButton);

    setStyleName("chattr-LoginPanel");

    loginButton.addClickListener(this);
    userNameBox.addKeyboardListener(this);
    messageBox.addKeyboardListener(this);
  }

  private void authenticate() {
    String userName = userNameBox.getText();
    String message = messageBox.getText();
    
    if (userName == null) {
      handler.onFailure();
      return;
    }

    userName = userName.trim();
    if (userName.length() == 0) {
      handler.onFailure();
      return;
    }

    handler.onSuccess(new User(userName, message));
  }

  public void onClick(Widget sender) {
    authenticate();
  }

  public void onKeyDown(Widget sender, char keyCode, int modifiers) {
    if (keyCode == KeyboardListener.KEY_ENTER) {
      if (sender == userNameBox) {
        messageBox.setFocus(true);
      } else {
        authenticate();
      }
    }
  }

  public void onKeyPress(Widget sender, char keyCode, int modifiers) {
  }

  public void onKeyUp(Widget sender, char keyCode, int modifiers) {
  }

  public int getTabIndex() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void setFocus(boolean focus) {
    userNameBox.setFocus(focus);
  }
}
