<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者用メニュー</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
	<div class="header">
		<span class="big_title">N</span>akamura <span class="big_title">K</span>azuya
		<span class="big_title">P</span>ortfolio
	</div>
	<div class="menu">
		<div class="main_frame">
			<p>管理者用メニュー画面</p>
		</div>
	</div>

	<div class="main_wrapper">
		<div class="employee_button">

			<a href="GetSectionEmployee" class="regist_button">
				<button class="display_button">従業員登録</button>
			</a> <a href="DisplayEmployeeList">
				<button class="display_button">従業員情報一覧</button>
			</a>
		</div>
		<div class="logout_button">
			<a href="regist_adminuser.jsp">
				<button class="display_button">管理者権限を付与する</button>
			</a>
		</div>
		<div class="link_main_button">
			<form action="LogoutChk" method="post">
				<button class="display_button">ログアウト</button>
			</form>
		</div>
		<div class=link_main_button>
			<input type="button" class="display_button"
				onclick="location.href='index.jsp'" value="Topページ">
		</div>
	</div>

	<div class="footer_top">
		<table class="table_format">
			<tr>
				<th>管理者情報</th>
			</tr>
			<tr>
				<td class="cel">会社名</td>
				<td>&nbsp;</td>
				<td>株式会社 Nakamura</td>
			</tr>
			<tr>
				<td class="cel">Tell</td>
				<td>&nbsp;</td>
				<td>012-345-6789</td>
			<tr>
				<td class="cel">Email</td>
				<td>&nbsp;</td>
				<td>aiueo@freemail.com</td>
			</tr>
		</table>
	</div>

	<div class="footer_design">
		<footer>
			<small>© 2022 Nakamura.</small>
		</footer>
	</div>
</body>
</html>

<%
	}
%>