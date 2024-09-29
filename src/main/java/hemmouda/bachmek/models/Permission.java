package hemmouda.bachmek.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.StringManager;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table (name= "permission")
public class Permission implements Serializable, Model <Permission> {
	private static final long serialVersionUID = 1L;
      
	public Permission () {
		this.users = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private String value;
	
	@ManyToMany (mappedBy= "permissions", fetch= FetchType.EAGER)
	private Set <User> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = StringManager.toLowerCase(value);
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", value=" + value + "]";
	}

}