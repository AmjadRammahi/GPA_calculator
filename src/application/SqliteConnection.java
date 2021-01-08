package application;

import java.sql.*;

public class SqliteConnection {
	public static Connection connect(){
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:UserDB.sqlite");
					return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
