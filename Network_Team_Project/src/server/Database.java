package server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import basic.RoomInformation;

/**
 * Database Class
 * 
 * This class will be control database. Every work of database operation will be
 * controlled in this class
 * 
 * @author Young
 *
 */
public class Database {
	Connection con = null;
	PreparedStatement userPS = null;
	PreparedStatement roomPS = null;
	Statement stmt = null;
	ResultSet rs = null;

	/**
	 * constructor
	 */
	public Database() {
		try {
			ConnectDB();
			synchronized (con) {
				con.setAutoCommit(false);
				String userPSQL = "insert into user_information(user_name,email,password) values(?, ?, ?);";
				userPS = (PreparedStatement) con.prepareStatement(userPSQL);

				String roomPSQL = "insert into room_information values(?,?,?,?,?,?,?,?);";
				roomPS = (PreparedStatement) con.prepareStatement(roomPSQL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ConnectDB
	 *
	 * Connect java to database
	 */
	public void ConnectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/re_db";
			String user = "root", passwd = "12345";
			con = (Connection) DriverManager.getConnection(url, user, passwd);

			System.out.println(con);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * InsertUserInfor
	 * 
	 * Insert user information in user information relation
	 * 
	 * @param name
	 *            - user's name
	 * @param email
	 *            - user's email
	 * @param pw
	 *            - user's password
	 * @return int // 1: success // -1: already exist // 0: fail: sql error
	 */
	public int InsertUserInfor(String name, String email, String pw) {

		try {
			// check if email is already existed
			stmt = (Statement) con.createStatement();
			String sql = "select * from user_information where email ='" + email + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return -1; // already exist
			}

			// insert user information
			userPS.setString(1, name);
			userPS.setString(2, email);
			userPS.setString(3, pw);

			int count = userPS.executeUpdate();
			if (count != 1) {
				System.out.println(count);
				return -2;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -2; // fail: sql error
		}
		return 1; // success
	}

	/**
	 * GetUserName
	 * 
	 * get user's name from database to use email
	 * 
	 * @param email
	 *            user's email
	 * @return user's name
	 */
	public String GetUserName(String email) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select user_name from user_information where email ='" + email + "'";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				return rs.getString(1); // return question
			} else {
				return null; // not exist room
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * GetRoomQuestion
	 * 
	 * Get room question search by room number
	 * 
	 * @param roomNum
	 * @return String the room question
	 */
	public String GetRoomQuestion(int roomNum) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select question from room_information where group_id =" + roomNum;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				return rs.getString(1); // return question
			} else {
				return null; // not exist room
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * CheckRoomAnswer
	 * 
	 * Check the specific room's answer is correct or not
	 * 
	 * @param String
	 *            - an user's answer
	 * @return return 1 if correct, return -1 if wrong, return -2 if sql error
	 */
	public int CheckRoomAnswer(String an, int roomNum) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select * from room_information where answer ='" + an + "' and group_id =" + roomNum;
			rs = stmt.executeQuery(sql);

			if (rs.next())
				return 1; // success
			else
				return -1; // fail
		} catch (SQLException e) {
			return -2; // sql error
		}
	}

	/**
	 * InsertRoomUser
	 * 
	 * Insert to room user relation with user's email and room number. email and
	 * room number combination will be unique
	 * 
	 * @param roomNumber
	 * @param email
	 * @return if success return 1, already exist return -1, else return 0
	 */
	public int InsertRoomUser(int roomNumber, String email) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "insert into room_user values('" + email + "'," + roomNumber + ")";
			int cnt = stmt.executeUpdate(sql);

			if (cnt == -1) {
				return -1;
			} else if (cnt == 1) {
				return 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * UpdateCurPeople
	 * 
	 * update current people in room information in database. if new user enter the
	 * room plus 1 to current people
	 * 
	 * @param roomNumber
	 * @return success return 1, else return -1
	 */
	public int UpdateCurPeople(int roomNumber) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "update room_information set cur_people = cur_people + 1 where group_id = " + roomNumber;
			int cnt = stmt.executeUpdate(sql);

			if (cnt == 1)
				return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * IsPosiibleEnterRoom
	 * 
	 * Check if specific room can be entered or not
	 * 
	 * @param roomNumber
	 * @return if possible return true, else return false
	 */
	public boolean IsPossibleEnterRoom(int roomNumber) {
		try {
			System.out.println("test2");
			stmt = (Statement) con.createStatement();
			String sql = "select cur_people, max_people from room_information where group_id =" + roomNumber;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				if (Integer.parseInt(rs.getString(1)) < Integer.parseInt(rs.getString(2)))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * IsAlreadyUser
	 * 
	 * Check if user already enter the room or not.
	 * 
	 * @param roomNumber
	 * @param email
	 * @return yes return true else return false
	 */
	public boolean IsAlreadyUser(int roomNumber, String email) {
		try {
			System.out.println("for test");
			stmt = (Statement) con.createStatement();
			String sql = "select * from room_user where group_id = " + roomNumber + " and email = '" + email + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			boolean temp = rs.next();
			System.out.println("is already user?: " + temp);
			return temp;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * UpdateRoomNumber
	 * 
	 * update room number add two
	 * 
	 * @return success return 1, else 0 or -1
	 */
	public int UpdateRoomNumber() {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "update room_number set number = number + 2";
			return stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * GetRoomNumber
	 * 
	 * get room number from database
	 * 
	 * @return if success return PIN, else return 01;
	 */
	public int GetRoomNumber() {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select number from room_number";
			rs = stmt.executeQuery(sql);

			if (rs.next())
				return rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * GetRoomName
	 * 
	 * Get room name from database.
	 * 
	 * @param roomNum
	 *            - pin number
	 * @return room name (group name)
	 */
	public String GetRoomName(int roomNum) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select group_name from room_information where group_id = " + roomNum;
			rs = stmt.executeQuery(sql);

			if (rs.next())
				return rs.getString(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * CheckRoomExist
	 * 
	 * check if room is already exist in database
	 * 
	 * @param roomNum
	 *            - room number which want to check
	 * @return if exist return true, else return false
	 */
	public boolean CheckRoomExist(int roomNum) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select * from room_information where group_id =" + roomNum;
			rs = stmt.executeQuery(sql);

			if (!rs.next())
				return false; // not exist

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * CheckPassword
	 * 
	 * Check password is match with email
	 * 
	 * @param email
	 * @param pw
	 *            - password
	 * @return if correct password return 1, else if SQL error return 0, else if
	 *         wrong password return -1 else if not exist email return -2
	 */
	public int CheckPassword(String email, String pw) {

		try {
			stmt = (Statement) con.createStatement();
			String sql = "select password from user_information where email ='" + email + "'";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				if (rs.getString(1).equals(pw))
					return 1; // correct password
				else
					return -1; // wrong password
			} else
				return -2; // not exist email
		} catch (SQLException e) {
			e.printStackTrace();
			return 0; // SQL error
		}
	}

	/**
	 * InsertRoomInfor
	 *
	 * Insert room information to database room_information relation
	 * 
	 * @param rf
	 *            - RoomInformation class
	 * @return boolean - if success return true, else return false
	 */
	public boolean InsertRoomInfor(RoomInformation rf) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Integer maxPeople = rf.howManyPeople;

			Calendar temp = rf.startDate;
			temp.add(Calendar.DATE, 1);

			roomPS.setString(1, rf.groupName);
			roomPS.setInt(2, rf.port);
			roomPS.setString(3, df.format(rf.startDate.getTime()));
			roomPS.setString(4, df.format(rf.endDate.getTime()));
			roomPS.setInt(5, maxPeople);
			roomPS.setString(6, rf.securityQuestion);
			roomPS.setString(7, rf.securityAnswer);
			roomPS.setInt(8, 0);

			int count = roomPS.executeUpdate();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * IsSender
	 * 
	 * Check if room is closed or not
	 * 
	 * @param roomNumber
	 * @return if room is open room return true else return false
	 */
	public boolean IsSender(int roomNumber) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select * from room_information where end_date <= date(now()) and group_id = " + roomNumber;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				System.out.println(rs.getInt(2));
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * GetOpenRoomNumberSet
	 * 
	 * Get room number that is opened. Return opened room set
	 * 
	 * @return ResultSet
	 */
	public ResultSet GetOpenRoomNumberSet() {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select group_id from room_information where end_date = date(now())";
			rs = stmt.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * GetOpenRoomEmailSet
	 * 
	 * Get email information who opened room user. Return email set.
	 * 
	 * @param roomNumber
	 * @return ResultSet
	 */
	public ResultSet GetOpenRoomEmailSet(int roomNumber) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select email from room_user where group_id = " + roomNumber;
			rs = stmt.executeQuery(sql);

			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DisconnectDB
	 * 
	 * disconnect database
	 */
	public void DisconnectDB() {

		try {
			if (con != null && !con.isClosed())
				con.close();
			if (userPS != null && !userPS.isClosed())
				userPS.close();
			if (roomPS != null && !roomPS.isClosed())
				roomPS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * CommitDB
	 * 
	 * commit database
	 */
	public void CommitDB() {
		try {

			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
