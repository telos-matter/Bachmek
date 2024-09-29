package hemmouda.bachmek.listeners;

import jakarta.servlet.ServletContextListener;
import hemmouda.bachmek.DAO.Factory;
import hemmouda.bachmek.enums.BacHonour;
import hemmouda.bachmek.enums.BacSerie;
import hemmouda.bachmek.enums.Gender;
import hemmouda.bachmek.enums.Major;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;

public class InitListener implements ServletContextListener {
	
	private static String CONTEXT_PATH = null;
	private static String ESTM_NAME = null;
	private static String ROOT_PATH = null;

	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		
		CONTEXT_PATH = servletContext.getContextPath();
		ESTM_NAME = servletContext.getInitParameter("estm_name");
		ROOT_PATH = servletContext.getRealPath("/");
		
		Gender.setGenderEnums();
		BacSerie.setBacSerieEnums();
		BacHonour.setBacHonourEnums();
		Major.setMajorEnums();
	
		servletContext.setAttribute("estm_name", ESTM_NAME);
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		Factory.closeFactory();
	}
	
	public static String getContextPath () {
		return CONTEXT_PATH;
	}
	
	public static String getEstmName () {
		return ESTM_NAME;
	}
	
	public static String getRootPath () {
		return ROOT_PATH;
	}
	
}
