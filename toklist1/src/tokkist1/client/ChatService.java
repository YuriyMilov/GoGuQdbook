package tokkist1.client;

import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

/**
 * Chat service interface.
 */
public interface ChatService extends RemoteService {
  /**
   * Exchange events with the chat service.
   * 
   * @gwt.typeArgs clientEvents <org.eclipsecon.gwt.chattr.client.ChatEvent>
   * @gwt.typeArgs <org.eclipsecon.gwt.chattr.client.ChatEvent>
   */
  List exchangeEvents(User user, List clientEvents) throws ChatServiceException;
  
  /**
   * Returns a list of the users that are available on the system.
   * 
   * @return list of users who are on the system
   * 
   * @gwt.typeArgs <org.eclipsecon.gwt.chattr.client.User>
   */
  List getCurrentUsers(User user);
}
