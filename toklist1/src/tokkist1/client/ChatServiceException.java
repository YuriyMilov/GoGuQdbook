package tokkist1.client;

import com.google.gwt.user.client.rpc.SerializableException;

/**
 * Base class for exceptions thrown by the chat service method.
 */
public class ChatServiceException extends SerializableException {

  public ChatServiceException() {
  }
  
  public ChatServiceException(String string) {
    super(string);
  }
}
