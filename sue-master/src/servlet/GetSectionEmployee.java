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
import model.entity.Section;

//データベースに接続して全部署を取得するクラス。
@WebServlet("/GetSectionEmployee")
public class GetSectionEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * データベースに接続して全部署を取得する。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			EmployeeDAO empdao = EmployeeDAO.getInstance();

			List<Section> sections = new LinkedList<Section>();

			try {
				empdao.dbConnect();
				empdao.createSt();
				sections = empdao.getSection();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				empdao.dbDiscon();
			}

			session.setAttribute("sections", sections);
			response.sendRedirect("regist_employee.jsp");
		}
	}

	//Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
