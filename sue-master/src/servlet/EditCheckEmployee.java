package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.EmployeeDAO;
import model.entity.Employee;
import model.entity.Section;

/**
 * Servlet implementation class EmployeeEdit
 * @author Yoshiyuki Tonami
 * データベースに接続して従業員コードに対応する従業員情報を送る。
 */
@WebServlet("/EditCheckEmployee")
public class EditCheckEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * データベースに接続して従業員コードに対応する従業員情報をセッションにセットする。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			String employeeCode = (String) session.getAttribute("employeeCode");
			EmployeeDAO empdao = EmployeeDAO.getInstance();
			Employee employee = null;
			List<Section> sections = new LinkedList<Section>();

			if (employeeCode != null) {

				try {
					empdao.dbConnect();
					empdao.createSt();
					employee = empdao.selectEmployee(employeeCode);
					sections = empdao.getSection();

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					empdao.dbDiscon();
				}

				session.setAttribute("employee", employee);
				session.setAttribute("sections", sections);

				if (employee == null) {
					response.sendRedirect("menu.jsp");
				} else {
					response.sendRedirect("edit_employee.jsp");
				}
			} else {
				response.sendRedirect("show_all_employee.jsp");
			}
		}
	}

	//Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
