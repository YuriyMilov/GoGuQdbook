package tokkist1.client;

public class UserAddEvent extends ChatEvent {
  private User user;

  public UserAddEvent() {
  }

  public UserAddEvent(User user) {
    this.user = user;
  }

  public void accept(ChatEventVisitor visitor) {
    visitor.visit(this);
  }

  public User getUser() {
    return user;
  }
  
  public String toString() {
    return "UserAdd: " + user.toString();
  }
}
