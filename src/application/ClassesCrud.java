package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClassesCrud {

	Connection connection = SqliteConnection.connect();

	public void insert(int userId, String classes, int credit, String grade) throws SQLException {
		PreparedStatement preparedStatement;
		String query = "INSERT INTO classes (id, class, credit, grade) VALUES(?, ?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, classes);
			preparedStatement.setInt(3, credit);
			preparedStatement.setString(4, grade);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(int userId, String classes, int credit, String grade) {
		PreparedStatement preparedStatement;
		String query = "UPDATE classes SET id = ?, credit = ?, grade = ? WHERE class = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, credit);
			preparedStatement.setString(3, grade);
			preparedStatement.setString(4, classes);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String className) {
		PreparedStatement preparedStatement;
		String query = "DELETE FROM classes WHERE class = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, className);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
