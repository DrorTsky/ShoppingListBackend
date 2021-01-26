package dts;

import org.springframework.stereotype.Component;

import dts.data.OperationEntity;

@Component
public class OperationConverter {
	
	/* OPERATION BOUNDARY
	private OperationID operationId;
	private String type;
	private ItemID item;
	private Date createdTimeStamp;
	private UserId invokedBy;
	private OperationAttributes operationAttributes;
	 */
	
	/* OPERATION ENTITY
	private String operationId;
	private String type;
	private String item;
	private Date createdTimeStamp;
	private String invokedBy;
	private String operationAttributes;
	 */

	public OperationEntity convertToEntity(OperationBoundary operation) {
		OperationEntity rv = new OperationEntity();
		
		if(operation.getOperationId() != null) {
			rv.setId(operation.getOperationId().stringifiedOperationId());			
		}else {
			rv.setId(null);
		}
		
		rv.setType(operation.getType());
		
		if(operation.getItem() != null) {
			rv.setItem(operation.getItem().stringifiedItemId());			
		}else {
			rv.setItem(null);
		}
		
		rv.setCreatedTimeStamp(operation.getCreatedTimeStamp());
		
		if(operation.getInvokedBy() != null) {
			rv.setInvokedBy(operation.getInvokedBy().stringifiedUserId());			
		}else {
			rv.setInvokedBy(null);
		}
		
		if(operation.getOperationAttributes() != null) {
			rv.setOperationAttributes(operation.getOperationAttributes().stringifiedOperationAttributes());
		}else {
			rv.setOperationAttributes(null);
		}
		
		return rv;
	}

	public OperationBoundary convertToBoundary(OperationEntity operationEntity) {
		
		OperationBoundary rv = new OperationBoundary();
		
		rv.setCreatedTimeStamp(operationEntity.getCreatedTimeStamp());
		
		rv.setInvokedBy(new UserId(operationEntity.getInvokedBy()));
		
		rv.setItem(new ItemID(operationEntity.getItem()));
		
		rv.setOperationAttributes(new OperationAttributes(operationEntity.getOperationAttributes()));
		
		rv.setOperationId(new OperationID(operationEntity.getId()));
		
		rv.setType(operationEntity.getType());
		
		return rv;
	}
	
	

}
