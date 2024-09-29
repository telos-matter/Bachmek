package hemmouda.bachmek.enums;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.GenderEnum;
import hemmouda.bachmek.util.StringManager;

public enum Gender {

	MALE (1),
	FEMALE (2);
	
	private int id;
	private GenderEnum genderEnum = null;
	
	private Gender (int id) {
		this.id = id;
	}
	
	public int getId () {
		return this.id;
	}
	
	private void setId (int id) {
		this.id = id;
	}
	
	public GenderEnum getGenderEnum () {
		return genderEnum;
	}
	
	private void setGenderEnum (GenderEnum genderEnum) {
		this.genderEnum = genderEnum;
		this.setId(genderEnum.getId());
	}
	
	public static void setGenderEnums () {
		GenderEnum genderEnum;
		for (Gender value : Gender.values()) {
			genderEnum = Manager.find(GenderEnum.class, value.getId());
			if (genderEnum == null) {
				genderEnum = new GenderEnum();
				genderEnum.setGender(value);
				Manager.insert(genderEnum);
			}
			value.setGenderEnum(genderEnum);
		}
	}
	
	public static GenderEnum getGenderEnum (String name) {
		if (name == null) {
			return null;
		}
		Integer id = StringManager.parseInt(name);
		if (id == null) {
			name = name.toUpperCase();
			try {
				return Gender.valueOf(name).getGenderEnum();
			} catch (Exception e) {
				return null;			
			}
		} else {
			for (Gender value : Gender.values()) {
				if (value.id == id) {
					return value.getGenderEnum();
				}
			}
			return null;
		}
	}
	
	public static boolean hasValue (String name) {
		if (name == null) {
			return false;
		}
		Integer id = StringManager.parseInt(name);
		if (id == null) {
			name = name.toUpperCase();
			for (Gender value : Gender.values()) {
				if (name.equals(value.toString()) ) {
					return true;
				}
			}
		} else {
			for (Gender value : Gender.values()) {
				if (value.id == id) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasNotValue (String name) {
		return !hasValue(name);
	}
	
}
