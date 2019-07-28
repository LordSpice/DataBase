
public class Graduate extends Student {

	private static final long serialVersionUID = 1L;

	public Graduate(String name, int id, double GPA) {
		super(name, id, GPA);
	}
	
	@Override
	public String getStatus() {
		String standing = "Good";
		
		if (getGPA() < 3.0)
			standing = "Probation";
		
		return standing;
	}
}
