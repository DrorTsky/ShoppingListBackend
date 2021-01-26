package dts;

public class ManagerID {

	private String email;
	private String space;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public ManagerID(String email, String space) {
		super();
		this.email = email;
		this.space = space;
	}

	@Override
	public String toString() {
		return "ManagerID [email=" + email + ", space=" + space + "]";
	}

}
