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
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Table (name= "assessment")
public class Assessment implements Serializable, Model <Assessment>, Activatable  {
	private static final long serialVersionUID = 1L;
      
	public Assessment () {
		isActive = true;
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private Boolean isActive;

	@ManyToOne(fetch= FetchType.EAGER)
	private Element element;
	
	public String getFullName () {
		return element.getFullName()+ " : "+ this.getName();
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
		if (!element.getIsActive()) {
			this.isActive = false;
		}
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
		return "Assessment [id=" + id + ", name=" + name + ", isActive=" + isActive + ", element=" + element + "]";
	}
	
}