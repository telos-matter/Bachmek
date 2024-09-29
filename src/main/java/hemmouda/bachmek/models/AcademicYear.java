package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.DateManager;

import java.util.Date;

@Entity
@Table (name= "academicYear")
public class AcademicYear implements Serializable, Model <AcademicYear> {
	private static final long serialVersionUID = 1L;
      
	public AcademicYear () {
		
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	@Temporal (TemporalType.DATE)
	private Date start_date;

	@Column
	@Temporal (TemporalType.DATE)
	private Date finish_date;
	
	public String getABBR() {
		return "" +DateManager.getYearFrom(start_date) +"/" +DateManager.getYearFrom(finish_date);
	}
	
	public String getDashedABBR() {
		return "" +DateManager.getYearFrom(start_date) +"-" +DateManager.getYearFrom(finish_date);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AcademicYear [id=" + id + ", start_date=" + start_date + ", finish_date=" + finish_date + "]";
	}
	
}