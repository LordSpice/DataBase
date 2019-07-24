import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class Department {

	private String code;
	private String name;
	private LinkedList<Student> students = new LinkedList<Student>();

	public Department(String code, String name) {
		this.code = code;
		this.name = name;

		try {
			ObjectInputStream objReader = new ObjectInputStream(new FileInputStream("departments.dat"));

		} catch (FileNotFoundException createFile) {

			System.out.println("File was not found, creating file with default values");
			
			try {
				ObjectOutputStream objCreator = new ObjectOutputStream(new FileOutputStream("departments.dat"));
				objCreator.writeObject(students);
			} catch (IOException e) {
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public LinkedList<Student> getStudents() {
		return students;
	}

	public void setStudents(LinkedList<Student> students) {
		this.students = students;
	}

	public String toString() {
		String details = code + " " + name;
		return details;
	}
}
