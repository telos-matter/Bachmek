package hemmouda.bachmek.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public class Manager {

	public static void insert (Object entity) {
		System.out.println("Inserting: " + entity); // DEBUG
		EntityManager entityManager = Factory.getFactory().createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
        	entityTransaction = entityManager.getTransaction();
        	entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception exception) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            System.out.println("Error occured while trying to insert: " +entity);
            exception.printStackTrace();
        } finally {
            entityManager.close();
        }
        System.out.println("After inserting: " + entity); // DEBUG
	}
	
	public static <T> T find (Class <T> clazz, int key) {
        EntityManager entityManager = Factory.getFactory().createEntityManager();
        T entity = null;
        try {
        	entity = entityManager.find(clazz, key);        	
        } catch (Exception exception) {
        	System.out.println("Error occured while trying to find: " +key +" for the class: " +clazz);
            exception.printStackTrace();
        } finally {
        	entityManager.close();
        }
        System.out.println("Found: " + entity); // DEBUG
		return entity;
	}
	
	public static <T> T selectUnique (Class <T> clazz, String field, Object value) {
		EntityManager entityManager = Factory.getFactory().createEntityManager();

    	String query = "SELECT c FROM "+clazz.getName()+" c WHERE c."+field+" = :value";
    	
    	TypedQuery <T> typedQuery = entityManager.createQuery(query, clazz);
    	typedQuery.setParameter("value", value);
    	
    	T entity = null;
    	try {
    		entity = typedQuery.getSingleResult();
    	} catch (NoResultException exception) {
    		entity = null;
    	} catch(Exception exception) {
    		System.out.println("Error occured while trying to selectUnique: " +field +" with the value: "+value+" for the class: " +clazz);
    		exception.printStackTrace();
    	} finally {
    		entityManager.close();
    	}
//    	System.out.println("SelectedUnique: " + entity); // DEBUG
    	return entity;
	}
	
	public static <T> T selectUnique (Class <T> clazz, String [] fields, Object [] values) {
		EntityManager entityManager = Factory.getFactory().createEntityManager();

    	String query = "SELECT c FROM "+clazz.getName()+" c WHERE ";
    	for (int i = 0; i < fields.length; i++) {
    		if (i == fields.length -1) {
    			query += "c." +fields[i] +" = :value_" +i;
    		} else {
    			query += "c." +fields[i] +" = :value_" +i +" AND ";
    		}
    	}
    	
    	TypedQuery <T> typedQuery = entityManager.createQuery(query, clazz);
    	for (int i = 0; i < values.length; i++) {
    		typedQuery.setParameter("value_" +i, values [i]);
    	}
    	
    	T entity = null;
    	try {
    		entity = typedQuery.getSingleResult();
    	} catch (NoResultException exception) {
    		entity = null;
    	} catch(Exception exception) {
    		System.out.println("Error occured while trying to selectUnique: " +fields +" with the value: "+values+" for the class: " +clazz);
    		exception.printStackTrace();
    	} finally {
    		entityManager.close();
    	}
//    	System.out.println("SelectedUnique: " + entity); // DEBUG
    	return entity;
	}
	
	public static <T> List <T> selectMultiple (Class <T> clazz, String field, Object value) {
		EntityManager entityManager = Factory.getFactory().createEntityManager();

    	String query = "SELECT c FROM "+clazz.getName()+" c WHERE c."+field+" = :value";
    	
    	TypedQuery <T> typedQuery = entityManager.createQuery(query, clazz);
    	typedQuery.setParameter("value", value);
    	
    	List <T> list = null;
    	try {
    		list = typedQuery.getResultList();
    	} catch(Exception exception) {
    		System.out.println("Error occured while trying to selectMultiple: " +field +" with the value: "+value+" for the class: " +clazz);
    		exception.printStackTrace();
    	} finally {
    		entityManager.close();
    	}
//    	System.out.println("SelectedMultiple: " + list); // DEBUG
    	return list;
	}
	
	public static <T> List <T> selectMultiple (Class <T> clazz, String [] fields, Object [] values) {
		EntityManager entityManager = Factory.getFactory().createEntityManager();

		String query = "SELECT c FROM "+clazz.getName()+" c WHERE ";
    	for (int i = 0; i < fields.length; i++) {
    		if (i == fields.length -1) {
    			query += "c." +fields[i] +" = :value_" +i;
    		} else {
    			query += "c." +fields[i] +" = :value_" +i +" AND ";
    		}
    	}
    	
    	TypedQuery <T> typedQuery = entityManager.createQuery(query, clazz);
    	for (int i = 0; i < values.length; i++) {
    		typedQuery.setParameter("value_" +i, values [i]);
    	}
    	
    	List <T> list = null;
    	try {
    		list = typedQuery.getResultList();
    	} catch(Exception exception) {
    		System.out.println("Error occured while trying to selectMultiple: " +fields +" with the values: "+values+" for the class: " +clazz);
    		exception.printStackTrace();
    	} finally {
    		entityManager.close();
    	}
//    	System.out.println("SelectedMultiple: " + list); // DEBUG
    	return list;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List <T> selectMultiple (Class <T> clazz, String query_script) {
		EntityManager entityManager = Factory.getFactory().createEntityManager();

		Query query = entityManager.createNativeQuery(query_script, clazz);
    	
    	List <T> list = null;
    	try {
    		list = query.getResultList();
    	} catch(Exception exception) {
    		System.out.println("Error occured while trying to selectMultiple: " +query_script +" for the class: " +clazz);
    		exception.printStackTrace();
    	} finally {
    		entityManager.close();
    	}
//    	System.out.println("SelectedMultiple: " + list); // DEBUG
    	return list;
	}
	
	public static <T> List <T> selectAll (Class <T> clazz) {
		EntityManager entityManager = Factory.getFactory().createEntityManager();

    	String query = "SELECT c FROM "+clazz.getName()+" c";
    	
    	TypedQuery <T> typedQuery = entityManager.createQuery(query, clazz);
    	
    	List <T> list = null;
    	try {
    		list = typedQuery.getResultList();
    	} catch(Exception exception) {
    		System.out.println("Error occured while trying to selectAll for the class: " +clazz);
    		exception.printStackTrace();
    	} finally {
    		entityManager.close();
    	}
//    	System.out.println("SelectedAll: " + list); // DEBUG
    	return list;
	}
	
	public static <T> T update (T entity) {
//		System.out.println("Updating: " + entity); // DEBUG
		EntityManager entityManager = Factory.getFactory().createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
        	entityTransaction = entityManager.getTransaction();
        	entityTransaction.begin();
            entity = entityManager.merge(entity);
            entityTransaction.commit();
        } catch (Exception exception) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            System.out.println("Error occured while trying to update: " +entity);
            exception.printStackTrace();
        } finally {
            entityManager.close();
        }
