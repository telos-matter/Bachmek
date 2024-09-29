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
@Table (name= "moduleCoefBound")
public class ModuleCoefBound implements Serializable, Model <ModuleCoefBound>  {
	private static final long serialVersionUID = 1L;
      
	public ModuleCoefBound () {
		this.coefficient = 1;
		this.passing_bound = 10.0f;
		this.failling_bound = 4.0f;
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private Integer coefficient;

	@Column
	private Float passing_bound;

	@Column
	private Float failling_bound;

	@ManyToOne(fetch= FetchType.EAGER)
	private MajorRep majorRep;

	@ManyToOne(fetch= FetchType.EAGER)
	private Module module;

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

	public Float getPassing_bound() {
		return passing_bound;
	}

	public void setPassing_bound(Float passing_bound) {
		this.passing_bound = passing_bound;
	}

	public Float getFailling_bound() {
		return failling_bound;
	}

	public void setFailling_bound(Float failling_bound) {
		this.failling_bound = failling_bound;
	}

	public MajorRep getMajorRep() {
		return majorRep;
	}

	public void setMajorRep(MajorRep majorRep) {
		this.majorRep = majorRep;
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
		return "ModuleCoefBound [id=" + id + ", coefficient=" + coefficient + ", passing_bound=" + passing_bound
				+ ", failling_bound=" + failling_bound + ", majorRep=" + majorRep
				+ ", module=" + module + "]";
	}
	
}