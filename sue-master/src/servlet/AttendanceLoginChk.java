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

//従業員のログインをチェックするクラス。
@WebServlet("/AttendanceLoginChk")
public class AttendanceLoginChk extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して従業員が既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String employeeCode = (String) session.getAttribute("employeeCode");
		if (employeeCode == null) {
			response.sendRedirect("attendance_login.jsp");
		} else {
			response.sendRedirect("attendance_menu.jsp");
		}
	}

	/*
	 * データベースに接続して従業員のログインをチェックする。<br>
	 * ログインに成功したらセッション情報に従業員コードをセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String employeeCode = request.getParameter("employeeCode");
		String password = request.getParameter("password");

		AttendanceEmployeeDAO attendEmpDao = AttendanceEmployeeDAO.getInstance();

		try {
			attendEmpDao.dbConnect();
			attendEmpDao.createSt();
			employeeCode = attendEmpDao.loginEmployee(employeeCode, password);
			session.setAttribute("employeeCode", employeeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			attendEmpDao.dbDiscon();
		}
		if (employeeCode != null) {
			response.sendRedirect("attendance_menu.jsp");
		} else {
			response.sendRedirect("attendance_login_error.jsp");
		}
	}
}
