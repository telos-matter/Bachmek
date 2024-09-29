package hemmouda.bachmek.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@Table (name= "instructor")
public class Instructor implements Serializable, Model <Instructor>, Activatable {
	private static final long serialVersionUID = 1L;
      
	public Instructor () {

	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch= FetchType.EAGER)
	private User user;
	
	@Column
	private String discipline;

	@Override
	public Boolean getIsActive() {
		return user.getIsActive();
	}
	
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
	
	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", user=" + user + ", discipline=" + discipline + "]";
	}

}