package tokkist1.client;

public class UserUpdateEvent extends ChatEvent {
  private User user;

  public UserUpdateEvent() {
  }

  public UserUpdateEvent(User user) {
    this.user = user;
  }

  public void accept(ChatEventVisitor visitor) {
    visitor.visit(this);
  }

  public User getUser() {
    return user;
  }
}