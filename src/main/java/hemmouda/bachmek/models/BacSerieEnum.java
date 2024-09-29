package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import hemmouda.bachmek.enums.BacSerie;

@Entity
@Table (name = "bacSerieEnum")
public class BacSerieEnum implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public BacSerieEnum () {
		
	}
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private BacSerie bac_serie;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BacSerie getBac_serie() {
		return bac_serie;
	}

	public void setBac_serie(BacSerie bac_serie) {
		this.bac_serie = bac_serie;
	}

	@Override
	public String toString() {
		return "BacSerieEnum [id=" + id + ", bac_serie=" + bac_serie + "]";
	}

}
