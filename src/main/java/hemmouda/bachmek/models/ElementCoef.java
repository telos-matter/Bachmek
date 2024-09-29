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
@Table (name= "elementCoef")
public class ElementCoef implements Serializable, Model <ElementCoef> {
	private static final long serialVersionUID = 1L;
      
	public ElementCoef () {
		this.coefficient = 1;
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private Integer coefficient;

	@ManyToOne(fetch= FetchType.EAGER)
	private ModuleRep moduleRep;

	@ManyToOne(fetch= FetchType.EAGER)
	private Element element;

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

	public ModuleRep getModuleRep() {
		return moduleRep;
	}

	public void setModuleRep(ModuleRep moduleRep) {
		this.moduleRep = moduleRep;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ElementCoef [id=" + id + ", coefficient=" + coefficient + ", moduleRep="
				+ moduleRep + ", element=" + element + "]";
	}
	
}