<%@page import="model.entity.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (session.getAttribute("loginUserId") == null) {
		response.sendRedirect("login.jsp");
	} else {
		Employee employee = (Employee) session.getAttribute("employee");
		if (employee != null) {
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>編集完了</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
	<div class="header">
		<span class="big_title">N</span>akamura <span class="big_title">K</span>azuya
		<span class="big_title">P</span>ortfolio
	</div>

	<div class="menu">
		<div class="main_frame">
			<p>
				従業員コード：<%=employee.getEmployeeCode()%>の編集完了しました
			</p>
		</div>
	</div>

	<div class="main_admin">
		<p>引き続き入力する場合は、メニューから入力をお願いいたします。</p>
		<div class="a_logout_button">
			<a href="menu.jsp">
				<button class="display_button">メニューに戻る</button>
			</a>
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
	} else {
			response.sendRedirect("menu.jsp");

		}
	}
%>