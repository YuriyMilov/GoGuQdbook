package tokkist1.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Asynchronous chat service interface. 
 */
public interface ChatServiceAsync {
  void exchangeEvents(User user, List clientEvents, AsyncCallback callback);
  
  void getCurrentUsers(User user, AsyncCallback callback);
}
