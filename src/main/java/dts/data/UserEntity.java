package dts.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "USERS")
public class UserEntity {

	private String id;
	private String userName;
	private UserRole role;
	private String avatar;

	private Set<UserEntity> children;
	private UserEntity parent;

	public UserEntity() {

	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	// maps a relationship to database
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent" /*, cascade = CascadeType.ALL*/)
	public Set<UserEntity> getChildren() {

		return children;
	}

	public void setChildren(Set<UserEntity> children) {
		this.children = children;
	}

	@ManyToOne(fetch = FetchType.EAGER /*, cascade = CascadeType.ALL*/)
	public UserEntity getParent() {
		return parent;
	}

	public void addChildren(UserEntity child) {
		this.children.add(child);
	}

	public void setParent(UserEntity parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userName=" + userName + ", role=" + role + ", avatar=" + avatar + "]";
	}

}
