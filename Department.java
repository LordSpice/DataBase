
/*import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;*/
import java.util.LinkedList;

public class Department {

	private String code;
	private String name;
	private LinkedList<Student> students = new LinkedList<Student>();

	public Department(String code, String name) {
		this.code = code;
		this.name = name;

		/*
		 * try { ObjectInputStream objReader = new ObjectInputStream(new
		 * FileInputStream("departments.dat"));
		 * 
		 * } catch (FileNotFoundException createFile) {
		 * 
		 * System.out.println("File was not found, creating file with default values");
		 * 
		 * try { ObjectOutputStream objCreator = new ObjectOutputStream(new
		 * FileOutputStream("departments.dat")); objCreator.writeObject(students);
		 * objCreator.close();
		 * 
		 * } catch (IOException e) { }
		 * 
		 * } catch (IOException e) { System.out.println(e.getMessage()); }
		 */
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

	public void addStudent(Student student) {
		students.add(student);
	}

	/*
	 * public static void main(String[] args) { LinkedList<Department> dpt; try {
	 * ObjectInputStream objReader = new ObjectInputStream(new
	 * FileInputStream("departments.dat"));
	 * 
	 * LinkedList<Department> readObject = extracted(objReader);
	 * LinkedList<Department> temp = readObject;
	 * 
	 * System.out.println(temp); objReader.close();
	 * 
	 * } catch (FileNotFoundException createFile) {
	 * 
	 * System.out.println("File was not found, creating file with default values");
	 * 
	 * } catch (IOException e) { } catch (ClassNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * private static LinkedList<Department> extracted(ObjectInputStream objReader)
	 * throws IOException, ClassNotFoundException { return (LinkedList<Department>)
	 * objReader.readObject(); }
	 */
}
