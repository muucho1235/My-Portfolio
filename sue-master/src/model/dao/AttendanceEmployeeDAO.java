package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


 //出退勤時刻管理データベースと繋ぐDAOクラス。
public class AttendanceEmployeeDAO {
	//唯一のインスタンスを生成する。
	private static AttendanceEmployeeDAO instance = new AttendanceEmployeeDAO();
	// 特定のデータベースとの接続(セッション)。
	private Connection con;
	//静的SQL文を実行し、作成された結果を返すために使用されるオブジェクト。
	private Statement st;
	
	 //"HH:mm:ss"のフォーマットで表記。
	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
	 //"yyyy-MM-dd"のフォーマットで表記。
	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private AttendanceEmployeeDAO() {
	}

	 //唯一のインスタンスを取得する。
	public static AttendanceEmployeeDAO getInstance() {
		return instance;
	}

	 //特定のデータベースとの接続(セッション)を生成する。 
	public void dbConnect() throws SQLException {
		ConnectionManager cm = ConnectionManager.getInstance();
		con = cm.connect();
	}

	 //静的SQL文を実行し、作成された結果を返すために使用されるオブジェクトを生成する。
	public void createSt() throws SQLException {
		st = con.createStatement();
	}

	//特定のデータベースとの接続(セッション)を切断する。
	public void dbDiscon() {
		try {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	 //指定されたemployeeCodeとpasswordから従業員がログインできるかどうかチェックする。
	public String loginEmployee(String employeeCode, String password) throws SQLException {
		String sql = "select * from employee where employee_code='"
				+ employeeCode + "' and password='" + password + "';";
		ResultSet rs = st.executeQuery(sql);
		if (rs.next()) {
			if (employeeCode.equals(rs.getString(1))) {
				if (password.equals(rs.getString(10))) {
					return employeeCode;
				}
			}
		}
		return null;
	}

	 //タイムカード出勤時間をテーブルに記録する。
	public boolean setStartTime(String employeeCode) throws SQLException {
		con.setAutoCommit(false);
		LocalDateTime now = LocalDateTime.now();
		//既にその日のデータが追加されていたらfalseを返す
		String sql = "SELECT * from work_time WHERE employee_code = '" + employeeCode
				+ "' and work_date = '" + now.format(dateFormat) + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			return false;
		} else {
			sql = "INSERT INTO work_time (employee_code, work_date, start_time) VALUES ('"
			+ employeeCode + "', '" + now.format(dateFormat) + "', '"
			+ now.format(timeFormat) + "' );";
			st.executeUpdate(sql);
			con.commit();
			return true;
		}
	}

	 //タイムカード退勤時間をテーブルに記録する。
	public boolean setFinishTime(String employeeCode) throws SQLException {
		con.setAutoCommit(false);
		LocalDateTime now = LocalDateTime.now();
		//出勤が押されていなかったらfalseを返す
		String sql = "SELECT * from work_time WHERE employee_code = '" + employeeCode
				+ "' and work_date = '" + now.format(dateFormat) + "';";
		ResultSet rs = st.executeQuery(sql);
		if(!rs.next()) {
			return false;
		} else {
			sql = "UPDATE work_time SET finish_time = '" + now.format(timeFormat)
					+ "' WHERE employee_code = '" + employeeCode + "' and work_date = '" + now.format(dateFormat) + "';";
			st.executeUpdate(sql);
			con.commit();
			return true;
		}
	}

	 //タイムカード休憩開始時間をテーブルに記録する。
	public boolean setStartBreakTime(String employeeCode) throws SQLException {
		con.setAutoCommit(false);
		LocalDateTime now = LocalDateTime.now();
		//出勤が押されていなかったらfalseを返す
		String sql = "SELECT * from work_time WHERE employee_code = '" + employeeCode
				+ "' and work_date = '" + now.format(dateFormat) + "';";
		ResultSet rs = st.executeQuery(sql);
		if(!rs.next()) {
			return false;
		} else {
			sql = "UPDATE work_time SET break_start_time = '" + now.format(timeFormat)
					+ "' WHERE employee_code = '" + employeeCode + "' and work_date = '" + now.format(dateFormat) + "';";
			st.executeUpdate(sql);
			con.commit();
			return true;
		}
	}

	//タイムカード休憩終了時間をテーブルに記録する。
	public boolean setFinishBreakTime(String employeeCode) throws SQLException {
		con.setAutoCommit(false);
		LocalDateTime now = LocalDateTime.now();
		//出勤または休憩開始が押されていなかったらfalseを返す
		String sql = "SELECT * from work_time WHERE employee_code = '" + employeeCode
				+ "' and work_date = '" + now.format(dateFormat) + "';";
		ResultSet rs = st.executeQuery(sql);
		if(!rs.next() && rs.getString(5) == null) {
			return false;
		} else {
			sql = "UPDATE work_time SET break_finish_time = '" + now.format(timeFormat)
					+ "' WHERE employee_code = '" + employeeCode + "' and work_date = '" + now.format(dateFormat) + "';";
			st.executeUpdate(sql);
			con.commit();
			return true;
		}
	}

}

