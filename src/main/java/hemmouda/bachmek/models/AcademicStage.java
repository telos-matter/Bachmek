package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table (name= "academicStage")
public class AcademicStage implements Serializable, Model <AcademicStage>, Activatable  {
	private static final long serialVersionUID = 1L;
      
	public AcademicStage () {
		this.isActive = true;
		this.isDiploma = false;
		this.academicSemesters = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private Integer sequence;
	
	@Column
	private Boolean isDiploma;

	@Column
	private Boolean isActive;

	@ManyToOne(fetch= FetchType.EAGER)
	private MajorEnum major;
	
	@OneToMany(mappedBy= "academicStage", fetch= FetchType.EAGER, cascade= {CascadeType.REMOVE, CascadeType.MERGE})
	private Set <AcademicSemester> academicSemesters;

	@Override
	public Model <AcademicStage> update () {
		AcademicStage academicStage = Manager.update(this);
		academicStage.major.getMajor().setMajorEnum(major);
		return academicStage;
	}
	
	/**
	 * @return the {@link MajorEnum}s' {@link AcademicStage} that
	 * is next in the sequence if it exists if not null
	 */
	public AcademicStage getNext () {
		for (AcademicStage academicStage : major.getAcademicStages()) {
			if (academicStage.getSequence() == this.getSequence() +1) {
				return academicStage;
			}
		}
		return null;
	}
	
	public AcademicSemester getFirstAcademicSemester () {
		for (AcademicSemester academicSemester : academicSemesters) {
			if (academicSemester.getSequence() == 1) {
				return academicSemester;
			}
		}
		return null;
	}
	
	public String getName () {
		return major.getMajor().name() +" #" +sequence;
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

	public Boolean getIsDiploma() {
		return isDiploma;
	}

	public void setIsDiploma(Boolean isDiploma) {
		this.isDiploma = isDiploma;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
		if (!major.getIsActive()) {
			this.isActive = false;
		}
		for (AcademicSemester academicSemester : this.academicSemesters) {
			academicSemester.setIsActive(this.isActive);
		}
	}

	public MajorEnum getMajor() {
		return major;
	}

	public void setMajor(MajorEnum major) {
		this.major = major;
	}

	public Set<AcademicSemester> getAcademicSemesters() {
		return academicSemesters;
	}

	public void setAcademicSemesters(Set<AcademicSemester> academicSemesters) {
		this.academicSemesters = academicSemesters;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AcademicStage [id=" + id + ", sequence=" + sequence + ", isDiploma=" + isDiploma + ", isActive=" + isActive
				+ ", major=" + major + "]";
	}
	
}