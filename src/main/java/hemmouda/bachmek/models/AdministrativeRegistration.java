package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.DateManager;
import hemmouda.bachmek.util.StringManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name= "administrativeRegistration")
public class AdministrativeRegistration implements Serializable, Model <AdministrativeRegistration>, Activatable {
	private static final long serialVersionUID = 1L;
      
	public AdministrativeRegistration () {
		this.diplomas = "";
		this.isActive = true;
		this.registration_date = DateManager.getCurrentDate();
		this.pedagogicalRegistations = new HashSet <> ();
	}

	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private String cne;

	@Column
	private String personal_address;

	@Column
	private String phone_number;

	@Column
	private String parents_address;

	@Column
	private String diplomas;

	@Column
	private Boolean isTuition;

	@Column
	private Boolean isActive;
	
	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicYear academicYear;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicStage academicStage;

	@ManyToOne(fetch= FetchType.EAGER, cascade= {CascadeType.REMOVE, CascadeType.MERGE})
	private OnlineRegistration onlineRegistration;
	
	@Column
	@Temporal (TemporalType.DATE)
	private Date registration_date;
	
	@OneToMany(mappedBy= "administrativeRegistration", fetch= FetchType.EAGER, cascade= {CascadeType.REMOVE})
	private Set <PedagogicalRegistration> pedagogicalRegistations;

	@Override
	public void delete () {
		Manager.selectUnique(Student.class, "administrativeRegistration", this).getUser().delete();
		Manager.delete(AdministrativeRegistration.class, id);
	}
	
	public String getFullName () {
		return onlineRegistration.getFullName();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public String getPersonal_address() {
		return personal_address;
	}

	public void setPersonal_address(String personal_address) {
		this.personal_address = personal_address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getParents_address() {
		return parents_address;
	}

	public void setParents_address(String parents_address) {
		this.parents_address = parents_address;
	}

	public String getDiplomas() {
		return diplomas;
	}

	public void setDiplomas(String diplomas) {
		if (diplomas == null) {
			this.diplomas = "";
		} else {
			this.diplomas = StringManager.trim(diplomas);
		}
	}

	public Boolean getIsTuition() {
		return isTuition;
	}

	public void setIsTuition(Boolean isTuition) {
		this.isTuition = isTuition;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public AcademicStage getAcademicStage() {
		return academicStage;
	}

	public void setAcademicStage(AcademicStage academicStage) {
		this.academicStage = academicStage;
	}

	public OnlineRegistration getOnlineRegistration() {
		return onlineRegistration;
	}

	public void setOnlineRegistration(OnlineRegistration onlineRegistration) {
		this.onlineRegistration = onlineRegistration;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}
    
	public Set<PedagogicalRegistration> getPedagogicalRegistations() {
		return pedagogicalRegistations;
	}

	public void setPedagogicalRegistations(Set<PedagogicalRegistration> pedagogicalRegistations) {
		this.pedagogicalRegistations = pedagogicalRegistations;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AdministrativeRegistration [id=" + id + ", cne=" + cne + ", personal_address=" + personal_address
				+ ", phone_number=" + phone_number + ", parents_address=" + parents_address + ", diplomas=" + diplomas
				+ ", isTuition=" + isTuition + ", isActive=" + isActive + ", academicYear=" + academicYear
				+ ", academicStage=" + academicStage + ", onlineRegistration=" + onlineRegistration
				+ ", registration_date=" + registration_date + "]";
	}
	
}