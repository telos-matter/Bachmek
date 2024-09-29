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
@Table (name= "academicStageBound")
public class AcademicStageBound implements Serializable, Model <AcademicStageBound> {
	private static final long serialVersionUID = 1L;
      
	public AcademicStageBound () {
		this.passing_bound = 10.0f;
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private Float passing_bound;

	@ManyToOne(fetch= FetchType.EAGER)
	private MajorRep majorRep;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicStage academicStage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getPassing_bound() {
		return passing_bound;
	}

	public void setPassing_bound(Float passing_bound) {
		this.passing_bound = passing_bound;
	}

	public MajorRep getMajorRep() {
		return majorRep;
	}

	public void setMajorRep(MajorRep majorRep) {
		this.majorRep = majorRep;
	}

	public AcademicStage getAcademicStage() {
		return academicStage;
	}

	public void setAcademicStage(AcademicStage academicStage) {
		this.academicStage = academicStage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AcademicStageBound [id=" + id + ", passing_bound=" + passing_bound
				+ ", majorRep=" + majorRep + ", academicStage=" + academicStage + "]";
	}
	
}