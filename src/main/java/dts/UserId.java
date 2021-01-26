package dts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class UserId {

	private String space;
	private String email;

	
	public UserId() {
		super();
		this.email = "tempEmail";
		this.space= "tempSpace";
	}
	
	public UserId(String email, String space) {
		super();
		this.email = email;
		this.space = space;
	}
	
	public UserId(String entityId) {
		super();
		String seperator = "!!!";
		String emailAndSpace[] = entityId.split(seperator);
		this.email = emailAndSpace[0];
		this.space= emailAndSpace[1];
	}
	
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
	
	public String stringifiedUserId() {
		
		String seperator = "!!!";
		return email+seperator+space;
	}
	
	@Override
	public String toString() {
		return "UserId [email=" + email + ", space=" + space + "]";
	}

}