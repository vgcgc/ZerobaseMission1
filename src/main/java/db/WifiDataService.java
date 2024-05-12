package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WifiDataService {
	
	public List<WifiData> WifiSelect() {
		
		List<WifiData> wifiDataList = new ArrayList<>();
		
		String url = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifiuser";
		String dbPassword = "인증키 입력";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			statement = connection.createStatement();
			
			String sql = "select * from wifi_data order by X_SWIFI_DISTANCE ASC LIMIT 20";
			
			rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				
				WifiData wifiData = new WifiData();
				
				wifiData.setMgrNo(rs.getString("X_SWIFI_MGR_NO"));
				wifiData.setDistance(Double.parseDouble(rs.getString("X_SWIFI_DISTANCE")));
				wifiData.setWrdofc(rs.getString("X_SWIFI_WRDOFC"));
				wifiData.setMainNm(rs.getString("X_SWIFI_MAIN_NM"));
				wifiData.setAdres1(rs.getString("X_SWIFI_ADRES1"));
				wifiData.setAdres2(rs.getString("X_SWIFI_ADRES2"));
				wifiData.setInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
				wifiData.setInstlTy(rs.getString("X_SWIFI_INSTL_TY"));
				wifiData.setInstlMby(rs.getString("X_SWIFI_INSTL_MBY"));
				wifiData.setSvcSe(rs.getString("X_SWIFI_SVC_SE"));
				wifiData.setCmcwr(rs.getString("X_SWIFI_CMCWR"));
				wifiData.setCnstcYear(Integer.parseInt(rs.getString("X_SWIFI_CNSTC_YEAR")));
				wifiData.setInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR"));
				wifiData.setRemars3(rs.getString("X_SWIFI_REMARS3"));
				wifiData.setLat(Double.parseDouble(rs.getString("LAT")));
				wifiData.setLnt(Double.parseDouble(rs.getString("LNT")));
				wifiData.setWorkDttm(rs.getString("WORK_DTTM"));

				wifiDataList.add(wifiData);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(statement != null && !statement.isClosed()) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return wifiDataList;
			
	}

	public void wifiInsert(WifiData wifiData) {
		
		String url = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifiuser";
		String dbPassword = "인증키 입력";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		//ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);		
			
			String sql = "INSERT INTO wifi_db.wifi_data "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, wifiData.getMgrNo());
			preparedStatement.setDouble(2, wifiData.getDistance());
			preparedStatement.setString(3, wifiData.getWrdofc());
			preparedStatement.setString(4, wifiData.getMainNm());
			preparedStatement.setString(5, wifiData.getAdres1());
			preparedStatement.setString(6, wifiData.getAdres2());
			preparedStatement.setString(7, wifiData.getInstlFloor());
			preparedStatement.setString(8, wifiData.getInstlTy());
			preparedStatement.setString(9, wifiData.getInstlMby());
			preparedStatement.setString(10, wifiData.getSvcSe());
			preparedStatement.setString(11, wifiData.getCmcwr());
			preparedStatement.setInt(12, wifiData.getCnstcYear());
			preparedStatement.setString(13, wifiData.getInoutDoor());
			preparedStatement.setString(14, wifiData.getRemars3());
			preparedStatement.setDouble(15, wifiData.getLat());
			preparedStatement.setDouble(16, wifiData.getLnt());
			preparedStatement.setString(17, wifiData.getWorkDttm());

			int affected = preparedStatement.executeUpdate();
			
			if (affected > 0) {
				System.out.println("저장 성공");
			} else {
				System.out.println("저장 실패");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
//			try {
//				if(rs != null && !rs.isClosed()) {
//					rs.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			try {
				if(preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void wifiReset() {
		
		String url = "jdbc:mariadb://localhost:3306/wifi_db";
		String dbUserId = "wifiuser";
		String dbPassword = "인증키 입력";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		//ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String resetSql = "TRUNCATE wifi_data;";
			preparedStatement = connection.prepareStatement(resetSql);
			preparedStatement.executeUpdate();
			

			int affected = preparedStatement.executeUpdate();
			
			if (affected > 0) {
				System.out.println("리셋 성공");
			} else {
				System.out.println("리셋 실패");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
//			try {
//				if(rs != null && !rs.isClosed()) {
//					rs.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			try {
				if(preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
