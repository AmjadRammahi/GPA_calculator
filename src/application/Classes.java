package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Classes {
	private StringProperty classes;
	private StringProperty credit;
	private StringProperty grade;
	
	/*
	 * constructor with initial values 
	 */
	public Classes(String classes, String credit, String grade) {
		this.classes = new SimpleStringProperty(classes);
		this.credit = new SimpleStringProperty(credit);
		this.grade = new SimpleStringProperty(grade);
	}
	
	/*
	 * default constructor 
	 */
	public Classes() {
		this(null, null, null);
	}

	
	public String getClasses() {
		return classes.get();
	}
	
	public StringProperty classesProperty() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes.set(classes);
	}

	public String getCredit() {
		return credit.get();
	}
	
	public StringProperty creditProperty() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit.set(credit);
	}

	public String getGrade() {
		return grade.get();
	}
	
	public StringProperty gradeProperty() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade.set(grade);
	}
}
