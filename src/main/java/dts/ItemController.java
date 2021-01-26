package dts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dts.data.UserRole;
import dts.exceptions.UserNotAdminException;
import dts.exceptions.UserNotManagerException;
import dts.logic.EnhancedItemsService;
import dts.logic.EnhancedUserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ItemController {

	private EnhancedItemsService itemsService;
	private EnhancedUserService userService;


	@Autowired
	public ItemController(EnhancedItemsService itemsService, EnhancedUserService userService) {
		super();
		this.itemsService = itemsService;
		this.userService = userService;
	}

	@RequestMapping(
			path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}/children",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void connectItems (
			@PathVariable("managerSpace") String adminSpace, 
			@PathVariable("managerEmail") String adminEmail,
			@PathVariable("itemSpace") String itemSpace, 
			@PathVariable("itemId") String itemId,
			@RequestBody ItemIdBoundary itemIdBoundary
			){
		ItemIdBoundary newItemIdBoundary = new ItemIdBoundary(itemIdBoundary);
		this.itemsService
		.connectItems(adminSpace,adminEmail,itemId,itemSpace,newItemIdBoundary);
	}

	@RequestMapping(
			path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/children",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getResponses (
			@PathVariable("userSpace") String userSpace, 
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace, 
			@PathVariable("itemId") String itemId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.itemsService
				.getItemChildren(userSpace,userEmail,itemId,itemSpace,size,page)
				.toArray(new ItemBoundary[0]);
	}

	
	@RequestMapping(
			path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}/parents",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getOriginal (
			@PathVariable("userSpace") String userSpace, 
			@PathVariable("userEmail") String userEmail,
			@PathVariable("itemSpace") String itemSpace, 
			@PathVariable("itemId") String itemId,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.itemsService
				.getItemParent(userSpace,userEmail,itemId,itemSpace,size,page).toArray(new ItemBoundary[0]);

	}

	@RequestMapping(path = "/dts/items/{managerSpace}/{managerEmail}", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary newItem(@PathVariable("managerSpace") String managerSpace, 
			@PathVariable("managerEmail") String managerEmail,
			@RequestBody ItemBoundary item) {

		UserBoundary loggedInUser = userService.login(managerSpace, managerEmail);
		//System.err.println("----role----"+ loggedInUser.getRole());
		if (loggedInUser.getRole() != UserRole.MANAGER) {
			throw new UserNotManagerException("only manager can create item");
		}
		else {
			// creates new item with ItemDetails
			ItemBoundary itemBoundary = new ItemBoundary(item);
			// Create user using userService
			ItemBoundary result = this.itemsService.create(managerSpace,managerEmail,itemBoundary);
			return result;
		}
	}

	@RequestMapping(path = "/dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}", 
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateItem(@PathVariable("managerSpace") String space, 
			@PathVariable("managerEmail") String email,
			@PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId,
			@RequestBody ItemBoundary itemToUpdate) {
		UserBoundary loggedInUser = userService.login(space, email);
		//System.err.println("----role----"+ loggedInUser.getRole());
		if (loggedInUser.getRole() != UserRole.MANAGER) {
			throw new UserNotManagerException("only manager can update item");
		}
		else {
			ItemBoundary itemBoundary = new ItemBoundary(itemToUpdate);
			ItemBoundary result = this.itemsService.update(space, email, itemSpace, itemId, itemToUpdate);
		}
	}

	@RequestMapping(path = "/dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary getSpecificItem(@PathVariable("userSpace") String space,
			@PathVariable("userEmail") String email, @PathVariable("itemSpace") String itemSpace,
			@PathVariable("itemId") String itemId){
		
		UserBoundary loggedInUser = userService.login(space, email);
		//System.err.println("----role----"+ loggedInUser.getRole());
		if (loggedInUser.getRole() != UserRole.MANAGER && loggedInUser.getRole() != UserRole.PLAYER) {
			throw new UserNotManagerException("only manager or can get specific item");
		}
		else {
			ItemBoundary item = this.itemsService.getSpecificItem(space,email,itemSpace,itemId);
			if (item.getActive() == true) {
				return item;
			}
			if (loggedInUser.getRole() != UserRole.PLAYER) {
				return item;
			}
			throw new UserNotManagerException("player can only access to active items");
		}
	}

	@RequestMapping(path = "dts/items/{userSpace}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ItemBoundary> getAllItems(@PathVariable("userSpace") String space,
			@PathVariable("userEmail") String email,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {

		
		UserBoundary loggedInUser = userService.login(space, email);
		List<ItemBoundary> activeItems = new ArrayList<ItemBoundary>();
		
		if (loggedInUser.getRole() != UserRole.MANAGER && loggedInUser.getRole() != UserRole.PLAYER) {
			throw new UserNotManagerException("only manager or player can get all items");
		}
		else {
			List<ItemBoundary> items = this.itemsService.getAll(space,email,size,page);
			for (ItemBoundary item : items) {
				if (item.getActive() == true) {
					activeItems.add(item);
				}
			}
			if (loggedInUser.getRole() != UserRole.PLAYER) {
				return items;
			}
			return activeItems;
		}
	}


	@RequestMapping(path = "/dts/admin/items/{adminSpace}/{adminEmail}", method = RequestMethod.DELETE)
	public void deleteAllItems(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		
		UserBoundary loggedInUser = userService.login(adminSpace, adminEmail);
		if (loggedInUser.getRole() != UserRole.ADMIN) {
			throw new UserNotAdminException("only admin can delete all items");
		}
		else {
			this.itemsService.deleteAll(adminSpace,adminEmail);
		}
	}

	@RequestMapping(path = "/dts/items/{userSpace}/{userEmail}/search/byNamePattern/{namePattern}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getAllItemsByNamePattern(
			@PathVariable("userSpace") String space,
			@PathVariable("userEmail") String email,
			@PathVariable("namePattern") String pattern,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {

		UserBoundary loggedInUser = userService.login(space, email);
		ArrayList<ItemBoundary> activeItems = new ArrayList<ItemBoundary>();
		
		if (loggedInUser.getRole() != UserRole.MANAGER && loggedInUser.getRole() != UserRole.PLAYER) {
			throw new UserNotManagerException("only manager or player can get specific items");
		}
		else {
			ItemBoundary[] items = this.itemsService.getAllItemByNamePattern(space,email,pattern, size, page).toArray(new ItemBoundary[0]);
			for (ItemBoundary item : items) {
				if (item.getActive() == true) {
					activeItems.add(item);
				}
			}
			if (loggedInUser.getRole() != UserRole.PLAYER) {
				return items;
			}
			return (ItemBoundary[]) activeItems.toArray();
		}
	}

	@RequestMapping(path = "/dts/items/{userSpace}/{userEmail}/search/byType/{type}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getAllItemsByType(
			@PathVariable("userSpace") String space,
			@PathVariable("userEmail") String email,
			@PathVariable("type") String type,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		
		UserBoundary loggedInUser = userService.login(space, email);
		ArrayList<ItemBoundary> activeItems = new ArrayList<ItemBoundary>();
		
		if (loggedInUser.getRole() != UserRole.MANAGER && loggedInUser.getRole() != UserRole.PLAYER) {
			throw new UserNotManagerException("only manager or player can get specific items");
		}
		else {
			ItemBoundary[] items = this.itemsService.getAllItemByTypePattern(space,email,type, size, page).toArray(new ItemBoundary[0]);
			for (ItemBoundary item : items) {
				if (item.getActive() == true) {
					activeItems.add(item);
				}
			}
			if (loggedInUser.getRole() != UserRole.PLAYER) {
				return items;
			}
			return (ItemBoundary[]) activeItems.toArray();
		}		
	}

	@RequestMapping(path = "/dts/items/{userSpace}/{userEmail}/search/near/{lat}/{lng}/{distance}", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getAllItemsInRange(
			@PathVariable("userSpace") String space,
			@PathVariable("userEmail") String email,
			@PathVariable("lng") double lng,
			@PathVariable("lat") double lat,
			@PathVariable("distance") int distance,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page
			) {
		
		UserBoundary loggedInUser = userService.login(space, email);
		ArrayList<ItemBoundary> activeItems = new ArrayList<ItemBoundary>();
		
		if (loggedInUser.getRole() != UserRole.MANAGER && loggedInUser.getRole() != UserRole.PLAYER) {
			throw new UserNotManagerException("only manager or player can get specific items");
		}
		else {
			ItemBoundary[] items = this.itemsService.getAllItemsInRange(space, email, lng, lat, distance, size, page).toArray(new ItemBoundary[0]);
			for (ItemBoundary item : items) {
				if (item.getActive() == true) {
					activeItems.add(item);
				}
			}
			if (loggedInUser.getRole() != UserRole.PLAYER) {
				return items;
			}
			return (ItemBoundary[]) activeItems.toArray();
		}	
	}


}