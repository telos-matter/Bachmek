package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hemmouda.bachmek.enums.Gender;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.DateManager;
import hemmouda.bachmek.util.StringManager;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
@Table (name= "user")
public class User implements Serializable, Model <User>, Activatable  {
	private static final long serialVersionUID = 1L;
      
	public User () {
		this.permissions = new HashSet <> ();
		this.creation_date = DateManager.getCurrentDate();
		this.isActive = true;
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private String email;

	@Column
	private String first_name;

	@Column
	private String last_name;

	@ManyToOne (fetch= FetchType.EAGER)
	private GenderEnum gender;

	@Column
	@Temporal (TemporalType.DATE)
	private Date birthdate;

	@Column
	private String phone_number;

	@Column
	private String personal_address;

	@Column
	private String cin;

	@Column
	private Boolean isActive;

	@Column
	@Temporal (TemporalType.DATE)
	private Date creation_date;
	
	@OneToOne(mappedBy= "user", fetch= FetchType.EAGER, cascade= {CascadeType.PERSIST ,CascadeType.REMOVE, CascadeType.MERGE})
	private Student student;
	
	@OneToOne(mappedBy= "user", fetch= FetchType.EAGER, cascade= {CascadeType.PERSIST ,CascadeType.REMOVE, CascadeType.MERGE})
	private Instructor instructor;
	
	@OneToOne(mappedBy= "user", fetch= FetchType.EAGER, cascade= {CascadeType.PERSIST ,CascadeType.REMOVE, CascadeType.MERGE})
	private Administrator administrator;

	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable (
		name= "user_permission",
		joinColumns= @JoinColumn (name= "user_id", referencedColumnName= "id"),
		inverseJoinColumns= @JoinColumn (name= "permission_id", referencedColumnName= "id")
		)
	private Set <Permission> permissions;

	public String getFormalFullName () {
		return (gender.getGender() == Gender.MALE) ? "Mr. " +getFullName(): "Ms. " +getFullName();
	}
	
	public String getFullName () {
		return getLast_name() +" " +getFirst_name();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return StringManager.forceCapitalize(first_name);
	}

	public void setFirst_name(String first_name) {
		this.first_name = StringManager.toLowerCase(first_name);
	}

	public String getLast_name() {
		return StringManager.toUpperCase(last_name);
	}

	public void setLast_name(String last_name) {
		this.last_name = StringManager.toLowerCase(last_name);
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPersonal_address() {
		return personal_address;
	}

	public void setPersonal_address(String personal_address) {
		this.personal_address = personal_address;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", first_name=" + first_name + ", last_name=" + last_name + ", gender=" + gender + ", birthdate="
				+ birthdate + ", phone_number=" + phone_number + ", personal_address=" + personal_address + ", cin="
				+ cin + ", isActive=" + isActive + ", creation_date=" + creation_date 
				+ "]";
	}
	
}