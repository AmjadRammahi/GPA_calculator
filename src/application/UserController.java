package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserController implements Initializable {
	Stage primaryStage = new Stage();
	FXMLLoader loader = new FXMLLoader();
	Pane root = new Pane();
	User user = Mediator.getInstance().getUser();
	ClassesCrud classes = new ClassesCrud();
	private ObservableList<Classes> userClasses = FXCollections.observableArrayList();

	/*
	 * current user [0]= user "id" needed to connect the class with the current
	 * user, [1] = user "name"
	 */
	String[] currentUser = new String[2];

	@FXML
	private Label username;

	@FXML
	private Label cumulativeGPA;

	@FXML
	private TextField newclass;

	@FXML
	private TextField credit;

	@FXML
	private TextField grade;

	@FXML
	private TableView<Classes> classesTable;

	@FXML
	private TableColumn<Classes, String> classColumn;

	@FXML
	private TableColumn<Classes, String> creditColumn;

	@FXML
	private TableColumn<Classes, String> gradeColumn;

	/*
	 * sign out button in the interface close the current window and take you back
	 * to the main login window
	 */
	public void signOut(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			root = loader.load(getClass().getResource("/application/login.fxml").openStream());
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
		DecimalFormat df = new DecimalFormat("#.###");
		Double result = 0.0;
		UserModel userModel = new UserModel();
		try {
			currentUser = userModel.user(user.getEmail());
			userClasses = userModel.getClasses(Integer.parseInt(currentUser[0]));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		username.setText(currentUser[1]);
		classColumn.setCellValueFactory(cellData -> cellData.getValue().classesProperty());
		creditColumn.setCellValueFactory(cellData -> cellData.getValue().creditProperty());
		gradeColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
		classesTable.setItems(userClasses);

		/*
		 * checking whether observable List is empty or not, if not calculate current
		 * cumulative GPA
		 */
		if (!userClasses.isEmpty()) {
			ArrayList<Object> grades = new ArrayList<>();
			ArrayList<Object> credits = new ArrayList<>();
			Double totalGradePoints = 0.0;
			/*
			 * put the values of each columns in the appropriate list
			 */
			classesTable.getItems().stream().forEach((o) -> grades.add(gradeColumn.getCellData(o)));
			classesTable.getItems().stream().forEach((o) -> credits.add(creditColumn.getCellData(o)));
			ArrayList<Double> gradePoints = getGradesPoints(grades);
			for (int i = 0; i < grades.size(); i++) {
				totalGradePoints += Double.parseDouble(credits.get(i).toString()) * gradePoints.get(i);
			}
			result = totalGradePoints / getTotalCredits(credits);
		}
		cumulativeGPA.setText(String.valueOf(df.format(result)));
	}

	/*
	 * pop up window to add class to the current user database
	 */
	public void newClass(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Pane root = loader.load(getClass().getResource("/application/updateClasses.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param list of the grades
	 * @return new list of converted letter grade to numbered grades
	 */
	public ArrayList<Double> getGradesPoints(ArrayList<Object> gradesList) {
		ArrayList<Double> gradePoints = new ArrayList<>();
		for (Object grade : gradesList) {
			switch (grade.toString()) {
			case "A":
				gradePoints.add(4.0);
				break;
			case "A-":
				gradePoints.add(3.7);
				break;
			case "B+":
				gradePoints.add(3.3);
				break;
			case "B":
				gradePoints.add(3.0);
				break;
			case "B-":
				gradePoints.add(2.7);
				break;
			case "C+":
				gradePoints.add(2.3);
				break;
			case "C":
				gradePoints.add(2.0);
				break;
			case "C-":
				gradePoints.add(1.7);
				break;
			case "D+":
				gradePoints.add(1.3);
				break;
			case "D":
				gradePoints.add(1.0);
				break;
			case "D-":
				gradePoints.add(0.7);
				break;
			case "F":
				gradePoints.add(0.0);
				break;
			default:
				gradePoints.add(0.0);
			}
		}
		return gradePoints;
	}
	/**
	 * 
	 * @param creditsList list of all the taken credit hours 
	 * @return sum of all credits
	 */
	public double getTotalCredits(ArrayList<Object> creditsList) {
		double totalCredits = 0;
		for (Object credit : creditsList) {
			totalCredits += Double.parseDouble(credit.toString());
		}
		return totalCredits;
	}
}
