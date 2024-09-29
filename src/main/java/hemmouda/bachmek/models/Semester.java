package hemmouda.bachmek.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import hemmouda.bachmek.models.interfaces.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table (name= "semester")
public class Semester implements Serializable, Model <Semester> {
	private static final long serialVersionUID = 1L;
      
	public Semester () {
		modules = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicSemester academicSemester;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicYear academicYear;

	@OneToMany(mappedBy= "semester", fetch= FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private Set <SemesterModule> modules;
	
	public boolean isApproved () {
		for (SemesterModule module : modules) {
			if (!module.getIsApproved()) {
				return false;
			}
		}
		return (modules.size() == 0) ? false : true;
	}
	
	public SemesterModule getSemesterModule (Module module) {
		for (SemesterModule semesterModule : modules) {
			if (module.equalsId(semesterModule.getModule())) {
				return semesterModule;
			}
		}
		return null;
	}
	
	public boolean hasModule (Module module) {
		for (SemesterModule semesterModule : modules) {
			if (module.equalsId(semesterModule.getModule())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isModuleApproved (Module module) {
		for (SemesterModule semesterModule : modules) {
			if (module.equalsId(semesterModule.getModule())) {
				return semesterModule.getIsApproved();
			}
		}
		return false;
	}
	
	public List <Element> getElements () {
		List <Element> list = new ArrayList <> ();
		for (SemesterModule semesterModule : modules) {
			list.addAll(semesterModule.getModule().getElements());
		}
		return list;
	}
	
	public String getName () {
		return academicYear.getABBR() +" " +academicSemester.getName();
	}
	
	public String getDashedName () {
		return academicYear.getDashedABBR() +" " +academicSemester.getName();
	}
	
	public String getShortName () {
		return academicSemester.getName();
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public AcademicSemester getAcademicSemester() {
		return academicSemester;
	}


	public void setAcademicSemester(AcademicSemester academicSemester) {
		this.academicSemester = academicSemester;
	}


	public AcademicYear getAcademicYear() {
		return academicYear;
	}


	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}


	public Set<SemesterModule> getModules() {
		return modules;
	}


	public void setModules(Set<SemesterModule> modules) {
		this.modules = modules;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Semester [id=" + id + ", academicSemester=" + academicSemester + ", academicYear=" + academicYear + "]";
	}
	
}