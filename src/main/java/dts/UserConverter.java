package dts;

import java.util.Map;

import org.springframework.stereotype.Component;

import dts.UserBoundary;
import dts.UserId;
import dts.data.UserEntity;
import dts.data.UserRole;


@Component
public class UserConverter {
	
	/* Boundary
	private String id;
	private String userName;
	private String role;
	private String avatar;
	 */
	
	/*
	private String id;
	private String userName;
	private UserRole role;
	private String avatar;
	 */
	
	public UserEntity convertToEntity(UserBoundary boundary) {
		
		//create new UserEntity
		UserEntity rv = new UserEntity();
		
		//set entity ID
		rv.setId(boundary.generateEntityId());
		
		//set entity userName
		rv.setUserName(boundary.getUsername());
		
		//set entity avatar
		rv.setAvatar(boundary.getAvatar());
		
		//set entity role
		//TODO
		if (boundary.getRole() != null) {
			rv.setRole(boundary.getRole());
		}
		
		return rv;
	}
	
	
	
	public UserBoundary convertToBoundary(UserEntity entity) {

		//create new UserBoundary
		UserBoundary rv = new UserBoundary();
		//get email and space from entity id
		String userEmailAndSpace[] = entity.getId().split("!!!");
//		System.err.println("in converter line 61");
//		System.err.println(userEmailAndSpace[0]+ " "+ userEmailAndSpace[1]);
		
		//set UserBoundary Id
		rv.setUserId(new UserId(userEmailAndSpace[0],userEmailAndSpace[1]));
//		System.err.println("in converter line 66");
//		System.err.println(rv.getUserId());
		//set UserBoundary userName
		rv.setUsername(entity.getUserName());
//		System.err.println("in converter line 70");
//		System.err.println(rv.getUsername());
		
		//set UserBoundary avatar
		rv.setAvatar(entity.getAvatar());
//		System.err.println("in converter line 75");
//		System.err.println(rv.getAvatar());
		//set UserBoundary role
		rv.setRole(entity.getRole());		
//		System.err.println("in converter line 79");
//		System.err.println(rv.getRole());
		return rv;
	}
	
}
