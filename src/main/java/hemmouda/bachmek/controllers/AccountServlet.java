package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.PedagRegManager;
import hemmouda.bachmek.business.UserManager;
import hemmouda.bachmek.business.UserManager.Role;
import hemmouda.bachmek.models.PedagogicalRegistration;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.ServletManager;
import hemmouda.bachmek.util.StringManager;

@WebServlet (urlPatterns= {"/account", "/account/logout", "/account/password", "/account/grades", "/account/info"})
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AccountServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/account/";
		
		User user = (User) request.getSession().getAttribute("user");
		Role role = UserManager.getRole(user);
		
		if (URN.equals("/account")) {
			
			path += "account.jsp";
			request.setAttribute("item", user);
			request.setAttribute("isStudent",(role == Role.STUDENT));
			
		} else if (URN.equals("/account/password")) {
			
			path += "password.jsp";
			
		} else if (URN.equals("/account/grades")) {
			
			if (role != Role.STUDENT) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			List <PedagogicalRegistration> list = Manager.selectMultiple(PedagogicalRegistration.class, "administrativeRegistration", user.getStudent().getAdministrativeRegistration());
			if (list.size() == 0) {
				path += "no_grades.jsp";
			} else {
				PedagogicalRegistration pedagogicalRegistration = ServletManager.getEntity(PedagogicalRegistration.class, request);
				if (pedagogicalRegistration == null) {
					pedagogicalRegistration = list.get(0);
				}
				path += "grades.jsp";
				request.setAttribute("pedagReg", pedagogicalRegistration);
				request.setAttribute("pedagRegs_list", list);
				request.setAttribute("list", PedagRegManager.getPedagRegGrades (pedagogicalRegistration));
				
			}
			
			
		} else if (URN.equals("/account/info")) {
			
			if (role != Role.STUDENT) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			path += "info.jsp";
			request.setAttribute("item", user);
			
		} else if (URN.equals("/account/logout")) {
			
			HttpSession session = request.getSession();
			session.removeAttribute("user");
			session.invalidate();
			response.sendRedirect(request.getContextPath() +"/home");
			return;
			
		}
		
		
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/account/";
		
		User user = (User) request.getSession().getAttribute("user");
		
		if (URN.equals("/account/password")) {
			
			String new_password = request.getParameter("new_password");
			if (new_password == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			String repeat_password = request.getParameter("repeat_password");
			if (repeat_password == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (StringManager.equals(new_password, repeat_password)) {
				String old_password = request.getParameter("old_password");
				if (old_password == null) {
					ServletManager.setInvalideFormError(request, response);
					return;
				}
				if (StringManager.equals(UserManager.decrypt(user.getPassword()), old_password)) {
					if (UserManager.validatePassword(new_password)) {
						user.setPassword(UserManager.encrypt(new_password));
						user.update();
						ServletManager.setUpdatedSucces(user.getFullName(), User.class, request, response);
						return;
					} else {
						ServletManager.setInvalideFormError(request, response);
						return;
					}
				} else {
					path += "password.jsp";
					request.setAttribute("error_message", "Your current password is incorrect.");
				}
			} else {
				path += "password.jsp";
				request.setAttribute("error_message", "The confirmed password doesn't match the new one.");
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);	
	}

}
