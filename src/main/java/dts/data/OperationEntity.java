package dts.data;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

import dts.ItemID;
import dts.OperationAttributes;

@Document(collection = "OPERATIONS")
public class OperationEntity {

	private String id;
	private String type;
	private String item;
	private Date createdTimeStamp;
	private String invokedBy;
	private String operationAttributes;
	
	private Set<OperationEntity> children;
	private OperationEntity parent;

	public OperationEntity() {

	}
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(String invokedBy) {
		this.invokedBy = invokedBy;
	}

	public String getOperationAttributes() {
		return operationAttributes;
	}

	public void setOperationAttributes(String operationAttributes) {
		this.operationAttributes = operationAttributes;
	}

	@Override
	public String toString() {
		return "OperationEntity [operationId=" + id + ", type=" + type + ", item=" + item
				+ ", createdTimeStamp=" + createdTimeStamp + ", invokedBy=" + invokedBy + ", operationAttributes="
				+ operationAttributes + "]";
	}
	
	// maps a relationship to database
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent" /*, cascade = CascadeType.ALL*/)
	public Set<OperationEntity> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}
	
	public void setChildren(Set<OperationEntity> children) {
		this.children = children;
	}

	@ManyToOne(fetch = FetchType.EAGER /*, cascade = CascadeType.ALL*/)
	public OperationEntity getParent() {
		return parent;
	}

	public void addChildren(OperationEntity child) {
		this.children.add(child);
	}

	public void setParent(OperationEntity parent) {
		this.parent = parent;
	}
	
}