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
@Table (name= "elementInstructor")
public class ElementInstructor implements Serializable, Model <ElementInstructor> {
	private static final long serialVersionUID = 1L;
      
	public ElementInstructor () {
		this.assessmentGrades = new HashSet <> ();
		this.assessmentCoefs = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch= FetchType.EAGER)
	private Element element;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicYear academicYear;

	@ManyToOne(fetch= FetchType.EAGER)
	private Instructor instructor;
	
	@OneToMany(mappedBy= "elementInstructor", fetch= FetchType.EAGER)
	private Set <AssessmentGrade> assessmentGrades;
	
	@OneToMany(mappedBy= "elementInstructor", fetch= FetchType.EAGER)
	private Set <AssessmentCoef> assessmentCoefs;

	public Element getAssociatedItem () {
		return this.getElement();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Set<AssessmentGrade> getAssessmentGrades() {
		return assessmentGrades;
	}

	public void setAssessmentGrades(Set<AssessmentGrade> assessmentGrades) {
		this.assessmentGrades = assessmentGrades;
	}

	public Set<AssessmentCoef> getAssessmentCoefs() {
		return assessmentCoefs;
	}

	public void setAssessmentCoefs(Set<AssessmentCoef> assessmentCoefs) {
		this.assessmentCoefs = assessmentCoefs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ElementInstructor [id=" + id + ", element=" + element + ", academicYear=" + academicYear
				+ ", instructor=" + instructor + "]";
	}
	
}