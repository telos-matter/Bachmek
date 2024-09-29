package hemmouda.bachmek.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Table (name= "academicSemester")
public class AcademicSemester implements Serializable, Model <AcademicSemester>, Activatable {
	private static final long serialVersionUID = 1L;
      
	public AcademicSemester () {
		this.isActive = true;
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column
	private Integer sequence;

	@Column
	private Boolean isActive;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicStage academicStage;
	
	/**
	 * @return for example (if this is BGI S1) BGI S3 BGI S5
	 * or any AcademicSemester (along side this one)
	 * that has the same sequence but different {@link AcademicStage}
	 */
	public List <AcademicSemester> getSameSequenceAcademicSemesters () {
		List <AcademicSemester> list = new ArrayList <> ();
		for (AcademicStage academicStage : this.academicStage.getMajor().getAcademicStages()) {
			for (AcademicSemester academicSemester : academicStage.getAcademicSemesters()) {
				if (academicSemester.getSequence() == this.getSequence()) {
					list.add(academicSemester);
					break;
				}
			}
		}
		return list;
	}
	
	@Override
	public Model <AcademicSemester> update () {
		AcademicSemester academicSemester = Manager.update(this);
		academicSemester.academicStage.getMajor().getMajor().setMajorEnum(academicStage.getMajor());
		return academicSemester;
	}
	
	/**
	 * @return the next {@link AcademicStage}s' {@link AcademicSemester} that
	 * has the same sequence if it exists if not null
	 */
	public AcademicSemester getSameNextStage () {
		AcademicStage next_academicStage = academicStage.getNext();
		if (next_academicStage == null) {
			return null;
		}
		for (AcademicSemester academicSemester : next_academicStage.getAcademicSemesters()) {
			if (academicSemester.getSequence() == this.sequence) {
				return academicSemester;
			}
		}
		return null;
	}
	
	private int getGeneralSequence () {
		int general_sequence = 0;
		for (AcademicStage academicStage : academicStage.getMajor().getAcademicStages()) {
			if (academicStage.getSequence() < this.academicStage.getSequence()) {
				general_sequence += academicStage.getAcademicSemesters().size();
			}
		}
		return general_sequence +sequence;
	}
	
	public String getSimpleName () {
		return "S" +getGeneralSequence();
	}
	
	public String getName () {
		return academicStage.getMajor().getMajor().name() +" S" +getGeneralSequence();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
		if (!academicStage.getIsActive()) {
			this.isActive = false;
		}
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
		return "AcademicSemester [id=" + id + ", sequence=" + sequence + ", isActive=" + isActive + ", academicStage="
				+ academicStage + "]";
	}
	
}