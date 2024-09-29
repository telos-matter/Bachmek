package hemmouda.bachmek.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.StringManager;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table (name= "module")
public class Module implements Serializable, Model <Module>, Activatable {
	private static final long serialVersionUID = 1L;
      
	public Module () {
		isActive = true;
		elements = new HashSet <> ();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private Boolean isActive;
	
	@OneToMany(mappedBy= "module", fetch= FetchType.EAGER, cascade= {CascadeType.REMOVE, CascadeType.MERGE})
	private Set <Element> elements;

	public List <Assessment> getAssessments () {
		List <Assessment> list = new ArrayList <> ();
		for (Element element : elements) {
			list.addAll(element.getAssessments());
		}
		return list;
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
		for (Element element : this.elements) {
			element.setIsActive(this.isActive);
		}
	}

	public Set<Element> getElements() {
		return elements;
	}

	public void setElements(Set<Element> elements) {
		this.elements = elements;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", name=" + name + ", isActive=" + isActive + "]";
	}
	
}