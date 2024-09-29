package hemmouda.bachmek.models.interfaces;

import hemmouda.bachmek.DAO.Manager;

public interface Model <T extends Model <T>> {

	public int getId ();
	
	public default boolean equalsId (Model <T> model) {
		return (model == null) ? false : this.getId() == model.getId();
	}
	
	public default void insert () {
		Manager.insert(this);
	}
	
	public default Model <T> update () {
		return Manager.update(this);
	}
	
	public default void delete () {
		Manager.delete(this.getClass(), this.getId());
	}
	
}
