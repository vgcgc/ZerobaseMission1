<%@ page import="db.History" %>
<%@ page import="java.util.List" %>
<%@ page import="db.HistoryService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" href="css/table.css"/>
</head>
<body>
	<%
		HistoryService historyService = new HistoryService();
		List<History> historyList = historyService.historySelect();	
	%>
	<h1>위치 히스토리 목록</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="index.jsp">Open API 와이파이 정보 가져오기</a>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		</tbody>
			<tr>
				<%
					for(int i = 0; i < historyList.size(); i++) {
				%>
				<tr>
					<td><%= historyList.get(i).getId() %></td>
					<td><%= historyList.get(i).getLat() %></td>
					<td><%= historyList.get(i).getLnt() %></td>
					<td><%= historyList.get(i).getDateStamp() %></td>
					<td style="text-align: center"><button onclick="deleteHistory(<%= historyList.get(i).getId()%>)">삭제</button></td>
				</tr>
				<% } %>
			</tr>
		</tbody>
	</table>
</body>

<script>
	function deleteHistory(id){
		location.href = "deleteHistory.jsp?id=" + id;
	}
</script>
</html>