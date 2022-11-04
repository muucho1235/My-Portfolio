package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.EmployeeDAO;
import model.dao.WorkTimeDAO;
import model.entity.Employee;
import model.entity.WorkTime;

//月を受け取って対応する出退勤時刻情報を渡すクラス。
@WebServlet("/AttendanceSelectTimesheet")
public class AttendanceSelectTimesheet extends HttpServlet {
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
	 * データベースに接続して月に対応する出退勤時刻情報のリストを取得する。<br>
	 * セッション情報に出退勤時刻情報のリストをセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String employeeCode = (String) session.getAttribute("employeeCode");
		String thisMonth = request.getParameter("thisMonth");
		Calendar thisMonthCalneder = Calendar.getInstance();
		thisMonthCalneder.set(Calendar.YEAR, Integer.parseInt(thisMonth.substring(0, 4)));
		thisMonthCalneder.set(Calendar.MONTH, Integer.parseInt(thisMonth.substring(5)));

		WorkTimeDAO workTimeDao = WorkTimeDAO.getInstance();
		EmployeeDAO empdao = EmployeeDAO.getInstance();
		try {
			workTimeDao.dbConnect();
			workTimeDao.createSt();
			List<WorkTime> workTimeThisMonthList =
					workTimeDao.selectWorkTimeThisMonthList(employeeCode, thisMonth);
			session.setAttribute("workTimeThisMonthList", workTimeThisMonthList);
			empdao.dbConnect();
			empdao.createSt();
			Employee employee = empdao.selectEmployee(employeeCode);
			String employeeName = employee.getLastName() + "　" + employee.getFirstName();
			session.setAttribute("employeeName", employeeName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("thisMonth", thisMonthCalneder);
		response.sendRedirect("attendance_view_timesheet.jsp");
	}
}
