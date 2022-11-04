package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//削除処理か編集処理か判断するクラス。
@WebServlet("/CheckEditDelete")
public class CheckEditDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して従業員が既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("menu.jsp");
		}
	}

	/*
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * 削除処理か編集処理か判断する。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		
		String employeeCode = request.getParameter("employeeCode");
		
		session.setAttribute("employeeCode", employeeCode);
		
		if (request.getParameter("submit").equals("従業員を編集する")) {
			response.sendRedirect("EditCheckEmployee");
			
		} else if (request.getParameter("submit").equals("従業員を削除する")) {
			response.sendRedirect("DeleteEmployee");
			
		} else {
			response.sendRedirect("show_all_employee.jsp");
		}
	}
}
