package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.EmployeeDAO;

//データベースから従業員を削除するクラス。
@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * Servlet に GET リクエストを処理可能にさせるため、(service メソッドを通じて) サーバによって呼び出される。<br>
	 * データベースに接続して対応する従業員を削除する。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUserId") == null) {
			response.sendRedirect("login.jsp");
		} else {
			//リクエストのエンコーディング方式を指定
			request.setCharacterEncoding("utf-8");
			//リクエストパラメータの取得
			String employeeCode = (String)session.getAttribute("employeeCode");
			//インスタンス生成
			EmployeeDAO dao = EmployeeDAO.getInstance();
			
			//従業員データ取得（従業員コードに適応するもの）
			int count=0;
			try {
				dao.dbConnect();
				dao.createSt();
				count = dao.deleteEmployee(employeeCode);
				session.setAttribute("COUNT", count);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dao.dbDiscon();
			}
			
			if(count != 1) {
				response.sendRedirect("delete_error_employee.jsp");
			}else{
				response.sendRedirect("delete_completion.jsp");
			}
		}
	}
	//Servlet に POST リクエストを処理可能にさせるため、(service メソッド経由で) サーバによって呼び出される。
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
