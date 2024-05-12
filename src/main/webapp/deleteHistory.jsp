<%@ page import="java.util.List" %>
<%@ page import="db.HistoryService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 삭제하기</title>
</head>
<body>
	<%
		String id = request.getParameter("id");
	
		HistoryService historyService = new HistoryService();
		historyService.historyDelete(id);
		
		response.sendRedirect("history.jsp");
	%>
</body>
</html>