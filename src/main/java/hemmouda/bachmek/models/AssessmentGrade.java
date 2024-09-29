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
@Table (name= "assessmentGrade")
public class AssessmentGrade implements Serializable, Model <AssessmentGrade> {
	private static final long serialVersionUID = 1L;
      
	public AssessmentGrade () {

	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private Float normal_session;

	@Column
	private Float catch_up_session;

	@ManyToOne(fetch= FetchType.EAGER)
	private ElementInstructor elementInstructor;

	@ManyToOne(fetch= FetchType.EAGER)
	private Assessment assessment;

	@ManyToOne(fetch= FetchType.EAGER)
	private PedagogicalRegistration pedagogicalRegistration;

	public float getFinalGrade () {
		return (catch_up_session == null) ? normal_session : Math.max(normal_session, catch_up_session);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getNormal_session() {
		return normal_session;
	}

	public void setNormal_session(Float normal_session) {
		this.normal_session = normal_session;
	}

	public Float getCatch_up_session() {
		return catch_up_session;
	}

	public void setCatch_up_session(Float catch_up_session) {
		this.catch_up_session = catch_up_session;
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

	public PedagogicalRegistration getPedagogicalRegistration() {
		return pedagogicalRegistration;
	}

	public void setPedagogicalRegistration(PedagogicalRegistration pedagogicalRegistration) {
		this.pedagogicalRegistration = pedagogicalRegistration;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AssessmentGrade [id=" + id + ", normal_session=" + normal_session + ", catch_up_session="
				+ catch_up_session + ", elementInstructor=" + elementInstructor
				+ ", assessment=" + assessment + ", pedagogicalRegistration=" + pedagogicalRegistration + "]";
	}
	
}