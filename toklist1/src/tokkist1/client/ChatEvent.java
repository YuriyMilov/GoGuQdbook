package tokkist1.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Base class for chat events
 */
public abstract class ChatEvent implements IsSerializable {

  /**
   * Accepts a ChatEventVisitor instance.  This method allows the different
   * event object to participate in a Visitor patter.
   * 
   * @param visitor
   */
  public abstract void accept(ChatEventVisitor visitor);
}