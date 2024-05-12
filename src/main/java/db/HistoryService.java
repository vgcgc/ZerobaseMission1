package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {
	
	public List<History> historySelect() {
		
		List<History> historyList = new ArrayList<>();
		
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
			
			String sql = "select * from history";
			
			rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				
				String id = rs.getString("ID");
				double lat = Double.parseDouble(rs.getString("LAT"));
				double lnt = Double.parseDouble(rs.getString("LNT"));
				String dateStamp = rs.getString("DATE_STAMP");
				
				History history = new History();
				history.setId(id);
				history.setLat(lat);
				history.setLnt(lnt);
				history.setDateStamp(dateStamp);
				
				historyList.add(history);
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
		
		return historyList;
			
	}

	public void historyInsert(Double latValue, Double lntValue) {
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
			
			String sql = "INSERT INTO wifi_db.history "
					+ "(LAT, LNT, DATE_STAMP) "
					+ "VALUES (?, ?, now());";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, Double.toString(latValue));
			preparedStatement.setString(2, Double.toString(lntValue));
			
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
	
	public void historyDelete(String id) {
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
			
			String sql = "DELETE FROM wifi_db.history "
					+ "WHERE ID = ? ";
			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			
			int affected = preparedStatement.executeUpdate();
			
			if (affected > 0) {
				System.out.println("삭제 성공");
			} else {
				System.out.println("삭제 실패");
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
