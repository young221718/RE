package basic;

import java.io.Serializable;

public class Chat implements Serializable {
	public String email;
	public String name;
	public String message;

	private boolean isMyChat(String myEmail) {
		if (email.equals(myEmail))
			return true;
		return false;
	}

	public String getMessage(String myEmail) {
		if (isMyChat(myEmail))
			return "Me: " + message;
		return name + ": " + message;
	}

}
