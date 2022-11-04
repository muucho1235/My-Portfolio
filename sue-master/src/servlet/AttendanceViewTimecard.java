package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.WorkTimeDAO;

//月を受け取ってデータベースに接続して対応する出退勤時刻情報を画面に送るクラス。
@WebServlet("/AttendanceViewTimecard")
public class AttendanceViewTimecard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して従業員が既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String employeeCode = (String) session.getAttribute("employeeCode");
		WorkTimeDAO workTimeDao = WorkTimeDAO.getInstance();

		try {
			workTimeDao.dbConnect();
			workTimeDao.createSt();
			String startCheck = workTimeDao.selectStartTime(employeeCode);
			session.setAttribute("startWork", startCheck);

			if(startCheck != null) {
				String finishCheck = workTimeDao.selectFinishTime(employeeCode);
				session.setAttribute("finishWork", finishCheck);

				//退勤ボタンが押されてなかったら休憩開始・終了ボタンが押せる
				if (finishCheck == null) {
					String startBreakCheck = workTimeDao.selectStartBreak(employeeCode);
					session.setAttribute("startBreak", startBreakCheck);

					//休憩開始ボタンが押されていたら休憩終了ボタンが押せる
					if (startBreakCheck != null) {
						String finishBreakCheck = workTimeDao.selectFinishBreak(employeeCode);
						session.setAttribute("finishBreak", finishBreakCheck);

					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			workTimeDao.dbDiscon();
		}
		response.sendRedirect("attendance_timecard.jsp");
	}

	//Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
