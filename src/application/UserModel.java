package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {
	Connection connection;

	public UserModel() {
		connection = SqliteConnection.connect();
		if (connection == null) {
			System.exit(1);
		}
	}

	public String[] user(String email) throws SQLException {
		String[] user = new String[2];
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String query = "select * from users where email = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			rs = preparedStatement.executeQuery();
			user[0] = String.valueOf(rs.getInt("id"));
			user[1] = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			rs.close();
		}
		return user;
	}

	public ObservableList<Classes> getClasses(int id) throws SQLException {
		ObservableList<Classes> userClasses = FXCollections.observableArrayList();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String query = "select * from classes where id = ?";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				userClasses.add(
						new Classes(rs.getString("class"), String.valueOf(rs.getInt("credit")), rs.getString("grade")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatement.close();
			rs.close();
		}
		return userClasses;
	}

}
