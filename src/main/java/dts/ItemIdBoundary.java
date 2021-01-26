package dts;

public class ItemIdBoundary {
	
	private String space;
	private String id;
	
	

	public ItemIdBoundary(String itemIdSpace, String itemIdID) {
		super();
		this.space = itemIdSpace;
		this.id = itemIdID;
	}
	
	public ItemIdBoundary() {
		// TODO Auto-generated constructor stub
	}


	public ItemIdBoundary(ItemIdBoundary itemIdBoundary) {
		this.space = itemIdBoundary.getSpace();
		this.id = itemIdBoundary.getId();
	}

	public String getSpace() {
		return space;
	}



	public void setSpace(String itemIdSpace) {
		this.space = itemIdSpace;
	}



	public String getId() {
		return id;
	}



	public void setId(String itemIdID) {
		this.id = itemIdID;
	}

	
	@Override
	public String toString() {
		return "ItemIdBoundary [itemIdSpace=" + space + ", itemIdID=" + id + "]";
	}

	public String getEntityLikeID() {
		String seperator ="!!!";
		return id+seperator+space;
	}


}
