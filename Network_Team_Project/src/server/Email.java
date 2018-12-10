package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Email class
 * 
 * Email class check open room and send email to users. Email class has main
 * function, so if you want to send email, execute this class. You must to
 * import mail.jar file. This code from http://fruitdev.tistory.com/15.
 * 
 * @author Young
 *
 */
public class Email {

	Properties p;
	Authenticator auth;
	Session session;
	MimeMessage msg;
	InternetAddress to;
	InternetAddress from;
	String content;

	/**
	 * Constructor
	 */
	public Email() {

		p = System.getProperties();
		p.put("mail.smtp.starttls.enable", "true"); // gmail must fix true
		p.put("mail.smtp.host", "smtp.gmail.com"); // smtp server address
		p.put("mail.smtp.auth", "true"); // gmail must fix true
		p.put("mail.smtp.port", "587"); // gmail port

		auth = new MyAuthentication();

		// Session and MineMessage
		session = Session.getDefaultInstance(p, auth);
		msg = new MimeMessage(session);

	}

	public void sendEmail(String reciever, int roomNumber) {
		try {

			content = "Hello, \nYour room is opened today!\nCome back to R;E and remind your memory.\n" + "Your PIN: "
					+ roomNumber + "\nEnter to this room!";

			// Send date
			msg.setSentDate(new Date());

			from = new InternetAddress();
			from = new InternetAddress("test<test@gmail.com>");

			// Email sender
			msg.setFrom(from);

			// Email receiver
			to = new InternetAddress(reciever);
			msg.setRecipient(Message.RecipientType.TO, to);

			// Email title
			msg.setSubject("[R;E] Remind Your Memory", "UTF-8");

			// Email context
			msg.setText(content, "UTF-8");

			// Email header
			msg.setHeader("content-Type", "text/html");

			// Send email
			javax.mail.Transport.send(msg);

		} catch (AddressException addr_e) {
			addr_e.printStackTrace();
		} catch (MessagingException msg_e) {
			msg_e.printStackTrace();
		}
	}

	// repeat send email everyday
	public static void main(String[] args) {
		ScheduledJob job = new ScheduledJob();
		long day = 1000 * 60 * 60 * 24; // 24 hours
		Timer jobScheduler = new Timer();
		jobScheduler.scheduleAtFixedRate(job, 1000, day);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		jobScheduler.cancel();

	}
}

/**
 * ScheduledJob class
 * 
 * Thread repeat everyday to send email. Check opened room in database and send
 * mail to opened room user
 * 
 * @author Young
 *
 */
class ScheduledJob extends TimerTask {

	public void run() {
		Database db = new Database();
		Email emailSender = new Email();
		ResultSet emailSet;

		ResultSet roomSet = db.GetOpenRoomNumberSet();
		try {
			while (roomSet.next()) {
				emailSet = db.GetOpenRoomEmailSet(roomSet.getInt(1));
				while (emailSet.next()) {
					emailSender.sendEmail(emailSet.getString(1), roomSet.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

/**
 * MyAuthentication class
 * 
 * Please change id and pw to your gmail information
 * @author Young
 *
 */
class MyAuthentication extends Authenticator {

	PasswordAuthentication pa;

	public MyAuthentication() {

		String id = "chanyoung6477@gmail.com"; // google ID
		String pw = "gen#2218ps#1828"; // google Password

		// enter id and password
		pa = new PasswordAuthentication(id, pw);

	}
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}
