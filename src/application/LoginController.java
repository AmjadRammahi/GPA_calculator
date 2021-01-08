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

public class LoginController implements Initializable {
	public LoginModel loginModel = new LoginModel();
	Stage primaryStage = new Stage();
	FXMLLoader loader = new FXMLLoader();
	public UserCrud crud= new UserCrud();
	Pane root = new Pane();
	
	
	@FXML
	private Label isConnected;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private PasswordField txtPass;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (loginModel.isDbConnected()) {
			isConnected.setText("connected");
		}else {
			isConnected.setText("not connected");
		}
	}
	
	public void login(ActionEvent event) {
		try {
			if (loginModel.isLogin(txtEmail.getText(), txtPass.getText())) {
				isConnected.setText("username and password is correct");
				Mediator.getInstance().setUser(new User(txtEmail.getText()));
				((Node)event.getSource()).getScene().getWindow().hide();
				redirect("/application/user.fxml");
			}else {
				isConnected.setText("invalide username and password");
			}
		} catch (SQLException e) {
			isConnected.setText("invalide username and password");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void newAccount(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			redirect("/application/signup.fxml");
		} catch (IOException e) {
			e.printStackTrace(); 
		}	
	}
	
	public void redirect(String page) throws IOException {
		root = loader.load(getClass().getResource(page).openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
