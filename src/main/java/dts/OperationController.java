package dts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dts.data.UserRole;
import dts.exceptions.UserNotAdminException;
import dts.exceptions.UserNotManagerException;
import dts.exceptions.UserNotPlayerException;
//import dts.logic.OperationsService;
import dts.logic.EnhancedOperationService;
import dts.logic.EnhancedUserService;

@RestController
public class OperationController {

	private EnhancedOperationService operationsService;
	private EnhancedUserService userService;

	@Autowired
	public void setOperationsService(EnhancedOperationService operationsService,EnhancedUserService userService ) {
		this.operationsService = operationsService;
		this.userService = userService;
	}

	@RequestMapping(path = "/dts/operations", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Object invokeOperation(@RequestBody OperationBoundary operation) {

		UserBoundary loggedInUser = userService.login(operation.getInvokedBy().getSpace(), operation.getInvokedBy().getEmail());
		if (loggedInUser.getRole() != UserRole.PLAYER) {
			throw new UserNotPlayerException("only player can invoke operation");
		}
		else {
			OperationBoundary operationBoudary = new OperationBoundary(operation);
			OperationBoundary rv = (OperationBoundary) this.operationsService.invokeOperation(operationBoudary);
			return rv;
		}

		//		return new JSONObject();
	}

	@RequestMapping(path = "/dts/admin/operations/{adminSpace}/{adminEmail}",
			method = RequestMethod.DELETE)
	public void deleteAllOperations(@PathVariable("adminSpace") String adminSpace,
			@PathVariable("adminEmail") String adminEmail) {
		UserBoundary loggedInUser = userService.login(adminSpace, adminEmail);
		if (loggedInUser.getRole() != UserRole.ADMIN) {
			throw new UserNotAdminException("only admin can delete all operations");
		}
		else {
			this.operationsService.deleteAllActions(adminSpace, adminEmail);
		}
	}

	@RequestMapping(
			path = "/dts/admin/operations/{adminSpace}/{adminEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<OperationBoundary> getAllOperations (
			@PathVariable("adminSpace") String adminSpace, 
			@PathVariable("adminEmail") String adminEmail,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		UserBoundary loggedInUser = userService.login(adminSpace, adminEmail);
		if (loggedInUser.getRole() != UserRole.ADMIN) {
			throw new UserNotAdminException("only admin can get all operations");
		}
		else {
			return this.operationsService.getAllOperationsBoundaries(adminSpace, adminEmail, size, page);
		}
	}

}
