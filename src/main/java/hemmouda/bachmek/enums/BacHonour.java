package hemmouda.bachmek.enums;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.BacHonourEnum;
import hemmouda.bachmek.util.StringManager;

public enum BacHonour {
	
	ACCEPTABLE (1),
	FAIR (2),
	GOOD (3),
	EXCELLENT (4);
	
	private int id;
	private BacHonourEnum bacHonourEnum = null;
	
	private BacHonour (int id) {
		this.id = id;
	}
	
	public int getId () {
		return this.id;
	}
	
	private void setId (int id) {
		this.id = id;
	}
	
	public BacHonourEnum getBacHonourEnum () {
		return bacHonourEnum;
	}
	
	private void setBacHonourEnum (BacHonourEnum bacHonourEnum) {
		this.bacHonourEnum = bacHonourEnum;
		this.setId (bacHonourEnum.getId());
	}
	
	public static void setBacHonourEnums () {
		BacHonourEnum bacHonourEnum;
		for (BacHonour value : BacHonour.values()) {
			bacHonourEnum = Manager.find(BacHonourEnum.class, value.getId());
			if (bacHonourEnum == null) {
				bacHonourEnum = new BacHonourEnum();
				bacHonourEnum.setBac_honour(value);
				Manager.insert(bacHonourEnum);
			}
			value.setBacHonourEnum(bacHonourEnum);
		}
	}
	
	public static BacHonourEnum getBacHonourEnum (String name) {
		if (name == null) {
			return null;
		}
		Integer id = StringManager.parseInt(name);
		if (id == null) {
			name = name.toUpperCase();
			try {
				return BacHonour.valueOf(name).getBacHonourEnum();
			} catch (Exception e) {
				return null;			
			}
		} else {
			for (BacHonour value : BacHonour.values()) {
				if (value.id == id) {
					return value.getBacHonourEnum();
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
			for (BacHonour value : BacHonour.values()) {
				if (name.equals(value.toString()) ) {
					return true;
				}
			}
			return false;
		} else {
			for (BacHonour value : BacHonour.values()) {
				if (value.id == id) {
					return true;
				}
			}
			return false;
		}
	}
	
	public static boolean hasNotValue (String name) {
		return !hasValue(name);
	}
	
	
	
}
