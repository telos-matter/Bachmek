package hemmouda.bachmek.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import hemmouda.bachmek.controllers.*;
import hemmouda.bachmek.listeners.InitListener;
import hemmouda.bachmek.models.Permission;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.StringManager;
import jakarta.servlet.annotation.WebServlet;
import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.UserManager.Role;

public class PermissionManager {
	
	private static final Class <?> [] ALL_SERVLETS = {
			LoginServlet.class,
			OnlineRegServlet.class,
			AcadStageMgmtServlet.class,
			
			
			AcadSemMgmtServlet.class,
			AcadYearMgmtServlet.class,
			AccountServlet.class,
			AdminRegServlet.class,
			AssessmentMgmtServlet.class,
			ElementMgmtServlet.class,
			InstructorMgmtServlet.class,
			MajorMgmtServlet.class,
			ModuleMgmtServlet.class,
			PedagRegMgmtServlet.class,
			SemesterMgmtServlet.class,
			CoefMgmtServlet.class,
			UserMgmtServlet.class,
			GradeMgmtServlet.class,
			ApprovementMgmtServlet.class,
			MMServlet.class
	};
	
	private static final Class <?> [] ROOT_ADMINISTRATOR_SERVLETS = {
			AcadSemMgmtServlet.class,
			AcadStageMgmtServlet.class,
			AcadYearMgmtServlet.class,
			AccountServlet.class,
			AdminRegServlet.class,
			AssessmentMgmtServlet.class,
			ElementMgmtServlet.class,
			InstructorMgmtServlet.class,
			MajorMgmtServlet.class,
			ModuleMgmtServlet.class,
			PedagRegMgmtServlet.class,
			SemesterMgmtServlet.class,
			UserMgmtServlet.class,
			ApprovementMgmtServlet.class,
			MMServlet.class
	};
	
	private static final Class <?> [] ADMINISTRATOR_SERVLETS = {
			AcadSemMgmtServlet.class,
			AcadStageMgmtServlet.class,
			AcadYearMgmtServlet.class,
			AccountServlet.class,
			AdminRegServlet.class,
			AssessmentMgmtServlet.class,
			ElementMgmtServlet.class,
			InstructorMgmtServlet.class,
			MajorMgmtServlet.class,
			ModuleMgmtServlet.class,
			PedagRegMgmtServlet.class,
			SemesterMgmtServlet.class,
			ApprovementMgmtServlet.class,
			MMServlet.class
	};
	
	private static final Class <?> [] INSTRUCTOR_SERVLETS = {
			AccountServlet.class,
			CoefMgmtServlet.class,
			GradeMgmtServlet.class,
			MMServlet.class
	};

	private static final Class <?> [] STUDENT_SERVLETS = {
			AccountServlet.class
	};
	
	private static final String [] ROOT_ADMINISTRATOR_URLS = ArrayManager.getURLs(ROOT_ADMINISTRATOR_SERVLETS);
	private static final String [] ADMINISTRATOR_URLS = ArrayManager.getURLs(ADMINISTRATOR_SERVLETS);
	private static final String [] INSTRUCTOR_URLS = ArrayManager.getURLs(INSTRUCTOR_SERVLETS);
	private static final String [] STUDENT_URLS = ArrayManager.getURLs(STUDENT_SERVLETS);
	
	/**
	 * @return the {@link Permission} with the value
	 * or creates it if it doesn't exist;
	 */
	public static Permission getPermission (String value) {
		value = StringManager.toLowerCase(value);
		Permission permission = Manager.selectUnique(Permission.class, "value", value);
		if (permission == null) {
			permission = new Permission();
			permission.setValue(value);
			permission.insert();
		}
		return permission;
	}
	
	public static boolean hasRootPermission (User user) {
		return hasPermission("root", user);
	}
	
	public static boolean hasRootPermission (Object user) {
		return hasPermission("root", user);
	}
	
	public static boolean hasPermission (String value, Object user) {
		return hasPermission(value, (User) user);
	}
	
