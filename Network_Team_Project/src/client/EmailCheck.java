package client;

import java.util.regex.Pattern;


public class EmailCheck {
	public static void main(String[] args) {
		String mail;
		
//		if(isEmail(mail)  == true)
//			System.out.println("True");
//		else
//			System.out.println("False");
	}


	public static boolean isEmail(String email) {
		boolean check;
		
		if (email == null) //이메일이 입력되지 않았을 떄
			return false;
	
		check = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
		return check; //이메일이 올바른 형식 : true, 올바르지 않은 형식 : false 를 반환
	}
}