package basic;
import java.util.Calendar;

public class RoomInformation {
	public String groupName;
	public int port;
	public int howManyPeople;
	public String securityQuestion;
	public String securityAnswer;
	public Calendar startDate;
	public Calendar endDate;
	
	
	public RoomInformation() {
		this.startDate = Calendar.getInstance();
	}
	public RoomInformation(String gN, int port, int people, String SQ, String SA, Calendar endDate) {
		super();
		this.groupName = gN;
		this.port = port;
		this.howManyPeople = people;
		this.securityAnswer = SA;
		this.securityQuestion = SQ;
		this.endDate = endDate;
	}
}


