package dts.logic;

import java.util.List;

import dts.OperationBoundary;
import dts.UserBoundary;

public interface EnhancedUserService extends UsersService{

	UserBoundary createUser(UserBoundary userBoundary);

	UserBoundary login(String space, String email);

	void deleteAllUsers(String adminSpace, String adminEmail);

	List<UserBoundary> getAllUsers(String adminSpace, String adminEmail);
	
	public List<UserBoundary> getAll(String adminSpace, String adminEmail, int size, int page);


}
