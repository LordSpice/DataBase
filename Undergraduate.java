
public class Undergraduate extends Student {

	private static final long serialVersionUID = 1L;

	public Undergraduate(String name, int id, double GPA) {
		super(name, id, GPA);
	}

	@Override
	public String getStatus() {
		String standing = "Good";

		if (getGPA() >= 3.0)
			standing = "Honour";
		else if (getGPA() < 2.0)
			standing = "Probation";

		return standing;
	}
}
