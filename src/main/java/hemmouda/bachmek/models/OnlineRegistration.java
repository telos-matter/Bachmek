package hemmouda.bachmek.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import hemmouda.bachmek.listeners.InitListener;
import hemmouda.bachmek.models.interfaces.Model;
import hemmouda.bachmek.util.DateManager;
import hemmouda.bachmek.util.StringManager;

import java.util.Date;

@Entity
@Table (name= "onlineRegistration")
public class OnlineRegistration implements Serializable, Model <OnlineRegistration> {
	private static final long serialVersionUID = 1L;
      
	public OnlineRegistration () {
		this.establishment = InitListener.getEstmName();
		this.registration_date = DateManager.getCurrentDate();
	}
      
	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column
	private String massar_code;

	@Column
	private String ar_first_name;

	@Column
	private String ar_last_name;

	@Column
	private String first_name;

	@Column
	private String last_name;

	@Column
	private String cin;

	@Column
	private String nationality;

	@ManyToOne(fetch= FetchType.EAGER)
	private GenderEnum gender;

	@Column
	@Temporal (TemporalType.DATE)
	private Date birthdate;

	@Column
	private String ar_birth_place;

	@Column
	private String birth_place;

	@Column
	private String residence_town;

	@Column
	private String province;

	@Column
	private Integer bac_year;

	@Column
	private String high_school;

	@Column
	private String bac_place;

	@Column
	private String academy;

	@ManyToOne(fetch= FetchType.EAGER)
	private BacSerieEnum bac_serie;

	@ManyToOne(fetch= FetchType.EAGER)
	private BacHonourEnum bac_honour;

	@ManyToOne(fetch= FetchType.EAGER)
	private MajorEnum major;

	@Column
	private String establishment;

	@Column
	@Temporal (TemporalType.DATE)
	private Date registration_date;

	public String getFullName () {
		return getLast_name() +" " +getFirst_name();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMassar_code() {
		return massar_code;
	}

	public void setMassar_code(String massar_code) {
		this.massar_code = massar_code;
	}

	public String getAr_first_name() {
		return ar_first_name;
	}

	public void setAr_first_name(String ar_first_name) {
		this.ar_first_name = ar_first_name;
	}

	public String getAr_last_name() {
		return ar_last_name;
	}

	public void setAr_last_name(String ar_last_name) {
		this.ar_last_name = ar_last_name;
	}

	public String getFirst_name() {
		return StringManager.forceCapitalize(first_name);
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return StringManager.toUpperCase(last_name);
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getAr_birth_place() {
		return ar_birth_place;
	}

	public void setAr_birth_place(String ar_birth_place) {
		this.ar_birth_place = ar_birth_place;
	}

	public String getBirth_place() {
		return birth_place;
	}

	public void setBirth_place(String birth_place) {
		this.birth_place = birth_place;
	}

	public String getResidence_town() {
		return residence_town;
	}

	public void setResidence_town(String residence_town) {
		this.residence_town = residence_town;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getBac_year() {
		return bac_year;
	}

	public void setBac_year(Integer bac_year) {
		this.bac_year = bac_year;
	}

	public String getHigh_school() {
		return high_school;
	}

	public void setHigh_school(String high_school) {
		this.high_school = high_school;
	}

	public String getBac_place() {
		return bac_place;
	}

	public void setBac_place(String bac_place) {
		this.bac_place = bac_place;
	}

	public String getAcademy() {
		return academy;
	}

	public void setAcademy(String academy) {
		this.academy = academy;
	}

	public BacSerieEnum getBac_serie() {
		return bac_serie;
	}

	public void setBac_serie(BacSerieEnum bac_serie) {
		this.bac_serie = bac_serie;
	}

	public BacHonourEnum getBac_honour() {
		return bac_honour;
	}

	public void setBac_honour(BacHonourEnum bac_honour) {
		this.bac_honour = bac_honour;
	}

	public MajorEnum getMajor() {
		return major;
	}

	public void setMajor(MajorEnum major) {
		this.major = major;
	}

	public String getEstablishment() {
		return establishment;
	}

	public void setEstablishment(String establishment) {
		this.establishment = establishment;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "OnlineRegistration [id=" + id + ", massar_code=" + massar_code + ", ar_first_name=" + ar_first_name
				+ ", ar_last_name=" + ar_last_name + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", cin=" + cin + ", nationality=" + nationality + ", gender=" + gender + ", birthdate=" + birthdate
				+ ", ar_birth_place=" + ar_birth_place + ", birth_place=" + birth_place + ", residence_town="
				+ residence_town + ", province=" + province + ", bac_year=" + bac_year + ", high_school=" + high_school
				+ ", bac_place=" + bac_place + ", academy=" + academy + ", bac_serie=" + bac_serie + ", bac_honour="
				+ bac_honour + ", major=" + major + ", establishment=" + establishment + ", registration_date="
				+ registration_date + "]";
	}

}