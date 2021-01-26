package dts;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import dts.data.UserRole;

public class UserBoundary {
	
	private UserId userId;
	private UserRole role;
	private String username;
	private String avatar;
	


	public UserBoundary(UserId id, String username, UserRole role, String avatar) {
		super();
		this.userId = new UserId("temp@temp.com", "temp");
		this.username = username;
		this.role = role;
		this.avatar = avatar;
	}
	
	public UserBoundary() {
		this.userId = new UserId("temp@temp.temp", "temp");
		this.username = "temp";
		this.role = UserRole.PLAYER;
		this.avatar = "temp";
	}

	public UserBoundary(UserBoundary update) {
//		System.err.println("in user boundary line 37");
//		System.err.println(update.getUserId());
		this.userId = new UserId(update.getUserId().getEmail(),"temp");
		this.role = update.getRole();
		this.username = update.getUsername();
		this.avatar = update.getAvatar();
	}

	public UserBoundary(UserDetails user) {
		if(user.getEmail().contains("@")) {
			this.userId = new UserId(user.getEmail(), "temp");
		}else {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "bad emails address");
		}
		if(user.getUsername() != null && !user.getUsername().equals("")) {
			this.username = user.getUsername();			
		}else{
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "username cannot be null");
		}
		this.role = user.getRole();
		if(user.getAvatar() != null && !user.getAvatar().equals("")) {
			this.avatar = user.getAvatar();			
		}else {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "avatar cannot be null");
		}
	}


	public String generateEntityId() {
		
//		String seperator = "@@@";
		
//		return this.id.getEmail()+seperator+this.id.getSpace();
		return this.userId.stringifiedUserId();
		
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId id) {
		this.userId = id;
	}



	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	@Override
	public String toString() {
		return "UserBoundary [userId=" + userId + ", role=" + role + ", username=" + username + ", avatar=" + avatar + "]";
	}


	

	
	
}