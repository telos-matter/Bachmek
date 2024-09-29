package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table (name= "majorRep")
public class MajorRep implements Serializable, Model <MajorRep> {
	private static final long serialVersionUID = 1L;
      
	public MajorRep () {
		this.academicStageBounds = new HashSet <> ();
		this.moduleCoefBounds = new HashSet <> ();
		this.semesterBounds = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch= FetchType.EAGER)
	private Instructor instructor;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicYear academicYear;

	@ManyToOne(fetch= FetchType.EAGER)
	private MajorEnum major;
	
	@OneToMany(mappedBy= "majorRep", fetch= FetchType.EAGER)
	private Set <AcademicStageBound> academicStageBounds;

	@OneToMany(mappedBy= "majorRep", fetch= FetchType.EAGER)
	private Set <ModuleCoefBound> moduleCoefBounds;
	
	@OneToMany(mappedBy= "majorRep", fetch= FetchType.EAGER)
	private Set <SemesterBound> semesterBounds;

	public MajorEnum getAssociatedItem () {
		return this.getMajor();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public MajorEnum getMajor() {
		return major;
	}

	public void setMajor(MajorEnum major) {
		this.major = major;
	}

	public Set<AcademicStageBound> getAcademicStageBounds() {
		return academicStageBounds;
	}

	public void setAcademicStageBounds(Set<AcademicStageBound> academicStageBounds) {
		this.academicStageBounds = academicStageBounds;
	}

	public Set<ModuleCoefBound> getModuleCoefBounds() {
		return moduleCoefBounds;
	}

	public void setModuleCoefBounds(Set<ModuleCoefBound> moduleCoefBounds) {
		this.moduleCoefBounds = moduleCoefBounds;
	}

	public Set<SemesterBound> getSemesterBounds() {
		return semesterBounds;
	}

	public void setSemesterBounds(Set<SemesterBound> semesterBounds) {
		this.semesterBounds = semesterBounds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MajorRep [id=" + id + ", instructor=" + instructor + ", academicYear=" + academicYear + ", major="
				+ major + "]";
	}
	
}