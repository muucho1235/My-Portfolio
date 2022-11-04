package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.ViewListDAO;
import model.entity.ViewListDisplay;

//データベースに接続して従業員情報全件一覧を取得するクラス。
@WebServlet("/DisplayEmployeeList")
public class DisplayEmployeeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。
	 * データベースに接続して従業員情報全件一覧を取得してセッションにセットする。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			//リクエストデータのエンコーディング
			request.setCharacterEncoding("UTF-8");
			// DAOのインスタンス生成
			 ViewListDAO dao =ViewListDAO.getInstance();

			try {
				dao.dbConnect();
				dao.createSt();
				
				List<ViewListDisplay> vldlist = dao.showAllList();
				session.setAttribute("vldlist", vldlist);

				response.sendRedirect("show_all_employee.jsp");

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.dbDiscon();
			}
		}
	}

	// Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