	public static boolean hasPermission (String value, User user) {
		value = StringManager.toLowerCase(value);
		for (Permission permission : user.getPermissions()) {
			if (StringManager.equals(permission.getValue(), value)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean canNotAccess (User user, Role role, String URN) {
		switch (role) {
		case STUDENT: {
			return ArrayManager.notContains(STUDENT_URLS, URN);
		}
		case INSTRUCTOR: {
			return ArrayManager.notContains(INSTRUCTOR_URLS, URN);
		}
		case ADMINISTRATOR: {
			if (hasRootPermission (user)) {
				return ArrayManager.notContains(ROOT_ADMINISTRATOR_URLS, URN);
			} else {
				return ArrayManager.notContains(ADMINISTRATOR_URLS, URN) ;
			}
		}
		}
		return true;
	}
	
	public static List <MenuLink> getMenuLinks (User user, Role role, String URN) {
		List <MenuLink> menuLinks = new ArrayList <> ();
		
		String context_path = InitListener.getContextPath();
		
		switch (role) {
		case STUDENT: {
			menuLinks.add(new MenuLink(context_path + "/account", "Account"));
			break;
		}
		case INSTRUCTOR: {
			menuLinks.add(new MenuLink(context_path + "/account", "Account"));
			menuLinks.add(new MenuLink(context_path + "/coef/list", "Coef. mgmt."));
			menuLinks.add(new MenuLink(context_path + "/grade/list", "Grade mgmt."));
			menuLinks.add(new MenuLink(context_path + "/mm", "Meeting Minutes"));
			break;
		}
		case ADMINISTRATOR: {
			if (hasRootPermission(user)) {
				menuLinks.add(new MenuLink(context_path + "/account", "Account"));
				menuLinks.add(new MenuLink(context_path + "/acadYear/list", "Acad.Year mgmt."));
				menuLinks.add(new MenuLink(context_path + "/major/list", "Major mgmt."));
				menuLinks.add(new MenuLink(context_path + "/module/list", "Module mgmt."));
				menuLinks.add(new MenuLink(context_path + "/semester/list", "Semester mgmt."));
				menuLinks.add(new MenuLink(context_path + "/adminReg/list", "Admin. Reg. mgmt."));
				menuLinks.add(new MenuLink(context_path + "/pedagReg/list", "Pedag. Reg. mgmt."));
				menuLinks.add(new MenuLink(context_path + "/user/list", "User mgmt."));
				menuLinks.add(new MenuLink(context_path + "/instructor/list", "Instructor mgmt."));
				menuLinks.add(new MenuLink(context_path + "/approvement/list", "Approvement mgmt."));
				menuLinks.add(new MenuLink(context_path + "/mm", "Meeting Minutes"));
			} else {
				menuLinks.add(new MenuLink(context_path + "/account", "Account"));
				menuLinks.add(new MenuLink(context_path + "/acadYear/list", "Acad.Year mgmt."));
				menuLinks.add(new MenuLink(context_path + "/major/list", "Major mgmt."));
				menuLinks.add(new MenuLink(context_path + "/module/list", "Module mgmt."));
				menuLinks.add(new MenuLink(context_path + "/semester/list", "Semester mgmt."));
				menuLinks.add(new MenuLink(context_path + "/adminReg/list", "Admin. Reg. mgmt."));
				menuLinks.add(new MenuLink(context_path + "/pedagReg/list", "Pedag. Reg. mgmt."));
				menuLinks.add(new MenuLink(context_path + "/instructor/list", "Instructor mgmt."));
				menuLinks.add(new MenuLink(context_path + "/approvement/list", "Approvement mgmt."));
				menuLinks.add(new MenuLink(context_path + "/mm", "Meeting Minutes"));
			}
			break;
		}
		}		
		
		MenuLink.setSelected(menuLinks, URN, context_path);
			
		return menuLinks;
	}
	
	public static class MenuLink implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public static void setSelected (List <MenuLink> list, String URN, String context_path) {
			Class <?> servlet = null;
			
			if (ArrayManager.contains(AssessmentMgmtServlet.class, URN) || ArrayManager.contains(ElementMgmtServlet.class, URN)) {
				servlet = ModuleMgmtServlet.class;
			} else if (ArrayManager.contains(AcadSemMgmtServlet.class, URN) || ArrayManager.contains(AcadStageMgmtServlet.class, URN)) {
				servlet = MajorMgmtServlet.class;
			}
			
			if (servlet == null) {
				for (Class <?> temp : ALL_SERVLETS) {
					if (ArrayManager.contains(temp, URN)) {
						servlet = temp;
						break;
					}
				}
			}
			
			String first_URL = ArrayManager.getFirstURL(servlet);
			for (MenuLink menuLink : list) {
				if (menuLink.getLink().equals(context_path +first_URL)) {
					menuLink.setSelected(true);
					break;
				}
			}
		}
		
		public MenuLink () {
			this (null, null);
		}
		
		public MenuLink (String link, String link_name) {
			this (link, link_name, false);
		}
		
		public MenuLink(String link, String link_name, boolean isSelected) {
			this.link = link;
			this.link_name = link_name;
			this.isSelected = isSelected;
		}

		private String link;
		
		private String link_name;
		
		private boolean isSelected;

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getLink_name() {
			return link_name;
		}

		public void setLink_name(String link_name) {
			this.link_name = link_name;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
	private static class ArrayManager {
		public static String getFirstURL (Class <?> servlet) {
			return getURLs(servlet)[0];
		}
		
		public static boolean contains (Class <?> servlet, String URN) {
			return contains (getURLs (servlet), URN);
		}
		
		public static boolean contains (String [] list, String item) {
			for (String s : list) {
				if (s.equals(item)) {
					return true;
				}
			}
			return false;
		}
		
		public static boolean notContains (String [] list, String item) {
			return ! contains(list, item);
		}
		
		private static String [] getURLs (Class <?> servlet) {
			return servlet.getDeclaredAnnotation(WebServlet.class).urlPatterns();
		}
		
		private static String [] getURLs (Class <?> [] servlets) {
			List <String> URLs = new ArrayList <> ();
			
			for (Class <?> servlet : servlets) {
				for (String URL : servlet.getDeclaredAnnotation(WebServlet.class).urlPatterns()) {
					URLs.add(URL);
				}
			}
			
			String [] list = new String [URLs.size()];
			for (int i = 0; i < list.length; i++) {
				list [i] = URLs.get(i);
			}
			return list;
		}
	}

}
