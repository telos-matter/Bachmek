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

import hemmouda.bachmek.enums.BacHonour;

@Entity
@Table (name = "bacHonourEnum")
public class BacHonourEnum  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public BacHonourEnum () {
		
	}
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private BacHonour bac_honour;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BacHonour getBac_honour() {
		return bac_honour;
	}

	public void setBac_honour(BacHonour bac_honour) {
		this.bac_honour = bac_honour;
	}

	@Override
	public String toString() {
		return "BacHonourEnum [id=" + id + ", bac_honour=" + bac_honour + "]";
	}

}
