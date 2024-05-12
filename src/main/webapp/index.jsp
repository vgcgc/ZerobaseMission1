<%@page import="db.WifiData"%>
<%@page import="db.WifiDataService"%>
<%@page import="lib.GetWifi" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=API키를 넣어주세요&libraries=services"></script>
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" href="css/table.css"/>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="javascript:void(0)" onclick="getLoadWifi()">Open API 와이파이 정보 가져오기</a>
	<br/><br/>
	LAT : <input type="text" id="LAT" value="0.0">, LNT : <input type="text" id="LNT" value="0.0">
	<button onclick="askForLocation()">내 위치 가져오기</button> 
	<button onclick="showWifi()">근처 WIFI 정보 보기</button>

	<table>
		<thead>
			<tr>
				<th>거리<br/>(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치<br/>(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외<br/>구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		</tbody>
			<tr>
				<%
					WifiDataService wifiService = new WifiDataService();
					List<WifiData> wifiList = wifiService.WifiSelect();
					
					if(wifiList.size() == 0){
						out.write("<tr><td colspan='17'><center>위치 정보를 입력한 후에 조회해 주세요.</center></td></tr>");
					}
				
					for(int i = 0; i < wifiList.size(); i++) {
				%>
				<tr>
					<td><%= String.format("%.4f", wifiList.get(i).getDistance()) %></td>
					<td><%= wifiList.get(i).getMgrNo() %></td>
					<td><%= wifiList.get(i).getWrdofc() %></td>
					<td><%= wifiList.get(i).getMainNm() %></td>
					<td><%= wifiList.get(i).getAdres1() %></td>
					<td><%= wifiList.get(i).getAdres2() %></td>
					<td><%= wifiList.get(i).getInstlFloor() %></td>
					<td><%= wifiList.get(i).getInstlTy() %></td>
					<td><%= wifiList.get(i).getInstlMby() %></td> 
					<td><%= wifiList.get(i).getSvcSe() %></td>
					<td><%= wifiList.get(i).getCmcwr() %></td>
					<td><%= wifiList.get(i).getCnstcYear() %></td>
					<td><%= wifiList.get(i).getInoutDoor() %></td>
					<td><%= wifiList.get(i).getRemars3() %></td>
					<td><%= wifiList.get(i).getLat() %></td>
					<td><%= wifiList.get(i).getLnt() %></td>
					<td><%= wifiList.get(i).getWorkDttm() %></td>
				</tr>
				<% } %>
			</tr>
		</tbody>
	</table>

</body>

<script>

	let address1 = '';
	let address2 = '';
	
	let lat = '';
	let lnt = '';
	
	function askForLocation () {
		navigator.geolocation.getCurrentPosition(function(position) {
			
			lat = position.coords.latitude;
			lnt = position.coords.longitude;
		    document.getElementById("LAT").value = lat;
		    document.getElementById("LNT").value = lnt;
		    
		    var geocoder = new kakao.maps.services.Geocoder(); // 주소-좌표 변환 객체를 생성합니다

			let coord = new kakao.maps.LatLng(lat, lnt);
			let callback = function(result, status) {
		        if (status === kakao.maps.services.Status.OK) {
		        	console.log(result);
		            address1 = result[0].address.region_2depth_name;
		            console.log(result[0].address.region_2depth_name);
		            address2 = result[0].address.region_3depth_name;
		            console.log(result[0].address.region_3depth_name);
		        }
		    }
			
		    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
		    
		});

	}
	
	function getLoadWifi(){
		
		if(lat == ""){
			alert("내 위치 가져오기 먼저 눌러주세요.");
		}else{
			location.href = "load-wifi.jsp?lat=" + lat + "&lnt=" + lnt;
		}

	}
	
	window.onload = function(){
	
		const urlParams = new URL(location.href).searchParams;
		
		if(urlParams.has("lat")){
			lat = urlParams.get("lat");
			document.getElementById("LAT").value = lat;
		}
		
		if(urlParams.has("lnt")){
			lnt = urlParams.get("lnt");
			document.getElementById("LNT").value = lnt;
		}
	
	}


</script>

</html>