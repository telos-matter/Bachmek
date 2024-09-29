package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.DateManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Entity
@Table (name= "pedagogicalRegistration")
public class PedagogicalRegistration implements Serializable, Model <PedagogicalRegistration> {
	private static final long serialVersionUID = 1L;
      
	public PedagogicalRegistration () {
		this.registration_date = DateManager.getCurrentDate();
		this.elements = new HashSet <> ();
		this.semesters = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	@Temporal (TemporalType.DATE)
	private Date registration_date;
	
	@ManyToOne(fetch= FetchType.EAGER)
	private AdministrativeRegistration administrativeRegistration;

	
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable (
		name= "pedagogicalRegistration_element",
		joinColumns= @JoinColumn (name= "pedagogicalRegistration_id", referencedColumnName= "id"),
		inverseJoinColumns= @JoinColumn (name= "element_id", referencedColumnName= "id")
		)
	private Set <Element> elements;
	
	// S1 + S3 for example (same acadYear tho)
	// But another one for S2 (Which ll be same year)
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable (
		name= "pedagogicalRegistration_semester",
		joinColumns= @JoinColumn (name= "pedagogicalRegistration_id", referencedColumnName= "id"),
		inverseJoinColumns= @JoinColumn (name= "semester_id", referencedColumnName= "id")
		)
	private Set <Semester> semesters;
	
	public List <AcademicSemester> getAcademicSemesters () {
		List <AcademicSemester> list = new ArrayList <> ();
		for (Semester semester : semesters) {
			list.add(semester.getAcademicSemester());
		}
		return list;
	}
	
	// Could do without the check, the implementation forces that when one is created that it has at
	// least 1 semester
	public AcademicYear getAcademicYear () {
		return (semesters.size() == 0) ? null : semesters.iterator().next().getAcademicYear();
	}
	
	public String getAcadYearABBR () {
		return (semesters.size() == 0) ? null : semesters.iterator().next().getAcademicYear().getABBR();
	}
	
	public MajorEnum getMajor () {
		return (semesters.size() == 0) ? null : semesters.iterator().next().getAcademicSemester().getAcademicStage().getMajor();
	}

	public List <Module> getModules () {
		List <Module> list = new ArrayList <> ();
		for (Element element : elements) {
			CollectionManager.addAsSet(list, element.getModule());
		}
		return list;
	}
	
	public List <AcademicStage> getAcademicStages () {
		List <AcademicStage> list = new ArrayList <> ();
		for (Semester semester : semesters) {
			CollectionManager.addAsSet(list, semester.getAcademicSemester().getAcademicStage());
		}
		return list;
	} 
	
	public boolean hasModule (Module module) {
		return CollectionManager.boolContains(getModules(), module);
	}
	
	public boolean hasElement (Element element) {
		return CollectionManager.boolContains(elements, element);
	}
	
	public boolean hasSemester (Semester semester) {
		return CollectionManager.boolContains(semesters, semester);
	}
	
	public boolean hasAcademicStage (AcademicStage academicStage) {
		return CollectionManager.boolContains(getAcademicStages(), academicStage);
	}
	
	public String getRegistrationName () {
		String name = "" +getAcadYearABBR();
		for (AcademicSemester academicSemester : getAcademicSemesters()) {
			name += " " +academicSemester.getSimpleName();
		}
		return name;
	}
	
	public String getFullName () {
		return administrativeRegistration.getFullName();
	}
	
	public String getCne () {
		return administrativeRegistration.getCne();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}

	public AdministrativeRegistration getAdministrativeRegistration() {
		return administrativeRegistration;
	}

	public void setAdministrativeRegistration(AdministrativeRegistration administrativeRegistration) {
		this.administrativeRegistration = administrativeRegistration;
	}

	public Set<Element> getElements() {
		return elements;
	}

	public void setElements(Set<Element> elements) {
		this.elements = elements;
	}

	public Set<Semester> getSemesters() {
		return semesters;
	}

	public void setSemesters(Set<Semester> semesters) {
		this.semesters = semesters;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PedagogicalRegistration [id=" + id + ", registration_date=" + registration_date
				+ ", administrativeRegistration=" + administrativeRegistration + "]";
	}
	
}