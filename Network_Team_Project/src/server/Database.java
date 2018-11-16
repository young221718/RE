package server;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Database {
	Connection con = null;
	PreparedStatement userPS = null;

	public static void main(String[] args) {
		Database redb = new Database();

		// System.out.println(redb.insertUserInfor("ChanYoung",
		// "young221718@gmail.com"));
		try {
			redb.con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * constructor
	 */
	public Database() {
		try {
			ConnectDB();
			con.setAutoCommit(false);
			String userPSQL = "insert into user_information values(?, ?);";
			userPS = (PreparedStatement) con.prepareStatement(userPSQL);
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
	 *            : user's name
	 * @param email
	 *            : user's email
	 * @return if success return true, else return false
	 */
	public boolean insertUserInfor(String name, String email) {

		try {
			userPS.setString(1, name);
			userPS.setString(2, email);

			int count = userPS.executeUpdate();
			if (count != 2) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
