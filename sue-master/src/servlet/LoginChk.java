package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDAO;

//管理者ユーザーのログインをチェックするクラス。
@WebServlet("/LoginChk")
public class LoginChk extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * 直接アクセスに対して管理者ユーザーが既にログインしていたらメニュー画面にリダイレクトさせる。
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
	 * データベースに接続して管理者ユーザーのログインをチェックする。<br>
	 * ログインに成功したらセッション情報にユーザーIDをセットする。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		UserDAO userDao = UserDAO.getInstance();

		boolean loginUserChkFlag = false;
		try {
			// 接続
			userDao.dbConnect();
			// ステートメント作成
			userDao.createSt();

			loginUserChkFlag = userDao.loginUser(userId, password);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			userDao.dbDiscon();
		}

		if (loginUserChkFlag) {
			session.setAttribute("loginUserId", userId);
			response.sendRedirect("menu.jsp");
		} else {
			response.sendRedirect("login_error.jsp");
		}
	}
}
