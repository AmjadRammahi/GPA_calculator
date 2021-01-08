package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupModel {
	Connection connection;

	public SignupModel() {
		connection = SqliteConnection.connect();
		if (connection == null) {
			System.exit(1);
		}
	}

	public boolean isDbConnected() {
		try {
			return !connection.isClosed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSignup(String email, String pass) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "select * from users where email = ? and password = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, pass);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}
}
