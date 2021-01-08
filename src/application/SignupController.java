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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SignupController implements Initializable {
	UserCrud crud = new UserCrud();
	SignupModel signupmodel = new SignupModel();
	FXMLLoader loader = new FXMLLoader();
	Stage primaryStage = new Stage();

	@FXML
	private Label isConnected;

	@FXML
	private TextField newName;

	@FXML
	private TextField newEmail;

	@FXML
	private PasswordField newPass;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (signupmodel.isDbConnected()) {
			isConnected.setText("connected");
		} else {
			isConnected.setText("not connected");
		}
	}

	public void signUp(ActionEvent event) {
		try {
			crud.insert(newName.getText(), newEmail.getText(), newPass.getText());
			Mediator.getInstance().setUser(new User(newEmail.getText()));
			if (signupmodel.isSignup(newEmail.getText(), newPass.getText())) {
				((Node)event.getSource()).getScene().getWindow().hide();
				Pane root = loader.load(getClass().getResource("/application/user.fxml").openStream());
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		} catch (SQLException e) {
			System.out.println("Invalid Information");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
