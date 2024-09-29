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
import hemmouda.bachmek.business.GradeManager;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.models.SemesterModule;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/approvement/list", "/approvement/approve"})
public class ApprovementMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ApprovementMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/approvement/";
		
		if (URN.equals("/approvement/list")) {
			
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				academicYear = AcadYearManager.getLatest();
			}
			List <AcademicSemester> acadSemesters_list = Manager.selectAll(AcademicSemester.class);
			if (acadSemesters_list.size() == 0) {
				ServletManager.setError(404, "First create an AcademicSemester.", request, response);
				return;
			}
			AcademicSemester academicSemester = ServletManager.getEntity("acadSemester_id", AcademicSemester.class, request);
			if (academicSemester == null) {
				academicSemester = acadSemesters_list.get(0);
			} 
			String [] fields = {"academicSemester", "academicYear"};
			Object [] values = {academicSemester, academicYear};
			Semester semester = Manager.selectUnique(Semester.class, fields, values);
			path += "list.jsp";
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
			request.setAttribute("acadSemester", academicSemester);
			request.setAttribute("acadSemesters_list", acadSemesters_list);
			request.setAttribute("semester", semester);
			request.setAttribute("list", GradeManager.getSemesterModuleList(semester));
			
		} else if (URN.equals("/approvement/approve")) {
			
			Boolean approve = ServletManager.assertParameter("approve", Boolean.class, request, response);
			if (approve == null) {
				return;
			}
			SemesterModule semesterModule = ServletManager.assertEntity("semesterModule_id", SemesterModule.class, request, response);
			if (semesterModule == null) {
				return;
			}
			GradeManager.approve(semesterModule.getSemester().getAcademicYear(), semesterModule.getModule(), approve);
			ServletManager.setUpdatedSucces("The Module Approvement", null, request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
