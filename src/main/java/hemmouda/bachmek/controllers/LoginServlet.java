package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import hemmouda.bachmek.business.UserManager;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.ServletManager;

@WebServlet ("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		final Object lock = session.getId().intern();
		synchronized (lock) {
			session.removeAttribute("user");
			session.invalidate();
		}
		getServletContext().getRequestDispatcher("/WEB-INF/jsps/login.jsp").forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (UserManager.refuteUserLogin(request.getParameter("username"), request.getParameter("password"))) {
			ServletManager.setInvalideFormError(request, response);
			return;
		}
		
		User user = UserManager.authentificate(request.getParameter("username"), request.getParameter("password"));
		if (user != null) {
			HttpSession session = request.getSession();
			final Object lock = session.getId().intern();
			synchronized (lock) {
				session.setAttribute("user", user);
			}
			response.sendRedirect(request.getContextPath()+"/account");
			return;
		}
		
		request.setAttribute("error_message", "Invalid information.");
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("password", request.getParameter("password"));
	
		getServletContext().getRequestDispatcher("/WEB-INF/jsps/login.jsp").forward(request, response);	
	}

}
