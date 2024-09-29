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
@Table (name= "student")
public class Student implements Serializable, Model <Student> {
	private static final long serialVersionUID = 1L;
      
	public Student () {

	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch= FetchType.EAGER)
	private User user;

	@OneToOne(fetch= FetchType.EAGER)
	private AdministrativeRegistration administrativeRegistration;

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

	public AdministrativeRegistration getAdministrativeRegistration() {
		return administrativeRegistration;
	}

	public void setAdministrativeRegistration(AdministrativeRegistration administrativeRegistration) {
		this.administrativeRegistration = administrativeRegistration;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", user=" + user + "]";
	}

}