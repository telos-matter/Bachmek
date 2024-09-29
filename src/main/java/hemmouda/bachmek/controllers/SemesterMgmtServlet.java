package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadYearManager;
import hemmouda.bachmek.business.SemesterManager;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/semester/list", "/semester", "/semester/create", "/semester/delete"})
public class SemesterMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SemesterMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/semester/";
		
		if (URN.equals("/semester")) {
			
			Semester semester = ServletManager.assertEntity(Semester.class, request, response);
			if (semester == null) {
				return;
			}
			path += "semester_info.jsp";
			request.setAttribute("item", semester);
			request.setAttribute("list", semester.getModules());
			
		} else if (URN.equals("/semester/list")) {
			
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				academicYear = AcadYearManager.getLatest();
			}
			path += "semester_list.jsp";
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
			request.setAttribute("list", Manager.selectMultiple(Semester.class, "academicYear", academicYear));
			
		} else if (URN.equals("/semester/create")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			List <AcademicSemester> academicSemesters = SemesterManager.filterSemesters(academicYear);
			AcademicSemester academicSemester = ServletManager.getEntity("acadSem_id", AcademicSemester.class, request);
			if (academicSemester == null) {
				if (!academicSemesters.isEmpty()) {
					academicSemester = academicSemesters.get(0);
				}
			}
			path += "create_semester.jsp";
			request.setAttribute("acadSems_list", academicSemesters);
			request.setAttribute("acadSem", academicSemester);
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("modules_list", SemesterManager.getLatestWrapped(academicSemester));
			request.setAttribute("max_module", SemesterManager.SEMESTER_MODULE_LIMIT);
			
		} else if (URN.equals("/semester/delete")) {
			
			Semester semester = ServletManager.assertEntity(Semester.class, request, response);
			if (semester == null) {
				return;
			}
			if (Manager.deleteIfPossible(Semester.class, semester.getId())) {
				ServletManager.setDeletedSucces(semester.getName(), Semester.class, request, response);
				return;
			} else {
				ServletManager.setDeletedFailure(semester.getName(), Semester.class, request, response);
				return;
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		
		if (URN.equals("/semester/create")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			AcademicSemester academicSemester = ServletManager.assertEntity("acadSem_id", AcademicSemester.class, request, response);
			if (academicSemester == null) {
				return;
			}
			List <Module> modules = ServletManager.assertEntities("modules_ids", Module.class, request, response);
			if (modules == null) {
				return;
			}
			Semester semester = SemesterManager.validate(academicYear, academicSemester, modules);
			if (semester == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			semester.insert();
			ServletManager.setCreatedSucces(semester.getName(), Semester.class, request, response);
			return;
			
		}
		
	}

}
