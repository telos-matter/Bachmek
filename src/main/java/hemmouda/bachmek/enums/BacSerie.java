package hemmouda.bachmek.enums;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.BacSerieEnum;
import hemmouda.bachmek.util.StringManager;

public enum BacSerie {
	
	SCIENTIFIC (1),
	LITERATURE (2),
	ECONOMY (3),
	TECHNOLOGY (4);
	
	private int id;
	private BacSerieEnum bacSerieEnum = null;
	
	private BacSerie (int id) {
		this.id = id;
	}
	
	public int getId () {
		return this.id;
	}
	
	private void setId (int id) {
		this.id = id;
	}
	
	public BacSerieEnum getBacSerieEnum () {
		return bacSerieEnum;
	}
	
	private void setBacSerieEnum (BacSerieEnum bacSerieEnum) {
		this.bacSerieEnum = bacSerieEnum;
		this.setId (bacSerieEnum.getId());
	}
	
	public static void setBacSerieEnums () {
		BacSerieEnum bacSerieEnum;
		for (BacSerie value : BacSerie.values()) {
			bacSerieEnum = Manager.find(BacSerieEnum.class, value.getId());
			if (bacSerieEnum == null) {
				bacSerieEnum = new BacSerieEnum();
				bacSerieEnum.setBac_serie(value);
				Manager.insert(bacSerieEnum);
			}
			value.setBacSerieEnum(bacSerieEnum);
		}
	}
	
	public static BacSerieEnum getBacSerieEnum (String name) {
		if (name == null) {
			return null;
		}
		Integer id = StringManager.parseInt(name);
		if (id == null) {
			name = name.toUpperCase();
			try {
				return BacSerie.valueOf(name).getBacSerieEnum();
			} catch (Exception e) {
				return null;			
			}
		} else {
			for (BacSerie value : BacSerie.values()) {
				if (value.id == id) {
					return value.getBacSerieEnum();
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
			for (BacSerie value : BacSerie.values()) {
				if (name.equals(value.toString()) ) {
					return true;
				}
			}
			return false;
		} else {
			for (BacSerie value : BacSerie.values()) {
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
