package application;

import java.sql.*;

public class LoginModel {
	Connection connection;

	public LoginModel() {
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

	public boolean isLogin(String email, String pass) throws SQLException {
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
		} catch (SQLException e) {
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}

	
}