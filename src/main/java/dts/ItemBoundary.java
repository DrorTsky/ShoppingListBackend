package dts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemBoundary {

	private ItemID id;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimeStamp;
	private UserId createdBy;
	private Location location;
	private Map<String, Object> itemAttributes;

	public ItemBoundary() {
		System.err.println("in empty constructor");
		this.id = new ItemID();
		this.type = "";
		this.name = "";
		this.active = false;
		this.createdTimeStamp = new Date();
		this.createdBy = new UserId();
		this.location = new Location();
		this.itemAttributes = new HashMap<String, Object>();
		this.itemAttributes.put("key1", "none");
		this.itemAttributes.put("key2", "none");
	}

	public ItemBoundary(ItemBoundary item) {
		
		System.err.println("in copy constructor boundary");
		
		this.id = item.getId();
		if(item.getType() != null && !item.getType().equals("")) {
			this.type = item.getType();
		}else {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "type cannot be null");
		}
		if(item.getName() != null && !item.getName().equals("")) {
			this.name = item.getName();
		}else {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "name cannot be null");
		}
		this.active = item.getActive();
		this.createdTimeStamp = item.getCreatedTimeStamp();
		this.createdBy = item.getCreatedBy();
		this.location = item.getLocation();
		this.itemAttributes = item.getItemAttributes(); //empty map
		this.itemAttributes.put("key1", item.itemAttributes.get("key1"));
		this.itemAttributes.put("key2", item.itemAttributes.get("key2"));
		System.err.println("----attrr in copy---- "+this.itemAttributes);
	}

	public ItemBoundary(ItemID id, String type, String name, Boolean active, Date createdTimeStamp,
			UserId createdBy, Location location, Map<String, Object> itemAttributes) {
		super();
//		System.err.println("in constructor");
		this.id = id;
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimeStamp = createdTimeStamp;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
	}

	public ItemBoundary(String type, String name, Boolean active, Date createdTimeStamp, UserId createdBy,
			Location location, Map<String, Object> itemAttributes) {
		super();
		this.type = type;
		this.name = name;
		this.active = active;
		this.createdTimeStamp = createdTimeStamp;
		this.createdBy = createdBy;
		this.location = location;
		this.itemAttributes = itemAttributes;
	}

	public String generateEntityId() {

		String seperator = "!!!";

		return this.id.getId() + seperator + this.id.getSpace();
	}

	public ItemID getId() {
		return id;
	}

	public void setId(ItemID id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public UserId getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserId createdBy) {
		this.createdBy = createdBy;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, Object> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(Map<String, Object> attr) {
		this.itemAttributes = attr;
	}

	@Override
	public String toString() {
		return "ItemBoundary [itemId=" + id + ", type=" + type + ", name=" + name + ", active=" + active
				+ ", createdTimeStamp=" + createdTimeStamp + ", createdBy=" + createdBy + ", location=" + location
				+ ", itemAttributes=" + itemAttributes + "]";
	}

}