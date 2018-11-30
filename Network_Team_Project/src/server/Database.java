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

public class Database {
	Connection con = null;
	PreparedStatement userPS = null;
	PreparedStatement roomPS = null;

	Statement stmt = null;
	ResultSet rs = null;

	// public static void main(String[] args) {
	// Database redb = new Database();
	//
	// System.out.println(redb.InsertUserInfor("ChanYoung", "young221718@gmail.com",
	// "1234"));
	// System.out.println(redb.CheckPassword("young221718@gmail.com", "1234"));
	//
	// // try {
	// // //redb.con.commit();
	// // } catch (SQLException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// }

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

				String roomPSQL = "insert into room_information values(?,?,?,?,?,?,?);";
				roomPS = (PreparedStatement) con.prepareStatement(roomPSQL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ConnectDB 데이터베이스에 연결하는 메서드입니다.
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
	 * insertUserInfor 유저 정보를 데이터베이스에 추가하고 싶을 때 쓰면 되는 함수이다.
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
	 * GetRoomQuetion: 방 보안질문을 받아오는 메소드
	 * 
	 * @param roomNum
	 *            방 번호
	 * @return String 방 번호에 해당하는 질문이다.
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
	 * @param String
	 *            an user's answer
	 * @return return 1 if success, return -1 if fail, return -2 if sql error
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
	 * UpdateRoomNumber
	 * update room number add two 
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
	 * get room number from database
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
	 * @param roomNum
	 *            pin number
	 * @return room name (group name)
	 */
	public String GetRoomName(int roomNum) {
		try {
			stmt = (Statement) con.createStatement();
			String sql = "select group_name from room_information which group_id = " + roomNum;
			rs = stmt.executeQuery(sql);

			if (rs.next())
				return rs.getString(0);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * CheckRoomExist check if room is already exist in database
	 * 
	 * @param roomNum
	 *            room number which want to check
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

		}
		return true;
	}

	/**
	 * CheckPassword - 로그인을 하기 위한 메서드
	 * 
	 * @param email
	 *            - 이메일
	 * @param pw
	 *            - 비밀번호
	 * @return int // 1: correct password // 0: SQL error // -1: wrong password //
	 *         -2: not exist email
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
	 * InsertRoomInfor 방 정보를 데이터베이스에 추가함.
	 * 
	 * @param rf
	 *            - RoomInformation 클래스를 받아옴
	 * @return boolean - if success return true, else return false
	 */
	public boolean InsertRoomInfor(RoomInformation rf) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Integer maxPeople = rf.howManyPeople;

			Calendar temp = rf.startDate;
			temp.add(rf.startDate.DATE, 1);
			String id = ((Integer) rf.port).toString() + "." + sdf.format(temp.getTime()).substring(2);

			roomPS.setString(1, rf.groupName);
			roomPS.setString(2, id);
			roomPS.setString(3, df.format(rf.startDate.getTime()));
			roomPS.setString(4, df.format(rf.endDate.getTime()));
			roomPS.setString(5, maxPeople.toString());
			roomPS.setString(6, rf.securityQuestion);
			roomPS.setString(7, rf.securityAnswer);

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
	 * DisconnectDB 데이터 베이스와 연결을 끊는 메서드이다.
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
	 * CommitDB : 데이터베이스를 커밋한다.
	 */
	public void CommitDB() {
		try {

			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
