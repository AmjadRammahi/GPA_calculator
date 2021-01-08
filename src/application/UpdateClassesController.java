package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateClassesController implements Initializable {
	FXMLLoader loader = new FXMLLoader();
	Stage primaryStage = new Stage();
	ClassesCrud classes = new ClassesCrud();
	User user = Mediator.getInstance().getUser();
	/*
	 * current user [0]= user "id" needed to connect the class with the current
	 * user, [1] = user "name"
	 */
	String[] currentUser = new String[2];

	@FXML
	private TextField newclass;
	@FXML
	private TextField credit;
	@FXML
	private TextField grade;

	// Event Listener on Button.onAction
	@FXML
	public void addClass(ActionEvent event) {
		try {
			classes.insert(Integer.parseInt(currentUser[0]), newclass.getText(), Integer.parseInt(credit.getText()),
					grade.getText());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteClass(ActionEvent event) {
			classes.delete(newclass.getText());
	}
	
	@FXML
	public void updateClass(ActionEvent event) {
			classes.update(Integer.parseInt(currentUser[0]), newclass.getText(), Integer.parseInt(credit.getText()), grade.getText());
	}

	// Event Listener on Button.onAction
	@FXML
	public void userMain(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Pane root = loader.load(getClass().getResource("/application/user.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		UserModel userModel = new UserModel();
		try {
			currentUser = userModel.user(user.getEmail());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
