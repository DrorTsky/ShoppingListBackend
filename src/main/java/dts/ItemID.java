package dts;

public class ItemID {
	
	private String space;
	private String id;
	
	public ItemID() {
		super();
//		this.space = "tempSpace";
//		this.id = "tempId";
	}
	
	public ItemID(String id, String space) {
		super();
		this.id = id;
		this.space = space;
	}
	
	public ItemID(String entityId) {
		super();
		String seperator = "!!!";
		String emailAndSpace[] = entityId.split(seperator);
		this.id = emailAndSpace[0];
		this.space= emailAndSpace[1];
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}
	
	public String stringifiedItemId() {
		String seperator = "!!!";
		return id+seperator+space;
	}

	@Override
	public String toString() {
		return "ItemID [space=" + space + ", id=" + id + "]";
	}

}