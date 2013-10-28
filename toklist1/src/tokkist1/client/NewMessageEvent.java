package tokkist1.client;

import java.util.List;

public class NewMessageEvent extends ChatEvent {
  private String message;
  
  /**
   * gwt.typeArgs <org.eclipsecon.gwt.chattr.client.User>
   */
  private List recipients;
  
  private User sender;

  /**
   * 
   */
  public NewMessageEvent() {
  }
  
  /**
   * @param sender
   * @param recipients
   * @param message
   */
  public NewMessageEvent(User sender, List recipients, String message) {
    this.sender = sender;
    this.recipients = recipients;
    this.message = message;
  }
  
  /**
   * 
   */
  public void accept(ChatEventVisitor visitor) {
    visitor.visit(this);
  }

  /**
   * 
   * @return
   */
  public String getMessage() {
    return message;
  }

  /**
   * @gwt.typeArgs <org.eclipsecon.gwt.chattr.client.User>
   * 
   * @return
   */
  public List getRecipients() {
    return recipients;
  }

  /**
   * 
   * @return
   */
  public User getSender() {
    return sender;
  }
}
