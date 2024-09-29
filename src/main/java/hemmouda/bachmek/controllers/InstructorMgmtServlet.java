package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadYearManager;
import hemmouda.bachmek.business.InstructorManager;
import hemmouda.bachmek.business.InstructorManager.InstructorRole;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/instructor/list", "/instructor/create", "/instructor/download"})
public class InstructorMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InstructorMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/instructor/";
		
		if (URN.equals("/instructor/list")) {
			
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				academicYear = AcadYearManager.getLatest();
			}
			InstructorRole instructorRole = InstructorRole.getInstructorRole(request.getParameter("instructorRole"));
			if (instructorRole == null) {
				instructorRole = InstructorRole.ELEMENT_INSTRUCTOR;
			}
			path += "instructor_list.jsp";
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
			request.setAttribute("instructorRole", instructorRole);
			request.setAttribute("instructorRoles_list", InstructorRole.values());
			request.setAttribute("list", Manager.selectMultiple(instructorRole.getCorrespondingClass(), "academicYear", academicYear));
			
		} else if (URN.equals("/instructor/create")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			InstructorRole instructorRole = InstructorRole.getInstructorRole(request.getParameter("instructorRole"));
			if (instructorRole == null) {
				ServletManager.setError(400, "The HTTP request is incomplete.", request, response);
				return;
			}
			path += "create_instructor.jsp";
			request.setAttribute("extra", (instructorRole == InstructorRole.ELEMENT_INSTRUCTOR));
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("instructorRole", instructorRole);
			request.setAttribute("instructors_list", Activatable.filterActive(Instructor.class));
			request.setAttribute("items_list", InstructorManager.getFilteredList(academicYear, instructorRole));
			
		} else if (URN.equals("/instructor/download")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			InstructorRole instructorRole = InstructorRole.getInstructorRole(request.getParameter("instructorRole"));
			if (instructorRole == null) {
				ServletManager.setError(400, "The HTTP request is incomplete.", request, response);
				return;
			}
			InstructorManager.writeDownloadList(academicYear, instructorRole, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/instructor/";
		
		if (URN.equals("/instructor/create")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			InstructorRole instructorRole = InstructorRole.getInstructorRole(request.getParameter("instructorRole"));
			if (instructorRole == null) {
				ServletManager.setError(400, "The HTTP request is incomplete.", request, response);
				return;
			}
			Instructor instructor = ServletManager.assertEntity("instructor_id", Instructor.class, request, response);
			if (instructor == null) {
				return;
			}
			Object item = ServletManager.assertEntity("item_id", instructorRole.getAssociatedClass(), request, response);
			if (item == null) {
				return;
			}
			Object entity = InstructorManager.validateCreation(academicYear, instructorRole, instructor, item);
			if (entity == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			ServletManager.setCreatedSucces(instructor.getUser().getFullName(), instructorRole.getCorrespondingClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

}
