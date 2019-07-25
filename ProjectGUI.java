import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.LinkedList;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ProjectGUI extends Application {

	static final Comparator<Student> ID_ORDER = new Comparator<Student>() {

		@Override
		public int compare(Student stu1, Student stu2) {

			int ID1 = stu1.getId();
			int ID2 = stu2.getId();

			if (ID1 == ID2)
				return 0;
			else if (ID1 >= ID2)
				return 1;
			else
				return -1;
		}

	};

	static final Comparator<Student> GPA_ORDER = new Comparator<Student>() {

		@Override
		public int compare(Student stu1, Student stu2) {

			double GPA1 = stu1.getGPA();
			double GPA2 = stu2.getGPA();

			if (GPA1 == GPA2)
				return 0;
			else if (GPA1 >= GPA2)
				return 1;
			else
				return -1;
		}
	};

	static final Comparator<Student> NAME_ORDER = new Comparator<Student>() {

		@Override
		public int compare(Student stu1, Student stu2) {

			String name1 = stu1.getName();
			String name2 = stu2.getName();

			return name1.compareTo(name2);
		}

	};

	@Override
	public void start(Stage primaryStage) {

		LinkedList<Department> dpt = new LinkedList<Department>();
		dpt = new LinkedList<Department>();
		dpt.add(new Department("COE", "Computer Engineering", new LinkedList<Student>()));
		dpt.add(new Department("SWE", "Software Engineering", new LinkedList<Student>()));
		dpt.add(new Department("ME", "Mechanical Engineering", new LinkedList<Student>()));
		dpt.add(new Department("EE", "Electrical Engineering", new LinkedList<Student>()));
		
		
		VBox myBox = new VBox();

		ToggleGroup choices = new ToggleGroup();
		RadioButton[] options = new RadioButton[7];

		options[0] = new RadioButton("List department by ID");
		options[1] = new RadioButton("List department by GPA");
		options[2] = new RadioButton("List department by Name");
		options[3] = new RadioButton("List all departments and number of students");
		options[4] = new RadioButton("Add new student to a department");
		options[5] = new RadioButton("Delete a student from a department");
		options[6] = new RadioButton("Change the department of a student");

		for (int i = 0; i < options.length; i++) {
			options[i].setToggleGroup(choices);
		}

		myBox.getChildren().addAll(options);

		Button submit = new Button("Submit");
		Button save = new Button("Save");
		Button exit = new Button("Exit");
		myBox.getChildren().add(submit);
		myBox.getChildren().add(save);
		myBox.getChildren().add(exit);
		ChoiceBox<Department> departments = new ChoiceBox<Department>(FXCollections.observableList(dpt));
		submit.setOnAction(new EventHandler<ActionEvent>() {

			private HBox actionWindow = new HBox();
			

			@Override
			public void handle(ActionEvent arg0) {

			//	departments.setValue(dpt.get(0));
				actionWindow.getChildren().addAll(departments);

				if (options[0].isSelected()) {

				} else if (options[1].isSelected()) {

				} else if (options[2].isSelected()) {

				} else if (options[3].isSelected()) {

				} else if (options[4].isSelected()) {

				} else if (options[5].isSelected()) {

				} else if (options[6].isSelected()) {

				} else {
					Label message = new Label("You have not chosen a valid option!");
					myBox.getChildren().add(message);
				}

				Scene popUp = new Scene(actionWindow, 500, 500);
				primaryStage.setScene(popUp);

			}
		});

		Scene display = new Scene(myBox, 500, 500);

		primaryStage.setScene(display);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
