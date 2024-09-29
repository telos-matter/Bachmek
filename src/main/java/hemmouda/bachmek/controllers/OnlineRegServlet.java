package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hemmouda.bachmek.business.OnlineRegManager;
import hemmouda.bachmek.enums.BacHonour;
import hemmouda.bachmek.enums.BacSerie;
import hemmouda.bachmek.enums.Major;
import hemmouda.bachmek.models.OnlineRegistrations;
import hemmouda.bachmek.util.DateManager;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/home", "/home/BCA", "/home/BGB", "/home/BGI", "/home/apply"})
public class OnlineRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public OnlineRegServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/online_reg/";
	
		if (URN.equals("/home")) {
			path += "home.jsp";
		} else if (URN.equals("/home/BCA")) {
			path += "home_BCA.jsp";		
		} else if (URN.equals("/home/BGB")) {
			path += "home_BGB.jsp";			
		} else if (URN.equals("/home/BGI")) {
			path += "home_BGI.jsp";	
		} else if (URN.equals("/home/apply")) {
			path += "apply.jsp";
			
			String major = request.getParameter("major");
			if (Major.hasNotValue(major)) {
				major = Major.BCA.toString();
			}
			request.setAttribute("selected_major", major);
			List <Major> majors = new ArrayList <> ();
			String majors_regex = "";
			for (Major value : Major.values()) {
				if (value.getMajorEnum().getIsActive()) {
					majors.add(value);
				}
				majors_regex += value.toString() +"|";
			}
			request.setAttribute("majors", majors);
			majors_regex = majors_regex.substring(0, majors_regex.length() -1);
			request.setAttribute("majors_regex", majors_regex);
			
			request.setAttribute("bacSeries", BacSerie.values());
			request.setAttribute("bacHonours", BacHonour.values());
			
			request.setAttribute("path_to_logo", "/resources/images/logos/" +major +"_logo_text.png");
			
			int current_year = DateManager.getCurrentYear();
			request.setAttribute("current_year", current_year);
			request.setAttribute("max_year", (current_year +1));	
			request.setAttribute("min_age", DateManager.toString(DateManager.getDateFromNow(17)));
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OnlineRegistrations onlineRegistrations = OnlineRegManager.validate(request.getParameter("massar_code"), request.getParameter("ar_first_name"), request.getParameter("ar_last_name"), request.getParameter("first_name"), request.getParameter("last_name"), request.getParameter("cin"), request.getParameter("nationality"), request.getParameter("gender"), request.getParameter("birthdate"), request.getParameter("ar_birth_place"), request.getParameter("birth_place"), request.getParameter("residence_town"), request.getParameter("province"), request.getParameter("bac_year"), request.getParameter("high_school"), request.getParameter("bac_place"), request.getParameter("academy"), request.getParameter("bac_serie"), request.getParameter("bac_honour"), request.getParameter("major"));
		if (onlineRegistrations == null) {
			ServletManager.setInvalideFormError(request, response);
			return;
		}
		onlineRegistrations.insert();
		ServletManager.setCreatedSucces("Your Application", null, request, response);
			
	}

}
