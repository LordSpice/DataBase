import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
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

public class ProjectGUI extends Application implements Serializable {

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

	public static void orderStudents(LinkedList<Department> dpt) {

		GridPane actionWindow = new GridPane();

		actionWindow.setVgap(5);
		actionWindow.setHgap(5);

		Stage secondaryStage = new Stage();

		final ChoiceBox<Department> departments = new ChoiceBox<Department>(FXCollections.observableList(dpt));
		departments.setValue(dpt.get(0));
		actionWindow.add(departments, 0, 0);

		String[] ordOpt = new String[3];

		ordOpt[0] = "Order students by ID";
		ordOpt[1] = "Order students by name";
		ordOpt[2] = "Order students by GPA";

		final ChoiceBox<String> setOrder = new ChoiceBox<String>(FXCollections.observableArrayList(ordOpt));
		actionWindow.add(setOrder, 0, 1);

		Button back = new Button("Back");
		actionWindow.add(back, 3, 4);

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				secondaryStage.hide();

			}
		});

		LinkedList<Student> students = dpt.get(departments.getSelectionModel().getSelectedIndex()).getStudents();

		Button print = new Button("Print");
		actionWindow.add(print, 2, 4);

		print.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				GridPane result = new GridPane();

				result.setVgap(5);
				result.setHgap(5);

				if (departments.getSelectionModel().getSelectedIndex() == 0) {
					listStudents(result, students, ID_ORDER);

				} else if (departments.getSelectionModel().getSelectedIndex() == 1) {
					listStudents(result, students, NAME_ORDER);

				} else if (departments.getSelectionModel().getSelectedIndex() == 2) {
					listStudents(result, students, GPA_ORDER);

				} else
					actionWindow.add(new Label("Please select an order option"), 3, 5);
			}
		});

		Scene popUp = new Scene(actionWindow, 400, 150);

		secondaryStage.setScene(popUp);
		secondaryStage.show();
	}

	public static void listStudents(GridPane result, LinkedList<Student> students, Comparator<Student> order) {

		Collections.sort(students, order);
		result.add(new Label(students.toString()), 0, 0);

		Scene resultWindow = new Scene(result, 800, 500);

		Stage print = new Stage();
		print.setScene(resultWindow);
		print.show();
	}

	public static void listDepartments(LinkedList<Department> dpt) { // Add students then test method | implemented serializable but haven't tested

		Stage displayWindow = new Stage();

		GridPane display = new GridPane();

		display.setVgap(5);
		display.setHgap(5);

		Label[] output = new Label[dpt.size()];

		int index = 0;
		int row = 0;
		for (Department department : dpt) {
			output[index].setText(department + "\n" + department.getStudents());
			display.add(output[index], 0, row);
			index++;
			row += 2;
		}

		display.add(new Label(dpt.size() + ""), 0, row);

		Scene displayScene = new Scene(display, 800, 800);

		displayWindow.setScene(displayScene);
		displayWindow.show();
	}

	public static void addStudent() {

		Stage secondaryStage = new Stage();

		GridPane actionWindow = new GridPane();

		actionWindow.setVgap(5);
		actionWindow.setHgap(5);

		actionWindow.add(new Label("Enter the student's name: "), 0, 0);
		actionWindow.add(new Label("Enter the student's ID: "), 0, 1);

		TextField name = new TextField();
		TextField ID = new TextField();

		actionWindow.add(name, 1, 0);
		actionWindow.add(ID, 1, 1);

		Button add = new Button("Add");
		Button back = new Button("Back");
		actionWindow.add(add, 3, 2);
		actionWindow.add(back, 4, 2);

		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		Scene studentAdd = new Scene(actionWindow, 500, 130);

		secondaryStage.setScene(studentAdd);
		secondaryStage.show();

	}

	public static LinkedList<Department> retrieveData() { // Refuses to read data

		LinkedList<Department> dpt = null;

		try {
			ObjectInputStream readObj = new ObjectInputStream(new FileInputStream("departments.dat"));
			dpt = (LinkedList<Department>) readObj.readObject();
			readObj.close();

		} catch (FileNotFoundException fe) {

			dpt = new LinkedList<Department>();
			try {
				ObjectOutputStream objPrint = new ObjectOutputStream(new FileOutputStream("departments.dat"));
				objPrint.writeObject(dpt);
				objPrint.close();

			} catch (FileNotFoundException fe2) {
			} catch (IOException ie) {
			}
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}

		return dpt;
	}

	public static void printData(LinkedList<Department> dpt) {

		try {
			ObjectOutputStream objPrint = new ObjectOutputStream(new FileOutputStream("departments.dat"));
			objPrint.writeObject(dpt);
			objPrint.close();

		} catch (FileNotFoundException fe) {
		} catch (IOException e) {
		}
	}

	@Override
	public void start(Stage primaryStage) {

		// LinkedList<Department> dpt = retrieveData();
		LinkedList<Department> dpt = new LinkedList<Department>(); // Temporarily created a list to create other methods

		dpt.add(new Department("COE", "Computer Engineering"));
		dpt.add(new Department("SWE", "Software Engineering"));
		dpt.add(new Department("ME", "Mechanical Engineering"));
		dpt.add(new Department("EE", "Electrical Engineering"));

		GridPane mainWindow = new GridPane();

		mainWindow.setVgap(5);
		mainWindow.setHgap(5);

		ToggleGroup choices = new ToggleGroup();
		RadioButton[] options = new RadioButton[5];

		options[0] = new RadioButton("List department students");
		options[1] = new RadioButton("List all departments and number of students");
		options[2] = new RadioButton("Add new student to a department");
		options[3] = new RadioButton("Delete a student from a department");
		options[4] = new RadioButton("Change the department of a student");

		for (int i = 0; i < options.length; i++) {
			options[i].setToggleGroup(choices);
			mainWindow.add(options[i], 0, i);
		}

		Button submit = new Button("Submit");
		Button save = new Button("Save");
		Button exit = new Button("Exit");
		mainWindow.add(submit, 2, 5);
		mainWindow.add(save, 3, 5);
		mainWindow.add(exit, 4, 5);

		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (options[0].isSelected()) {

					orderStudents(dpt);

				} else if (options[1].isSelected()) {

					listDepartments(dpt);

				} else if (options[2].isSelected()) {

					addStudent();

				} else if (options[3].isSelected()) {

				} else if (options[4].isSelected()) {

				} else {
					Label message = new Label("You have not chosen a valid option!");
					mainWindow.add(message, 5, 5);
				}

			}
		});

		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				printData(dpt);
			}
		});

		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();

			}
		});

		Scene display = new Scene(mainWindow, 600, 200);

		primaryStage.setScene(display);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
