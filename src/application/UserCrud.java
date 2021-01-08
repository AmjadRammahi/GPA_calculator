package application;

import java.sql.*;

public class UserCrud {
	Connection connection = SqliteConnection.connect();
	
	public void insert(String name, String email, String pass) throws SQLException {
		PreparedStatement preparedStatement;
		String query = "INSERT INTO users (name, email, password) VALUES(?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, pass);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.err.println("sql: " + e.getMessage());
		}
	}
	
	public void update(String name, String email, String pass) {
		PreparedStatement preparedStatement;
		String query = "UPDATE users SET name = ?, email = ?, password = ?, WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, pass);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.err.println("sql: " + e.getMessage());
		}
	}
	
	public void delete(int id) {
		PreparedStatement preparedStatement;
		String query = "DELETE FROM users WHERE id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.err.println("sql: " + e.getMessage());
		}
	}
	
}
