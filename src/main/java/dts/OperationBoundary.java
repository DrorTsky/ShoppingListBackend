package dts;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OperationBoundary {

	private OperationID operationId;
	private String type;
	private ItemID item;
	private Date createdTimeStamp;
	private UserId invokedBy;
	private OperationAttributes operationAttributes;

	public OperationID getOperationId() {
		return operationId;
	}

	public void setOperationId(OperationID operationId) {
		this.operationId = operationId;
	}

	public OperationBoundary(OperationID operationId, String type, ItemID item, Date createdTimeStamp, UserId invokedBy,
			OperationAttributes operationAttributes) {
		super();
		this.operationId = operationId;
		this.type = type;
		this.item = item;
		this.createdTimeStamp = createdTimeStamp;
		this.invokedBy = invokedBy;
		this.operationAttributes = operationAttributes;
	}
	
	public OperationBoundary(OperationBoundary operation) {
//		System.err.println(operation.toString());
		this.operationId = operation.getOperationId();
		if(operation.getType() != null && !operation.getType().equals("")) {
			this.type = operation.getType();			
		}else {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "type cannot be null");
		}
		this.item = operation.getItem();
		this.createdTimeStamp = operation.getCreatedTimeStamp();
		this.invokedBy = operation.getInvokedBy();
		this.operationAttributes = operation.getOperationAttributes();
	}

	public OperationBoundary() {
		this.operationId = new OperationID("tempSpace", "tempID");
		this.type = "tempType";
		this.item = new ItemID("tempID", "tempSpace");
		this.createdTimeStamp = new Date();
		this.invokedBy = new UserId("tempEmail", "tempSpace");
		this.operationAttributes = new OperationAttributes("something!!!something");
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ItemID getItem() {
		return item;
	}

	public void setItem(ItemID item) {
		this.item = item;
	}

	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public UserId getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(UserId invokedBy) {
		this.invokedBy = invokedBy;
	}

	public OperationAttributes getOperationAttributes() {
		return operationAttributes;
	}

	public void setOperationAttributes(OperationAttributes operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

	@Override
	public String toString() {
		return "OperationBoundary [operationId=" + operationId + ", type=" + type + ", item=" + item
				+ ", createdTimeStamp=" + createdTimeStamp + ", invokedBy=" + invokedBy + ", operationAttributes="
				+ operationAttributes + "]";
	}

}
