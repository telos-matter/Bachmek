package hemmouda.bachmek.enums;

import java.util.ArrayList;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.MajorEnum;
import hemmouda.bachmek.util.StringManager;

public enum Major {
	
	BCA (1),
	BGB (2),
	BGI (3),
	BGE (4);
	
	private int id;
	private MajorEnum majorEnum = null;
	
	private Major (int id) {
		this.id = id;
	}

	public int getId () {
		return this.id;
	}
	
	private void setId (int id) {
		this.id = id;
	}
	
	public MajorEnum getMajorEnum () {
		return this.majorEnum;
	}
	
	public void setMajorEnum (MajorEnum majorEnum) {
		this.majorEnum = majorEnum;
		this.setId(majorEnum.getId());
	}
	
	public static List <Major> getActiveValues () {
		List <Major> list = new ArrayList <> ();
		for (Major major : values()) {
			if (major.majorEnum.getIsActive()) {
				list.add(major);
			}
		}
		return list;
	}
	
	public static void setMajorEnums () {
		MajorEnum majorEnum;
		for (Major value : Major.values()) {
			majorEnum = Manager.find(MajorEnum.class, value.getId());
			if (majorEnum == null) {
				majorEnum = new MajorEnum ();
				majorEnum.setMajor(value);
				Manager.insert(majorEnum);
			}
			value.setMajorEnum(majorEnum);
		}
	}
	
	public static MajorEnum getMajorEnum (String name) {
		if (name == null) {
			return null;
		}
		Integer id = StringManager.parseInt(name);
		if (id == null) {
			name = name.toUpperCase();
			try {
				return Major.valueOf(name).getMajorEnum();
			} catch (Exception e) {
				return null;			
			}
		} else {
			for (Major value : Major.values()) {
				if (value.id == id) {
					return value.getMajorEnum();
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
			for (Major value : Major.values()) {
				if (name.equals(value.toString()) ) {
					return true;
				}
			}
		} else {
			for (Major value : Major.values()) {
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
