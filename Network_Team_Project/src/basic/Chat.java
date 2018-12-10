package basic;

import java.io.Serializable;

/**
 * Chat class
 * 
 * Chat class will be used to send chat from client. Email and chat will
 * transfer by this class.
 * 
 * @author Young
 *
 */
public class Chat implements Serializable {
	public String email;
	public String name;
	public String message;

	/**
	 * isMyChat
	 * 
	 * Check this chat is mine or other's
	 * 
	 * @param myEmail
	 * @return return it is mine return true, else return false
	 */
	private boolean isMyChat(String myEmail) {
		if (email.equals(myEmail))
			return true;
		return false;
	}

	/**
	 * getMessage
	 * 
	 * Get converted chat to string. If chat is mine header will be 'Me', else head
	 * will be other user's name
	 * 
	 * @param myEmail
	 * @return String that converted chat
	 */
	public String getMessage(String myEmail) {
		if (isMyChat(myEmail))
			return "Me: " + message;
		return name + ": " + message;
	}

}
