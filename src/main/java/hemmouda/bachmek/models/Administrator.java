package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Table (name= "administrator")
public class Administrator implements Serializable, Model <Administrator> {
	private static final long serialVersionUID = 1L;
      
	public Administrator () {

	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch= FetchType.EAGER)
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Administrator [id=" + id + ", user=" + user + "]";
	}
	
}