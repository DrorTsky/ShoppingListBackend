package dts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class OperationID {
	
	private String space;
	private String id;
	
	public OperationID() {
		super();
//		this.space = "tempSpace";
//		this.id = "tempId";
	}
	
	public OperationID(String space, String id) {
		super();
		this.space = space;
		this.id = id;
	}

	public OperationID(String entityId) {
		super();
		String seperator = "!!!";
		String emailAndSpace[] = entityId.split(seperator);
		this.id = emailAndSpace[0];
		this.space= emailAndSpace[1];
	}
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String stringifiedOperationId() {
		
		String seperator = "!!!";
		
		return id+seperator+space;
	}
	
	@Override
	public String toString() {
		return "OperationID [space=" + space + ", id=" + id + "]";
	}

}
