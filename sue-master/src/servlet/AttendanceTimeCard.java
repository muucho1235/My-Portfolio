package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.AttendanceEmployeeDAO;

//打刻情報を受け取ってデータベースに反映させるクラス。
@WebServlet("/AttendanceTimeCard")
public class AttendanceTimeCard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して従業員が既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("employeeCode") == null) {
			response.sendRedirect("attendance_login.jsp");
		} else {
			response.sendRedirect("attendance_menu.jsp");
		}
	}

	/*
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * 押されたボタンに応じた打刻処理を行う。<br>
	 * データベースに接続して対応する打刻処理を反映させる。<br>
	 * 処理が成功したら完了画面にリダイレクトさせる。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String employeeCode = (String) session.getAttribute("employeeCode");
		//どのボタンが押されたかをチェック
		String attendance = request.getParameter("attendance");
		AttendanceEmployeeDAO attendEmpDao = AttendanceEmployeeDAO.getInstance();

		boolean Flag = false;
		try {
			attendEmpDao.dbConnect();
			attendEmpDao.createSt();
			if (attendance.equals("出勤処理")) {
				Flag = attendEmpDao.setStartTime(employeeCode);
			} else if (attendance.equals("退勤処理")) {
				Flag = attendEmpDao.setFinishTime(employeeCode);
			} else if (attendance.equals("休憩開始処理")) {
				Flag = attendEmpDao.setStartBreakTime(employeeCode);
			} else if (attendance.equals("休憩終了処理")) {
				Flag = attendEmpDao.setFinishBreakTime(employeeCode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			attendEmpDao.dbDiscon();
		}

		if (Flag) {
			session.setAttribute("attendance", attendance);
			response.sendRedirect("attendance_completion.jsp");
		} else {
			response.sendRedirect("attendance_timecard_error.jsp");
		}
	}
}
