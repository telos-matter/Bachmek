package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import hemmouda.bachmek.enums.Gender;

@Entity
@Table (name = "genderEnum")
public class GenderEnum implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public GenderEnum () {
		
	}

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "GenderEnum [id=" + id + ", gender=" + gender + "]";
	}
	
}
