package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Table (name= "assessmentCoef")
public class AssessmentCoef implements Serializable, Model <AssessmentCoef> {
	private static final long serialVersionUID = 1L;
      
	public AssessmentCoef () {
		this.coefficient = 1;
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private Integer coefficient;

	@ManyToOne(fetch= FetchType.EAGER)
	private ElementInstructor elementInstructor;

	@ManyToOne(fetch= FetchType.EAGER)
	private Assessment assessment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}

	public ElementInstructor getElementInstructor() {
		return elementInstructor;
	}

	public void setElementInstructor(ElementInstructor elementInstructor) {
		this.elementInstructor = elementInstructor;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AssessmentCoef [id=" + id + ", coefficient=" + coefficient
				+ ", elementInstructor=" + elementInstructor + ", assessment=" + assessment + "]";
	}
	
}