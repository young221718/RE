package basic;
import java.io.Serializable;
import java.util.Calendar;

public class RoomInformation implements Serializable{
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
	public void print() {
		
		
		System.out.println();
		System.out.println("=========================================================");
		System.out.println("Group Name: " + groupName);
		System.out.println("Port: " + port);
		System.out.println("People: " + howManyPeople);
		System.out.println("EndDate: " + endDate.get(Calendar.YEAR) +"-"+endDate.get(Calendar.MONTH)+"-"+endDate.get(Calendar.DATE));
		System.out.println("Question: " + securityQuestion);
		System.out.println("Answer: " + securityAnswer);
		System.out.println("=========================================================");
		System.out.println();
	}
}


