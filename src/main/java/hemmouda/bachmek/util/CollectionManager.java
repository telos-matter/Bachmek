package hemmouda.bachmek.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hemmouda.bachmek.models.interfaces.Model;

public class CollectionManager {
	
	/**
	 * @return The intersection formed by the two collections
	 * based on the ids.
	 * @apiNote 
	 * The elements of the returned list are form the {@link #a} collection
	 * <p> If one of the collection has a duplicate element, it'll also be
	 * duplicated in the returned list
	 */
	public static <T extends Model <T>> List <T> intersection (Collection <T> a, Collection <T> b) {
		List <T> list = new ArrayList <> ();
		for (T a_model : a) {
			for (T b_model : b) {
				if (a_model.getId() == b_model.getId()) {
					list.add(a_model);
				}
			}
		}
		return list;
	}
	
	/**
	 * Check {@link #contains(Collection, Model)}
	 */
	public static <T extends Model <T>> boolean boolContains (Collection <T> collection, T model) {
		return contains(collection, model) != null;
	}

	/**
	 * <p>Checks whether or not the model is within the collection
	 * by comparing the ids
	 * @return The model from the passed collection if found, otherwise null
	 */
	public static <T extends Model <T>> T contains (Collection <T> collection, T model) {
		for (T collection_model : collection) {
			if (model.getId() == collection_model.getId()) {
				return collection_model;
			}
		}
		return null;
	}
	
	/**
	 * <p>Removes the model from the collection based on the id
	 * <p>Uses the {@link #contains(Collection, Model)} method
	 */
	public static <T extends Model <T>> boolean remove (Collection <T> collection, T model) {
		T collection_model = contains(collection, model);
		return (collection_model == null) ? null : collection.remove(collection_model);
	}
	
	/**
	 * <p>Adds the model if and only if it doesn't already
	 *exist in the collection based on the id
	 * <p>Uses the {@link #contains(Collection, Model)} method
	 */
	public static <T extends Model <T>> boolean addAsSet (Collection <T> collection, T model) {
		if (contains(collection,model) == null) {
			return collection.add(model);
		}
		return false;
	}
	
}
