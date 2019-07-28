import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

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

		public int compare(Student stu1, Student stu2) {
			if (stu1.getGPA() > stu2.getGPA())
				return 1;

			else if (stu1.getGPA() < stu2.getGPA())
				return -1;

			else
				return 0;
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

	@SuppressWarnings("unchecked")
	public static LinkedList<Department> retrieveData() {

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

	public static void errorWindow(String title, String message) {

		Stage errorWindow = new Stage();

		VBox layout = new VBox();

		Label errorMessage = new Label(message);
		Button closeButton = new Button("Ok");

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				errorWindow.close();

			}
		});

		layout.getChildren().addAll(errorMessage, closeButton);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 400, 70);

		errorWindow.setTitle(title);
		errorWindow.setScene(scene);
		errorWindow.showAndWait();
	}

	public static void listStudents(LinkedList<Department> dpt, int departmentIndex, int order) {

		GridPane layout = new GridPane();

		layout.setVgap(5);
		layout.setHgap(5);

		LinkedList<Student> students = dpt.get(departmentIndex).getStudents();
		
		if (order == 0)
			Collections.sort(students, ID_ORDER);
		else if (order == 1)
			Collections.sort(students, GPA_ORDER);
		else if (order == 2)
			Collections.sort(students, NAME_ORDER);

		layout.add(new Label(dpt.get(departmentIndex).getStudents().toString()), 0, 0);
		layout.setAlignment(Pos.CENTER);

		Scene resultWindow = new Scene(layout, 1000, 500);

		Stage print = new Stage();

		print.setTitle("Students");
		print.setScene(resultWindow);
		print.show();
	}

	public static void orderStudents(LinkedList<Department> dpt) {

		GridPane actionWindow = new GridPane();

		actionWindow.setVgap(5);
		actionWindow.setHgap(5);

		Stage secondaryStage = new Stage();

		ChoiceBox<Department> departments = new ChoiceBox<Department>(FXCollections.observableList(dpt));
		actionWindow.add(departments, 0, 0);

		String[] ordOpt = new String[3];

		ordOpt[0] = "Order students by ID";
		ordOpt[1] = "Order students by name";
		ordOpt[2] = "Order students by GPA";

		ChoiceBox<String> setOrder = new ChoiceBox<String>(FXCollections.observableArrayList(ordOpt));
		actionWindow.add(setOrder, 0, 1);

		Button back = new Button("Back");
		actionWindow.add(back, 3, 4);

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				secondaryStage.hide();

			}
		});

		Button print = new Button("Print");
		actionWindow.add(print, 2, 4);

		print.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				int departmentIndex = departments.getSelectionModel().getSelectedIndex();

				if (departmentIndex == 0) {

					listStudents(dpt, departmentIndex, 0);

				} else if (departmentIndex == 1) {

					listStudents(dpt, departmentIndex, 1);
					
				} else if (departmentIndex == 2) {

					listStudents(dpt, departmentIndex, 2);

				} else
					errorWindow("Invalid Order", "Please select a valid order option!");
			}
		});

		actionWindow.setAlignment(Pos.CENTER);

		Scene popUp = new Scene(actionWindow, 400, 150);

		secondaryStage.setScene(popUp);
		secondaryStage.setTitle("Organise Students");
		secondaryStage.show();
	}

	public static void listDepartments(LinkedList<Department> dpt) {

		Stage displayWindow = new Stage();

		GridPane display = new GridPane();

		display.setVgap(5);
		display.setHgap(5);

		Label[] output = new Label[dpt.size()];

		int index = 0;
		int row = 0;
		int total = 0;
		for (Department department : dpt) {
			output[index] = new Label();
			output[index].setText(department + "\n" + department.getStudents());
			display.add(output[index], 0, row);
			index++;
			row += 2;
			total += department.getStudents().size();
		}

		display.add(new Label("Total number of students: " + total), 0, row);

		Scene displayScene = new Scene(display, 1000, 800);

		displayWindow.setTitle("Departments");
		displayWindow.setScene(displayScene);
		displayWindow.show();
	}

	public static void addStudent(LinkedList<Department> dpt) {

		Stage secondaryStage = new Stage();

		GridPane actionWindow = new GridPane();

		ChoiceBox<Department> departments = new ChoiceBox<Department>(FXCollections.observableList(dpt));

		RadioButton underGraduate = new RadioButton("Undergraduate");
		RadioButton graduate = new RadioButton("graduate");

		ToggleGroup btns = new ToggleGroup();
		underGraduate.setToggleGroup(btns);
		graduate.setToggleGroup(btns);

		actionWindow.setVgap(5);
		actionWindow.setHgap(5);

		actionWindow.add(departments, 2, 2);
		actionWindow.add(underGraduate, 2, 0);
		actionWindow.add(graduate, 2, 1);

		actionWindow.add(new Label("Enter the student's name: "), 0, 0);
		actionWindow.add(new Label("Enter the student's ID: "), 0, 1);
		actionWindow.add(new Label("Enter the student's GPA: "), 0, 2);

		TextField name = new TextField();
		TextField ID = new TextField();
		TextField GPA = new TextField();

		actionWindow.add(name, 1, 0);
		actionWindow.add(ID, 1, 1);
		actionWindow.add(GPA, 1, 2);

		Button add = new Button("Add");
		Button back = new Button("Back");

		actionWindow.add(add, 3, 4);
		actionWindow.add(back, 4, 4);

		back.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				secondaryStage.close();

			}
		});

		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (name.getText().trim().isEmpty()) {
					errorWindow("Invalid Name", "Please enter the student's name!");

				} else if (ID.getText().trim().isEmpty()) {
					errorWindow("Invalid ID", "Please enter the student's ID!");

				} else if (GPA.getText().trim().isEmpty()) {
					errorWindow("Invalid Name", "Please enter the student's GPA!");

				} else {

					String stuName = name.getText();
					int stuID = Integer.parseInt(ID.getText());
					double stuGPA = Double.parseDouble(GPA.getText());

					int deptIndex = departments.getSelectionModel().getSelectedIndex();

					if (graduate.isSelected()) {
						dpt.get(deptIndex).addStudent(new Graduate(stuName, stuID, stuGPA));
						errorWindow("Action Complete", "The student has been succesfully added");

					} else if (underGraduate.isSelected()) {
						dpt.get(deptIndex).addStudent(new Undergraduate(stuName, stuID, stuGPA));
						errorWindow("Action Complete", "The student has been succesfully added");

					}
				}
			}
		});

		actionWindow.setAlignment(Pos.CENTER);

		Scene studentAdd = new Scene(actionWindow, 750, 150);

		secondaryStage.setScene(studentAdd);
		secondaryStage.setTitle("Add Student");
		secondaryStage.show();

	}

	public static void deleteStudentScreen(LinkedList<Department> dpt, int index) {

		Stage deleteStage = new Stage();

		GridPane layout = new GridPane();

		layout.setHgap(5);

		ChoiceBox<Student> students = new ChoiceBox<Student>(
				FXCollections.observableList(dpt.get(index).getStudents()));
		layout.add(students, 0, 0);

		Button delete = new Button("Delete");
		layout.add(delete, 1, 0);

		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				int student = students.getSelectionModel().getSelectedIndex();
				dpt.get(index).getStudents().remove(student);
				errorWindow("Action Performed", "Student was succesfully removed");

			}

		});

		Button back = new Button("Back");
		layout.add(back, 2, 0);

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				deleteStage.close();
			}
		});

		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 450, 70);

		deleteStage.setTitle("Delete Student");
		deleteStage.setScene(scene);
		deleteStage.show();
	}

	public static void deleteStudent(LinkedList<Department> dpt) {

		Stage secondaryStage = new Stage();

		GridPane layout = new GridPane();

		layout.setHgap(5);
		layout.setVgap(5);

		ChoiceBox<Department> departments = new ChoiceBox<Department>(FXCollections.observableList(dpt));
		layout.add(departments, 0, 0);

		Button select = new Button("Select");
		layout.add(select, 1, 0);

		select.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				int index = departments.getSelectionModel().getSelectedIndex();

				if (index >= 0)
					deleteStudentScreen(dpt, index);
				else
					errorWindow("Invalid Department", "Please choose a department to proceed");
			}

		});

		Button back = new Button("Back");
		layout.add(back, 2, 0);

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				secondaryStage.close();
			}
		});

		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 370, 70);

		secondaryStage.setTitle("Choose Department");
		secondaryStage.setScene(scene);
		secondaryStage.show();
	}

	public static void swap(LinkedList<Department> dpt, int index, int studentIndex, int newDepartment) {

		Student student = dpt.get(index).getStudents().get(studentIndex);

		dpt.get(index).getStudents().remove(studentIndex);
		dpt.get(newDepartment).addStudent(student);

		errorWindow("Action Succesful", "Student was moved succesfully");

	}

	public static void changingDepartmentScreen(LinkedList<Department> dpt, int index) {

		Stage actionWindow = new Stage();

		GridPane layout = new GridPane();

		layout.setHgap(5);
		layout.setVgap(5);

		@SuppressWarnings("unchecked")
		LinkedList<Department> newDepartment = (LinkedList<Department>) dpt.clone();
		newDepartment.remove(index);

		ChoiceBox<Student> studentsList = new ChoiceBox<Student>(
				FXCollections.observableList(dpt.get(index).getStudents()));
		ChoiceBox<Department> departmentsList = new ChoiceBox<Department>(FXCollections.observableList(newDepartment));

		layout.add(studentsList, 0, 0);
		layout.add(departmentsList, 1, 0);

		Button change = new Button("Change");
		Button back = new Button("Back");

		layout.add(change, 2, 0);
		layout.add(back, 3, 0);

		change.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				int studentIndex = studentsList.getSelectionModel().getSelectedIndex();
				int newDepartment = departmentsList.getSelectionModel().getSelectedIndex();

				swap(dpt, index, studentIndex, newDepartment);
			}
		});

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				actionWindow.close();

			}
		});

		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 700, 300);

		actionWindow.setTitle("Change Department");
		actionWindow.setScene(scene);
		actionWindow.show();
	}

	public static void changeDepartment(LinkedList<Department> dpt) {

		Stage secondaryStage = new Stage();

		GridPane layout = new GridPane();

		layout.setHgap(5);
		layout.setVgap(5);

		ChoiceBox<Department> departments = new ChoiceBox<Department>(FXCollections.observableList(dpt));

		layout.add(departments, 0, 0);

		Button select = new Button("Select");
		Button back = new Button("Back");
		layout.add(select, 1, 0);
		layout.add(back, 2, 0);

		select.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				int index = departments.getSelectionModel().getSelectedIndex();

				if (index >= 0) {

					changingDepartmentScreen(dpt, index);

				} else
					errorWindow("Invalid Department", "Please choose a department to proceed");
			}
		});

		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				secondaryStage.close();
			}

		});

		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 370, 70);

		secondaryStage.setTitle("Choose Department");
		secondaryStage.setScene(scene);
		secondaryStage.show();
	}

	@Override
	public void start(Stage primaryStage) {

		LinkedList<Department> dpt = retrieveData();

		GridPane mainWindow = new GridPane();

		mainWindow.setVgap(5);
		mainWindow.setHgap(5);

		ToggleGroup choices = new ToggleGroup();
		RadioButton[] options = new RadioButton[5];

		options[0] = new RadioButton("List a department's students");
		options[1] = new RadioButton("List all departments and total students");
		options[2] = new RadioButton("Add a new student to a department");
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

					addStudent(dpt);

				} else if (options[3].isSelected()) {

					deleteStudent(dpt);

				} else if (options[4].isSelected()) {

					changeDepartment(dpt);

				} else {
					errorWindow("Input Invalid", "Please select a valid option to proceed!");
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
				Platform.exit();

			}
		});

		mainWindow.setAlignment(Pos.CENTER);

		Scene display = new Scene(mainWindow, 600, 200);

		primaryStage.setTitle("Registration Database");
		primaryStage.setScene(display);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
