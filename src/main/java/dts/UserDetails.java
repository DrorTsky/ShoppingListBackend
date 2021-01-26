package dts;

import dts.data.UserRole;

public class UserDetails {
	
	private String email;
	private UserRole role;
	private String username;
	private String avatar;
	



	public UserDetails(String email, UserRole role, String username, String avatar) {
		super();
		this.email = email;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
	}

	public UserDetails() {
		// TODO Auto-generated constructor stub
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public UserRole getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		return "UserDetails [email=" + email + ", role=" + role + ", username=" + username + ", avatar=" + avatar + "]";
	}

	

}
