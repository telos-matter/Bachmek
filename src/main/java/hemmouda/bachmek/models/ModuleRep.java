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
@Table (name= "moduleRep")
public class ModuleRep implements Serializable, Model <ModuleRep> {
	private static final long serialVersionUID = 1L;
      
	public ModuleRep () {
		this.elementCoefs = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch= FetchType.EAGER)
	private Instructor instructor;

	@ManyToOne(fetch= FetchType.EAGER)
	private AcademicYear academicYear;

	@ManyToOne(fetch= FetchType.EAGER)
	private Module module;
	
	@OneToMany(mappedBy= "moduleRep", fetch= FetchType.EAGER)
	private Set <ElementCoef> elementCoefs;

	public Module getAssociatedItem () {
		return this.getModule();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Set<ElementCoef> getElementCoefs() {
		return elementCoefs;
	}

	public void setElementCoefs(Set<ElementCoef> elementCoefs) {
		this.elementCoefs = elementCoefs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ModuleRep [id=" + id + ", instructor=" + instructor + ", academicYear=" + academicYear + ", module="
				+ module + "]";
	}
	
}