package tokkist1.client;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LabelledTextBox extends TextBox implements FocusListener {
  private final String labelText;
  private boolean hasUserText = false;

  public LabelledTextBox(String labelText) {
    this.labelText = labelText;

    setText(labelText);

    setActive(false);
    
    addFocusListener(this);
  }

  private void setActive(boolean isActive) {
    final String styleName = "inactive";
    if (isActive) {
      removeStyleName(styleName);
    } else {
      addStyleName(styleName);
    }
  }
  
  public void onFocus(Widget sender) {
    if (!hasUserText) {
      setText("");
    }
    setActive(true);
  }

  public void onLostFocus(Widget sender) {
    String text = getText();
    hasUserText = (text.length() > 0);    
    if (!hasUserText) {
      setText(labelText);
    }
    setActive(hasUserText);
  }
}
