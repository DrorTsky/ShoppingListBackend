package dts.dal;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dts.UserBoundary;
import dts.UserConverter;
import dts.UserId;
import dts.data.UserEntity;
import dts.exceptions.UserNotFoundException;
import dts.logic.EnhancedUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
public class RdbUserService implements EnhancedUserService{

	private String space;
	private UserConverter userConverter;
	private UserDao userDao;

	private Log log = LogFactory.getLog(RdbUserService.class);
	
	public RdbUserService(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Autowired
	public void setUserConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}


	@Value("${spring.application.name:ShoppingList}")
	public void setInitProperty(String space) {
		this.space = space;

	}
	
	@Transactional
	@Override
	public void updateUser(String userSpace, String userEmail, UserBoundary update) {
		String idToFind = userEmail + "!!!" + userSpace ;
		String userId = update.generateEntityId();
//		System.err.println("in update line 54");
		UserEntity oldUser= this.userDao.findById(idToFind).orElseThrow(()->
			new UserNotFoundException("could not found: " + idToFind));
//		System.err.println(oldUser.toString());
//		System.err.println("in update line 57");
//		if (update.getUserId() != null) {
//			//String userId = update.getUserId().toString();
//				oldUser.setId(update.generateEntityId());
//		}
//		System.err.println(oldUser.toString());
		
		if (update.getUsername()!=null) {
			oldUser.setUserName(update.getUsername());
		}
		
		if (update.getRole()!=null) {
			oldUser.setRole(update.getRole());
		}
		
		if (update.getAvatar()!= null) {
			oldUser.setAvatar(update.getAvatar());
		}
//		System.err.println("in update line 74");
		oldUser = this.userDao.save(oldUser);
//		System.err.println("in update line 76");
//		System.err.println(oldUser.toString());

//		return this.userConverter.convertToBoundary(oldUser);	
		
		// Log
		this.log.debug("User updated. new user Id: " + userId);
	}

	@Transactional
	@Override
	public UserBoundary createUser(UserBoundary userBoundary) {
		//sets user id space to be teams space
		userBoundary.setUserId(new UserId(userBoundary.getUserId().getEmail(),this.space)); 

		//create new user entity to store in mock-up "database"
		UserEntity entity = this.userConverter.convertToEntity(userBoundary); 
		
		entity = this.userDao.save(entity);
		UserBoundary rv = this.userConverter.convertToBoundary(entity);
		
		// Log
		this.log.debug("New user created. Id: " + entity.getId() + ". Username: " + entity.getUserName());
				
		return rv;		
	}

	@Transactional (readOnly = true)
	@Override
	public UserBoundary login(String space, String email) {
		String id = email+"!!!"+space;

		Optional<UserEntity> optional = this.userDao
				.findById(id);
			if (optional.isPresent()) {
				UserEntity entity = optional.get();
				
				// Log
				this.log.debug("New user login. Id: " + entity.getId());
				
				return this.userConverter.convertToBoundary(entity);
			}else {
				// Log
				this.log.error("Error while login with Id: " + id);
				
				throw new UserNotFoundException("could not find user with id: " + id);
			}
	}

	@Transactional
	@Override
	public void deleteAllUsers(String adminSpace, String adminEmail) {
		// Log
		this.log.debug("All users deleted");
		this.userDao.deleteAll();		
	}

	@Transactional (readOnly = true)
	@Override
	public List<UserBoundary> getAllUsers(String adminSpace, String adminEmail) {
		Iterable<UserEntity> entities =  this.userDao.findAll();

		List<UserBoundary> rv = new LinkedList<>();
		for(UserEntity entity : entities) {
			UserBoundary temp = this.userConverter.convertToBoundary(entity);
			rv.add(temp);
		}

		// Log
		this.log.debug("Getting all users");
				
		return rv;
	}

	@Transactional (readOnly = true)
	@Override
	public List<UserBoundary> getAll(String adminSpace, String adminEmail, int size, int page) {
		// Log
		this.log.debug("Getting all users");
		
		return this.userDao
				.findAll(PageRequest.of(page, size, Direction.DESC, "id"))
				.stream() // Stream<ItemEntity>
				.map(this.userConverter::convertToBoundary)// Stream<UserBoundary>
				.collect(Collectors.toList());// List<UserBoundary>
	}

	

}
