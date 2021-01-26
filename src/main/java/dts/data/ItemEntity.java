package dts.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import dts.ItemBoundary;

//@EnableRelMongo 
@Document(collection = "ITEMS")
public class ItemEntity {

	private String id;
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimeStamp;
	private String createdBy;
	private Double lng; 
	private Double lat; 

	private String attr;

	//@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL /*, cascade = CascadeType.ALL*/)
    //@JoinProperty(name = "children")
	@DBRef(lazy = true, db = "children")
	private Set<ItemEntity> children = new HashSet<ItemEntity>();

	//@ManyToOne(fetch = FetchType.LAZY)
    //@JoinProperty(name = "parent")
	@DBRef(lazy = true, db = "parent")
	private ItemEntity parent;
	
	public ItemEntity() {
		
	}
	
	@Id
	public String getId() {
		return id;
	}
	
	@Id
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getLat() {
		return lat;
	}
	
	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Double getLng() {
		return lng;
	}
	
	public void setLng(Double lng) {
		this.lng = lng;
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

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Lob
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	
	@Override
	public String toString() {
		return "ItemEntity [itemId=" + id + ", type=" + type + ", name=" + name + ", active=" + active
				+ ", createdTimeStamp=" + createdTimeStamp + ", createdBy=" + createdBy + ", lng=" + lng + ", lat="
				+ lat + ", attr=" + attr + ", children=" + children + ", parent=" + parent + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemEntity other = (ItemEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// maps a relationship to database
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent" /*, cascade = CascadeType.ALL*/)
	public Set<ItemEntity> getChildren() {
		return children;
	}
	
	public void setChildren(Set<ItemEntity> children) {
		this.children = children;
	}

	
	//@ManyToOne(fetch = FetchType.LAZY /*, cascade = CascadeType.ALL*/)
	public ItemEntity getParent() {
		return parent;
	}

	public void addChildren(ItemEntity child) {
		System.err.println(child.toString());
		this.children.add(child);
		System.err.println(children.toString());
		
	}

	public void setParent(ItemEntity parent) {
		this.parent = parent;
		
	}
}
