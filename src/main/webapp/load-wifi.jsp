<%@ page import="db.WifiData" %>
<%@ page import="db.WifiDataService" %>
<%@ page import="db.HistoryService" %>
<%@ page import="java.util.List" %>
<%@page import="lib.GetWifi" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
	<%
	
		Double lat = Double.parseDouble(request.getParameter("lat"));
		Double lnt = Double.parseDouble(request.getParameter("lnt"));

		// 와이파이 검색 기록
		HistoryService historyService = new HistoryService();
		historyService.historyInsert(lat, lnt);
	
		// 와이파이 정보 가져오기
		GetWifi getWifi = new GetWifi();
		WifiDataService wifiDataService = new WifiDataService();
		
		wifiDataService.wifiReset();
		int wifiCnt = getWifi.GetWifiSave(lat, lnt);
		
		String hrefStr = "index.jsp?lat=" + lat + "&lnt=" + lnt;
	
	%>
	<h1><%=wifiCnt%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
	<a href="<%=hrefStr%>">홈 으로 가기</a>
</body>
</html>