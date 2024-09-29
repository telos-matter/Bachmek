package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadYearManager;
import hemmouda.bachmek.business.MMManager;
import hemmouda.bachmek.business.MMManager.MMType;
import hemmouda.bachmek.business.UserManager;
import hemmouda.bachmek.business.UserManager.Role;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/mm", "/mm/download"})
public class MMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MMServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/mm/";
		
		User user = (User) request.getSession().getAttribute("user");
		Role role = UserManager.getRole(user);
		
		
		if (URN.equals("/mm")) {
			
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				academicYear = AcadYearManager.getLatest();
			}
			List <MMType> types_list = null;
			if (role == Role.ADMINISTRATOR) {
				types_list = MMType.valuesAsList();
			} else {
				types_list = MMType.instructorValues(user.getInstructor(), academicYear);
			}
			if (types_list.size() == 0) {
				path += "error_list.jsp";
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
			} else {
				MMType type = MMType.getMMType(request.getParameter("type"));
				if (type == null) {
					if (types_list.contains(MMType.SEMESTER)) {
						type = MMType.SEMESTER;
					} else {
						type = types_list.get(0);
					}
				}
				switch (type) {
				case STAGE: case MODULE: path += "list.jsp"; break;
				case SEMESTER: path += "semester_list.jsp"; break;
				case ELEMENT: path += "element_list.jsp"; break;
				}
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
				request.setAttribute("type", type);
				request.setAttribute("types_list", types_list);
				if (role == Role.ADMINISTRATOR) {
					request.setAttribute("list", MMManager.getList(academicYear, type));
				} else {
					request.setAttribute("list", MMManager.getList(academicYear, type, user.getInstructor()));
				}
			}
			
		} else if (URN.equals("/mm/download")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			MMType type = MMType.getMMType(request.getParameter("type"));
			if (type == null) {
				ServletManager.setError(400, "The HTTP request is incomplete.", request, response);
				return;
			}
			Object item = ServletManager.assertEntity(type.getCorrespondingClass(), request, response);
			if (item == null) {
				return;
			}
			if (type == MMType.SEMESTER) {
				Boolean normal = ServletManager.assertParameter("normal", Boolean.class, request, response);
				if (normal == null) {
					return;
				}
				MMManager.writeDownloadList(type, academicYear, item, normal, response);
				return;
			} else {
				MMManager.writeDownloadList(type, academicYear, item, null, response);
				return;
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
