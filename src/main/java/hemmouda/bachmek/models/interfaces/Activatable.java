package hemmouda.bachmek.models.interfaces;

import java.util.ArrayList;
import java.util.Collection;

import hemmouda.bachmek.DAO.Manager;

public interface Activatable {

	public Boolean getIsActive ();

	public default Boolean getIsNotActive () {
		Boolean isActive = this.getIsActive();
		return (isActive == null) ? null : !isActive;
	}
	
	public static Collection <? extends Activatable> filterActive (Class <? extends Activatable> clazz) {
		return filterActive(Manager.selectAll(clazz));
	}
	
	public static Collection <? extends Activatable> filterActive (Collection <? extends Activatable> collection) {
		Collection <Activatable> new_collection = new ArrayList <> ();
		for (Activatable item : collection) {
			if (item.getIsActive()) {
				new_collection.add(item);
			}
		}
		return new_collection;
	}
	
}
