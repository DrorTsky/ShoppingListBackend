package dts.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dts.OperationBoundary;
import dts.UserBoundary;
import dts.UserConverter;
import dts.UserId;
import dts.data.UserEntity;
import dts.exceptions.UserNotAdminException;
import dts.exceptions.UserNotFoundException;


//@Service
public class MockupUsersService implements UsersService{
	
	private Map<String, UserEntity> users;
	private String space;
	private UserConverter userConverter;
	
	@PostConstruct
	public void init() {
		
		this.users = Collections.synchronizedMap(new HashMap<>());
		
	}
	
	@Value("${spring.application.name:ShoppingList}")
	public void setInitProperty(String space) {
		this.space = space;
	}

	@Autowired
	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}


	@Override
	public UserBoundary createUser(UserBoundary newUser) {
		
		//sets user id space to be teams space
		newUser.setUserId(new UserId(newUser.getUserId().getEmail(),this.space)); 

		//create new user entity to store in mock-up "database"
		UserEntity entity = this.userConverter.convertToEntity(newUser); 

		//stores the entity in the mock-up "database"
		this.users.put(entity.getId(), entity); 
		
		//creates new and updated user boundary to return
		UserBoundary rv = this.userConverter.convertToBoundary(entity); 

		//returns new user boundary
		return rv; 
	}

	@Override
	public UserBoundary login(String userSpace, String userEmail) { //same as get user by user Id
		String id = userEmail+"!!!"+userSpace;
		
		// gets user entity from the database
		UserEntity entity = this.users.get(id);
		if(entity != null) {
			// converts retrieved entity to UserBoudary and returns it
			return this.userConverter.convertToBoundary(entity);
		}else {
			// if not found throw exception
			throw new UserNotFoundException("could not find user with id: " + id);
		}
	}

	@Override
	public void updateUser(String userSpace, String userEmail, UserBoundary update) {
		// generate entity id
		String id = userEmail+"!!!"+userSpace;
		// find the specific entity using id
		UserEntity entity = this.users.get(id);
		
		if(entity != null) {
			// creates new entity from updated UserBoundary
			UserEntity updatedEntity = this.userConverter.convertToEntity(update);
			// updates the UsetEntity with given id
			this.users.put(id, updatedEntity);
			// returns updated UserBoundary
//			return this.userConverter.convertToBoundary(updatedEntity);
		}else {
			// if not found throw exception
			throw new UserNotFoundException("could not find user with id: " + id);
		}		
	}

	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		List<UserBoundary> allUsers =  this.users.values().stream().map(userConverter::convertToBoundary).collect(Collectors.toList());
		return allUsers;
	}

	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		// clears users synchronized map
		this.users.clear();		
	}
}
