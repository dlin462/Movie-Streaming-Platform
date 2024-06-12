package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
	private static DBHelper instance;
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String database_url = "jdbc:mysql://localhost:3306/cse305_2";
	public static final String username = "root";
	public static final String password = "root";

	private DBHelper() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading database driver.");
		}
	}
	
	public static DBHelper getInstance() {
		if (instance == null)
			instance = new DBHelper();
		return instance;
	}
	
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(database_url, username, password);
	}
	
	public void execute(String sqlString, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public ResultSet executeQuery(String sqlString, Connection c) {
		ResultSet rs = null;
		Connection conn = c;
		try {
			PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sqlString);
			rs=statement.executeQuery();        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}