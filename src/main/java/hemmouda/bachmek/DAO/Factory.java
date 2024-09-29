package hemmouda.bachmek.DAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {
	
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("bachmekDB");

	public static EntityManagerFactory getFactory () {
		return FACTORY;
	}
	
	public static void closeFactory () {
		FACTORY.close();
		// Then Centurion said: And fire the employees too, Sir?
	}
	
}
