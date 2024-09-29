package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.PermissionManager;
import hemmouda.bachmek.business.UserManager;
import hemmouda.bachmek.business.UserManager.Role;
import hemmouda.bachmek.enums.Gender;
import hemmouda.bachmek.models.Administrator;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.Permission;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/user/list", "/user", "/user/create", "/user/edit", "/user/activate"})
public class UserMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserMgmtServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/user/";
		
		if (URN.equals("/user")) {
			
			User user = ServletManager.assertEntity(User.class, request, response);
			if (user == null) {
				return;
			}
			path += "user_info.jsp";
			request.setAttribute("item", user);
			request.setAttribute("item_role", UserManager.getRole(user));
			request.setAttribute("decrypted_password", UserManager.decrypt(user.getPassword()));
			
		} else if (URN.equals("/user/list")) {
			
			path += "users_list.jsp";
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			Role role = Role.getRole(request.getParameter("filter_role"));
			if (role == null) {
				role = Role.STUDENT;
			}
			request.setAttribute("filter_role", role);
			request.setAttribute("roles_list", Role.values());
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", UserManager.getFilteredUsers(role));
			} else {
				request.setAttribute("list", UserManager.getUsers(role));
			}
			
		} else if (URN.equals("/user/create")) {
			
			Boolean createAdmin = ServletManager.getParameter("createAdmin", Boolean.class, request);
			if (createAdmin == null) {
				createAdmin = true;
			}
			path += "create_user.jsp";
			request.setAttribute("createAdmin", createAdmin);
			request.setAttribute("gender_list", Gender.values());
			request.setAttribute("gender", "MALE");
			if (createAdmin) {
				request.setAttribute("isRoot", PermissionManager.hasRootPermission(request.getSession().getAttribute("user")));
			}
			
		} else if (URN.equals("/user/edit")) {
			
			User user = ServletManager.assertEntity(User.class, request, response);
			if (user == null) {
				return;
			}
			path += "edit_user.jsp";
			request.setAttribute("item", user);
			request.setAttribute("item_role", UserManager.getRole(user));
			request.setAttribute("gender_list", Gender.values());
			request.setAttribute("decrypted_password", UserManager.decrypt(user.getPassword()));
			
		} else if (URN.equals("/user/activate")) {
			
			User user = ServletManager.assertEntity(User.class, request, response);
			if (user == null) {
				return;
			}
			user.setIsActive(!user.getIsActive());
			user.update();
			ServletManager.setUpdatedSucces(user.getUsername(), user.getClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/user/";
		
		if (URN.equals("/user/create")) {
			
			Boolean createAdmin = ServletManager.assertParameter("createAdmin", Boolean.class, request, response);
			if (createAdmin == null) {
				return;
			}
			User user = UserManager.validateUser(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), request.getParameter("first_name"), request.getParameter("last_name"), request.getParameter("gender"), request.getParameter("birthdate"), request.getParameter("phone_number"), request.getParameter("personal_address"), request.getParameter("cin"));
			if (user == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (Manager.selectUnique(User.class, "username", user.getUsername()) != null) {
				path += "create_user.jsp";
				request.setAttribute("error_message", "This Username already exist.");
				request.setAttribute("createAdmin", createAdmin);
				request.setAttribute("gender_list", Gender.values());
				request.setAttribute("username", request.getParameter("username"));
				request.setAttribute("password", request.getParameter("password"));
				request.setAttribute("email", request.getParameter("email"));
				request.setAttribute("first_name", request.getParameter("first_name"));
				request.setAttribute("last_name", request.getParameter("last_name"));
				request.setAttribute("gender", request.getParameter("gender"));
				request.setAttribute("birthdate", request.getParameter("birthdate"));
				request.setAttribute("phone_number", request.getParameter("phone_number"));
				request.setAttribute("personal_address", request.getParameter("personal_address"));
				request.setAttribute("cin", request.getParameter("cin"));
				if (!createAdmin) {
					request.setAttribute("discipline", request.getParameter("discipline"));
				}
			} else {
				if (createAdmin) {
					Administrator administrator = new Administrator();
					administrator.setUser(user);
					user.setAdministrator(administrator);
					if (PermissionManager.hasRootPermission(request.getSession().getAttribute("user"))) {
						Boolean root = ServletManager.getParameter("root", Boolean.class, request);
						if (root != null && root) {
							Permission root_permission = PermissionManager.getPermission("root");
							user.getPermissions().add(root_permission);
						}
					}
					user.insert();
					ServletManager.setCreatedSucces(user.getUsername(), User.class, request, response);
					return;
				} else {
					String discipline = request.getParameter("discipline");
					if (UserManager.validateInstructorDiscipline(discipline)) {
						Instructor instructor = new Instructor ();
						instructor.setDiscipline(discipline);
						instructor.setUser(user);
						user.setInstructor(instructor);
						user.insert();
						ServletManager.setCreatedSucces(user.getUsername(), User.class, request, response);
						return;
					} else {
						ServletManager.setInvalideFormError(request, response);
						return;
					}
				}
			}
			
		} else if (URN.equals("/user/edit")) {
			
			User user = ServletManager.assertEntity(User.class, request, response);
			if (user == null) {
				return;
			}
			User validated_user = UserManager.validateUser(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), request.getParameter("first_name"), request.getParameter("last_name"), request.getParameter("gender"), request.getParameter("birthdate"), request.getParameter("phone_number"), request.getParameter("personal_address"), request.getParameter("cin"));
			if (validated_user == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!user.getUsername().equals(validated_user.getUsername()) && Manager.selectUnique(User.class, "username", validated_user.getUsername()) != null) {
				path += "edit_user.jsp";
				request.setAttribute("error_message", "This Username already exist.");
				request.setAttribute("item", user);
				request.setAttribute("item_role", UserManager.getRole(user));
				request.setAttribute("gender_list", Gender.values());
				request.setAttribute("decrypted_password", UserManager.decrypt(user.getPassword()));
			} else {
				if (UserManager.getRole(user) == Role.INSTRUCTOR) {
					String discipline = request.getParameter("discipline");
					if (UserManager.validateInstructorDiscipline(discipline)) {
						user.getInstructor().setDiscipline(discipline);
					} else {
						ServletManager.setInvalideFormError(request, response);
						return;
					}
				}
				validated_user.setId(user.getId());
				validated_user.setCreation_date(user.getCreation_date());
				validated_user.setPermissions(user.getPermissions());
				switch (UserManager.getRole(user)) {
				case STUDENT: 
					validated_user.setStudent(user.getStudent());
					break;
				case INSTRUCTOR:
					validated_user.setInstructor(user.getInstructor());
					break;
				case ADMINISTRATOR:
					validated_user.setAdministrator(user.getAdministrator());
					break;
				}
				validated_user.update();
				ServletManager.setUpdatedSucces(validated_user.getFullName(), User.class, request, response);
				return;
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);		
	}


}