//        System.out.println("After updating: " + entity); // DEBUG
		return entity;
	}
	
	public static <T> void  delete (Class <T> clazz, int key) {
//		System.out.println("Deleting from: " + clazz +" with the id: " +key); // DEBUG
		EntityManager entityManager = Factory.getFactory().createEntityManager();
		EntityTransaction entityTransaction = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			T entity = entityManager.find(clazz, key);
//			System.out.println("Deleting: " + entity); // DEBUG
			entityManager.remove(entity);
			entityTransaction.commit();
			// DEBUG
				entity = entityManager.find(clazz, key);
				if (entity == null) {
					System.out.println("Deleted succesfully.");
				} else {
					System.out.println("/!\\ " +entity +"was NOT deleted.");
				}
			// DEBUG
		} catch (Exception exception) {
			if (entityTransaction != null) {
				entityTransaction.rollback();
			}
			System.out.println("Error occured while trying to delete from: " + clazz +" with the id: " +key);
			exception.printStackTrace();
		} finally {
			entityManager.close();
		}
	}
	
	public static <T> boolean  deleteIfPossible (Class <T> clazz, int key) { // In reality this should be a business layer check before alowing deletion in first place, but just to finish the project and make sure delete works.. 
		System.out.println("Deleting (if possible) from: " + clazz +" with the id: " +key); // DEBUG
		EntityManager entityManager = Factory.getFactory().createEntityManager();
		EntityTransaction entityTransaction = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			T entity = entityManager.find(clazz, key);
			System.out.println("Deleting (if possible): " + entity); // DEBUG
			entityManager.remove(entity);
			entityTransaction.commit();
			// DEBUG
				entity = entityManager.find(clazz, key);
				if (entity == null) {
					System.out.println("Deleted (if possible) succesfully.");
					return true;
				} else {
					System.out.println("/!\\ (if possible) " +entity +"was NOT deleted.");
					return false;
				}
			// DEBUG
		} catch (Exception exception) {
			if (entityTransaction != null) {
				entityTransaction.rollback();
			}
			return false;
		} finally {
			entityManager.close();
		}
	}
	
}

