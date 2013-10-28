package tokkist1.client;

/**
 * Chat event visitor. Provides a simple way to deal with all of the different
 * chat events without needing to perform lots of instance tests.
 */
public abstract class ChatEventVisitor {
  public abstract void visit(NewMessageEvent event);

  public abstract void visit(UserAddEvent event);

  public abstract void visit(UserRemoveEvent event);

  public abstract void visit(UserUpdateEvent event);
}
