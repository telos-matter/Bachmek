package hemmouda.bachmek.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.enums.Major;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table (name= "majorEnum")
public class MajorEnum implements Serializable, Model <MajorEnum>, Activatable  {
	private static final long serialVersionUID = 1L;
      
	public MajorEnum () {
		this.isActive = true;
		this.academicStages = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	@Enumerated(EnumType.STRING)
	private Major major;

	@Column
	private Boolean isActive;
	
	@OneToMany(mappedBy= "major", fetch= FetchType.EAGER, cascade= {CascadeType.REMOVE, CascadeType.MERGE})
	private Set <AcademicStage> academicStages;

	@Override
	public Model <MajorEnum> update () {
		MajorEnum majorEnum = Manager.update(this);
		majorEnum.major.setMajorEnum(majorEnum);
		return majorEnum;
	}
	
	public List <AcademicSemester> getAcademicSemesters () {
		List <AcademicSemester> list = new ArrayList <> ();
		for (AcademicStage academicStage : academicStages) {
			list.addAll(academicStage.getAcademicSemesters());
		}
		return list;
	}
	
	public String getName () {
		return this.major.name();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
		for (AcademicStage academicStage : this.academicStages) {
			academicStage.setIsActive(this.isActive);
		}
	}

	public Set<AcademicStage> getAcademicStages() {
		return academicStages;
	}

	public void setAcademicStages(Set<AcademicStage> academicStages) {
		this.academicStages = academicStages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Major [id=" + id + ", major=" + major + ", isActive=" + isActive + "]";
	}
	
}