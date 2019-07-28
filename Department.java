
/*import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;*/
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private LinkedList<Student> students = new LinkedList<Student>();

	public Department(String code, String name) {
		this.code = code;
		this.name = name;

		/* try {
			ObjectInputStream objReader = new ObjectInputStream(new FileInputStream("departments.dat"));

		} catch (FileNotFoundException createFile) {

			System.out.println("File was not found, creating file with default values");

			try {
				ObjectOutputStream objCreator = new ObjectOutputStream(new FileOutputStream("departments.dat"));
				objCreator.writeObject(students);
				objCreator.close();

			} catch (IOException e) {
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} */

	}
	
	static final Comparator<Student> ID_ORDER = new Comparator<Student>() {

		@Override
		public int compare(Student stu1, Student stu2) {

			int ID1 = stu1.getId();
			int ID2 = stu2.getId();

			if (ID1 == ID2)
				return 0;
			else if (ID1 > ID2)
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

	public void sortStudents(int order) {

		if (order == 0)
			Collections.sort(students, ID_ORDER);
		else if (order == 1)
			Collections.sort(students, GPA_ORDER);
		else if (order == 2)
			Collections.sort(students, NAME_ORDER);

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
