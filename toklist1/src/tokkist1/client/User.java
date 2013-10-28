package tokkist1.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A user of the Chattr application.
 */
public class User implements IsSerializable {
  public static User createUser(String name) {
    User user = new User();
    user.name = name;
    user.statusMessage = "I am the test.";
    return user;
  }

  private String name;

  private String statusMessage;

  /**
   * Only used by serialization
   */
  public User() {
  }

  /**
   * Constructs a {@link User} instance.
   * 
   * @param name user's name
   * @param statusMessage user's status message
   */
  public User(String name, String statusMessage) {
    this.name = name;
    this.statusMessage = statusMessage;
  }

  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (obj instanceof User) {
      User user = (User) obj;

      return name.equals(user.name);
    }

    return false;
  }

  /**
   * Returns the user's name.
   * 
   * @return user's name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the user's status message.
   * 
   * @return user's status message.
   */
  public String getStatusMessage() {
    return statusMessage;
  }

  public int hashCode() {
    return name.hashCode();
  }

  /**
   * Sets the user's status message.
   * 
   * @param statusMessage new value for the user's status message
   */
  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String toString() {
    return getName() + " - " + getStatusMessage();
  }
}
