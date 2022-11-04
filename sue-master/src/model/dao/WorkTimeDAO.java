package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import model.entity.WorkTime;

//画面表示のために出退勤時刻管理データベースと繋ぐDAOクラス。
public class WorkTimeDAO {
	//唯一のインスタンスを生成する
	private static WorkTimeDAO instance = new WorkTimeDAO(); //唯一のインスタンスとする
	//特定のデータベースとの接続(セッション)。
	private Connection con;
	//静的SQL文を実行し、作成された結果を返すために使用されるオブジェクト。
	private Statement st;
	//privateのため新規のインスタンスをつくらせない。
	private WorkTimeDAO() {
	}
	//唯一のインスタンスを取得する。
	public static WorkTimeDAO getInstance() {
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

	//出勤情報が既に存在しているかチェックする。
	public String selectStartTime(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			return "disable";
		} else {
			return null;
		}
	}

	// 退勤情報が既に存在しているかチェックする。
	public String selectFinishTime(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next() && rs.getString(4) != null) {
			return "disable";
		} else {
			return null;
		}
	}

	//休憩開始情報が既に存在しているかチェックする。
	public String selectStartBreak(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next() && rs.getString(5) != null) {
			return "disable";
		} else {
			return null;
		}
	}

	//休憩終了情報が既に存在しているかチェックする。
	public String selectFinishBreak(String employeeCode) throws SQLException {
		String sql = "SELECT * FROM work_time WHERE employee_code = '" + employeeCode +
				"' AND work_date = '" + LocalDate.now() + "';";
		ResultSet rs = st.executeQuery(sql);
		if(rs.next() && rs.getString(6) != null) {
			return "disable";
		} else {
			return null;
		}
	}

	//従業員コードと月から勤務記録を抽出する。
	public List<WorkTime> selectWorkTimeThisMonthList(String employeeCode,String thisMonth)
			throws SQLException {
		List<WorkTime> workTimeThisMonthList = new LinkedList<WorkTime>();
		String sql = "SELECT * FROM work_time WHERE employee_code = '" + employeeCode +
		"' AND work_date LIKE '" + thisMonth + "%';";
		ResultSet rs = st.executeQuery(sql);

		while(rs.next()){
			WorkTime workTime = new WorkTime();
			workTime.setWorkDate(LocalDate.parse(rs.getString(2),
					DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			if(rs.getString(3) != null) {
				LocalTime startTime = LocalTime.parse(rs.getString(3), dtf);
				workTime.setStartTime(startTime);
			}
			if(rs.getString(4) != null) {
				LocalTime finishTime = LocalTime.parse(rs.getString(4), dtf);
				workTime.setFinishTime(finishTime);
			}
			if(rs.getString(5) != null) {
				LocalTime breakStartTime = LocalTime.parse(rs.getString(5), dtf);
				workTime.setBreakStartTime(breakStartTime);
			}
			if(rs.getString(6) != null) {
				LocalTime breakFinishTime = LocalTime.parse(rs.getString(6), dtf);
				workTime.setBreakFinishTime(breakFinishTime);
			}
			if(rs.getString(5) != null && rs.getString(6) != null) {
				//自動計算セットするメソッド
				workTime.calcBreakTime();
			}
			if(rs.getString(3) != null && rs.getString(4) != null) {
				//自動計算セットするメソッド
				workTime.calcWorkingHours();
			}
			workTimeThisMonthList.add(workTime);
		}
		return workTimeThisMonthList;
	}
}
