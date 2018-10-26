package basic;

public class Check {
	public static boolean checkRoomInformation(RoomInformation r) {
		try {
			throw new CheckException();
		} catch(Exception e) {
			
		}
		
		return false;
	}

	public static boolean checkNullString(String str){
		if(str != null)
			return true;
		return false;
		
	}
}
