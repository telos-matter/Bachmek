package hemmouda.bachmek.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Model;

@Entity
@Table (name= "semester_module")
public class SemesterModule implements Serializable, Model <SemesterModule> {
	private static final long serialVersionUID = 1L;

	public SemesterModule () {
		this.isApproved = false;
	}
	
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column
	private Boolean isApproved;
	
	@ManyToOne(fetch= FetchType.EAGER)
	private Semester semester;

	@ManyToOne(fetch= FetchType.EAGER)
	private Module module;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SemesterModule [id=" + id + ", isApproved=" + isApproved + ", Semester=" + semester.getId() + ", module="
				+ module.getId() + "]";
	}
	
}
