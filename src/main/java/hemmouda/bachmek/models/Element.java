package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.StringManager;

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
@Table (name= "element")
public class Element implements Serializable, Model <Element>, Activatable  {
	private static final long serialVersionUID = 1L;
      
	public Element () {
		this.isActive = true;
		this.assessments = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private Boolean isActive;

	@ManyToOne(fetch= FetchType.EAGER)
	private Module module;
	
	@OneToMany(mappedBy= "element", fetch= FetchType.EAGER, cascade= {CascadeType.REMOVE, CascadeType.MERGE})
	private Set <Assessment> assessments;

	public String getFullName () {
		return module.getName() + " : "+ this.getName();
	}
	
	public String getDashedFullName () {
		return module.getName() + " - "+ this.getName();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return StringManager.forceCapitalize(name);
	}

	public void setName(String name) {
		this.name = StringManager.toLowerCase(name);
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
		if (!module.getIsActive()) {
			this.isActive = false;
		}
		for (Assessment assessment : this.assessments) {
			assessment.setIsActive(this.isActive);
		}
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Set<Assessment> getAssessments() {
		return assessments;
	}

	public void setAssessments(Set<Assessment> assessments) {
		this.assessments = assessments;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Element [id=" + id + ", name=" + name + ", isActive=" + isActive + ", module=" + module + "]";
	}
	
}