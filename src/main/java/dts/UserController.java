package dts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dts.data.UserRole;
import dts.exceptions.UserNotAdminException;
import dts.exceptions.UserNotFoundException;
import dts.exceptions.UserNotManagerException;
import dts.logic.EnhancedUserService;
import dts.logic.UsersService;

//@CrossOrigin(origins = "http://localhost:3000/friends")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

	private EnhancedUserService userService;

	@Autowired
	public void setUsersService(EnhancedUserService userService) {
		this.userService = userService;
	}

	@RequestMapping(path = "/dts/users",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary newUser(@RequestBody UserDetails user) {
		//creates new user with UserDetails
		UserBoundary userBoundary = new UserBoundary(user);
		//Create user using userService
		UserBoundary result = this.userService.createUser(userBoundary);
		return result;

	}

	@RequestMapping(path = "/dts/user/login/{userSpace}/{userEmail}",
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(@PathVariable("userSpace") String userSpace, 
			@PathVariable("userEmail") String userEmail) {
		try {
			return this.userService.login(userSpace, userEmail);

		}catch(RuntimeException ex) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Provide correct user Id", ex);
		}
	}

	@RequestMapping(path = "/dts/user/{userSpace}/{userEmail}", 
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable("userSpace") String userSpace, 
			@PathVariable("userEmail") String userEmail,
			@RequestBody UserBoundary update) {
		//		System.err.println(update.toString());
		UserBoundary temp = new UserBoundary(update);
		//		System.err.println(update.toString());
		this.userService.updateUser(userSpace, userEmail, temp);
		//		System.err.println("userSpace = " + userSpace + ", userEmail = " + userEmail);

	}

	@RequestMapping(path = "/dts/admin/users/{adminSpace}/{adminEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllUsers(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {

		UserBoundary loggedInUser = userService.login(adminSpace, adminEmail);
		if (loggedInUser.getRole() != UserRole.ADMIN) {
			throw new UserNotAdminException("only admin can delete all users");
		}
		else {	
			this.userService.deleteAllUsers(adminSpace, adminEmail);
		}

	}

	
	@RequestMapping(
			path = "/dts/admin/users/{adminSpace}/{adminEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserBoundary> getAllUsers (
			@PathVariable("adminSpace") String adminSpace, 
			@PathVariable("adminEmail") String adminEmail,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		UserBoundary loggedInUser = userService.login(adminSpace, adminEmail);
		if (loggedInUser.getRole() != UserRole.ADMIN) {
			throw new UserNotAdminException("only admin can get all users");
		} else {
			return this.userService.getAll(adminSpace, adminEmail, size, page);
		}
	}
}
