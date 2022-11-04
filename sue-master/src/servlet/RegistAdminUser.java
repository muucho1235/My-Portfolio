package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDAO;

//管理者ユーザー新規登録クラス。
@WebServlet("/RegistAdminUser")
public class RegistAdminUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して管理者ユーザーが既にログインしていたらメニュー画面にリダイレクトさせる。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("menu.jsp");
		}
	}

	/*
	 * Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。<br>
	 * データベースに接続して管理者ユーザーを挿入する。<br>
	 * セッションに管理者ユーザーIDをセットする。<br>
	 * メニュー画面にリダイレクトさせる。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストオブジェクトのエンコーディング方式の指定
		request.setCharacterEncoding("UTF-8");
		
		//セッションキーを用意
		HttpSession session = request.getSession();
		
		// リクエストパラメータの取得
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		UserDAO dao = UserDAO.getInstance();
		
		boolean insertUserChkFlag = false;
		
		try {
			dao.dbConnect();
			dao.createSt();
			insertUserChkFlag = dao.insertUser(userId, password);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.dbDiscon();
		}
		
		if(insertUserChkFlag) {
			session.setAttribute("loginUserId", userId);
			response.sendRedirect("completion.jsp");
		} else {
			response.sendRedirect("regist_error_adminuser.jsp");
		}
	}
}
