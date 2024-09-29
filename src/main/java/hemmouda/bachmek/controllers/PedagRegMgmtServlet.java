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
import hemmouda.bachmek.business.PedagRegManager;
import hemmouda.bachmek.business.SemesterManager;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.AdministrativeRegistration;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.PedagogicalRegistration;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/pedagReg/list", "/pedagReg/create", "/pedagReg/create/next", "/pedagReg/create/finish", "/pedagReg/download"})
public class PedagRegMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PedagRegMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/pedag_reg/";
		
		if (URN.equals("/pedagReg/list")) {
			
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				academicYear = AcadYearManager.getLatest();
			}
			List <AcademicSemester> acadSemesters_list = Manager.selectAll(AcademicSemester.class);
			if (acadSemesters_list.size() == 0) {
				ServletManager.setError(204, "First create an AcademicSemester", request, response);
				return;
			}
			AcademicSemester academicSemester = ServletManager.getEntity("acadSemester_id", AcademicSemester.class, request);
			if (academicSemester == null) {
				academicSemester = acadSemesters_list.get(0);
			}
			path += "list.jsp";
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
			request.setAttribute("acadSemester", academicSemester);
			request.setAttribute("acadSemesters_list", acadSemesters_list);
			String [] fields = {"academicYear", "academicSemester"};
			Object [] values = {academicYear, academicSemester};
			Semester semester = Manager.selectUnique(Semester.class, fields, values);
			request.setAttribute("semester", semester);
			request.setAttribute("list", MMManager.getRegisteredPedagRegList(semester));
			
		} else if (URN.equals("/pedagReg/create")) {
			
			path += "create_reg.jsp";
			request.setAttribute("acadStage_list", Activatable.filterActive(AcademicStage.class));
			request.setAttribute("acadYear_list", Manager.selectAll(AcademicYear.class));
			AcademicStage academicStage = ServletManager.getEntity("acadStage_id", AcademicStage.class, request);
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicStage == null && academicYear == null) {
				request.setAttribute("list", Activatable.filterActive(AdministrativeRegistration.class));
			} else if (academicStage == null) {
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("list", Activatable.filterActive(Manager.selectMultiple(AdministrativeRegistration.class, "academicYear", academicYear)));
			} else if (academicYear == null) {
				request.setAttribute("acadStage", academicStage);
				request.setAttribute("list", Activatable.filterActive(Manager.selectMultiple(AdministrativeRegistration.class, "academicStage", academicStage)));
			} else {
				request.setAttribute("acadStage", academicStage);
				request.setAttribute("acadYear", academicYear);
				String [] fields = {"academicYear", "academicStage"};
				Object [] values = {academicYear, academicStage};
				request.setAttribute("list", Activatable.filterActive(Manager.selectMultiple(AdministrativeRegistration.class, fields, values)));
			}
			
		} else if (URN.equals("/pedagReg/create/next")) {
			
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity("adminReg_id", AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			if (!administrativeRegistration.getIsActive()) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				AcademicYear current_year = AcadYearManager.getEarliestCurrent();
				if (current_year == null) {
					request.setAttribute("error_message", "First create a valide Academic Year. The Online Reg. has been created.");
					response.sendRedirect(request.getContextPath() +"/acadYear/create");
					return;
				}
				path += "create_reg_next.jsp";
				request.setAttribute("adminReg", administrativeRegistration);
				request.setAttribute("acadYear", current_year);
				request.setAttribute("acadYear_list", AcadYearManager.getAfterOrEqual(current_year));
				request.setAttribute("semester_list", SemesterManager.getFilteredAboveOrEqual(current_year, administrativeRegistration.getAcademicStage()));
			} else {
				AcademicYear current_year = AcadYearManager.getEarliestCurrent();
				if (current_year == null) {
					ServletManager.setCorruptedData(request, response);
					return;
				}
				List <AcademicYear> acadYear_list = AcadYearManager.getAfterOrEqual(current_year);
				if (CollectionManager.contains(acadYear_list, academicYear) == null) {
					ServletManager.setCorruptedData(request, response);
					return;
				}
				path += "create_reg_next.jsp";
				request.setAttribute("adminReg", administrativeRegistration);
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("acadYear_list", acadYear_list);
				request.setAttribute("semester_list", SemesterManager.getFilteredAboveOrEqual(academicYear, administrativeRegistration.getAcademicStage()));
			}
			
		} else if (URN.equals("/pedagReg/download")) {
			
			Semester semester = ServletManager.assertEntity(Semester.class, request, response);
			if (semester == null) {
				return;
			}
			PedagRegManager.writeDownloadList(semester, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/pedag_reg/";
		
		if (URN.equals("/pedagReg/create/next")) {
			AcademicYear current_year = AcadYearManager.getEarliestCurrent();
			if (current_year == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity("adminReg_id", AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			Semester semester = ServletManager.assertEntity("semester_id", Semester.class, request, response);
			if (semester == null) {
				return;
			}
			if (semester.isApproved()) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			PedagogicalRegistration pedagogicalRegistration = PedagRegManager.getPedagogicalRegistration(administrativeRegistration, semester);
			if (pedagogicalRegistration == null) {
				path += "create_reg_finish.jsp";
				request.setAttribute("view_size", PedagRegManager.STUDENT_MODULE_LIMIT);
				request.setAttribute("adminReg", administrativeRegistration);
				request.setAttribute("semester", semester);
				request.setAttribute("list", SemesterManager.getFilteredElements(semester));
			} else if (CollectionManager.contains(pedagogicalRegistration.getSemesters(), semester) != null) {
				path += "create_reg_next.jsp";
				request.setAttribute("error_message", "This student is already registered in the selected Semester");
				request.setAttribute("adminReg", administrativeRegistration);
				request.setAttribute("acadYear", semester.getAcademicYear());
				request.setAttribute("acadYear_list", AcadYearManager.getAfterOrEqual(current_year));
				request.setAttribute("semester_list", SemesterManager.getFilteredAboveOrEqual(semester.getAcademicYear(), administrativeRegistration.getAcademicStage()));
			} else if (! PedagRegManager.canAddElements(pedagogicalRegistration)) {
				path += "create_reg_next.jsp";
				request.setAttribute("error_message", "This can't take any more Modules");
				request.setAttribute("adminReg", administrativeRegistration);
				request.setAttribute("acadYear", semester.getAcademicYear());
				request.setAttribute("acadYear_list", AcadYearManager.getAfterOrEqual(current_year));
				request.setAttribute("semester_list", SemesterManager.getFilteredAboveOrEqual(semester.getAcademicYear(), administrativeRegistration.getAcademicStage()));
			} else {
				path += "create_reg_finish.jsp";
				request.setAttribute("view_size", PedagRegManager.STUDENT_MODULE_LIMIT);
				request.setAttribute("adminReg", administrativeRegistration);
				request.setAttribute("semester", semester);
				request.setAttribute("list", SemesterManager.getFilteredElements(semester));
			}
			
			
		} else if (URN.equals("/pedagReg/create/finish")) {
			
			AcademicYear current_year = AcadYearManager.getEarliestCurrent();
			if (current_year == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity("adminReg_id", AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			Semester semester = ServletManager.assertEntity("semester_id", Semester.class, request, response);
			if (semester == null) {
				return;
			}
			if (semester.isApproved()) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			List <Element> elements = ServletManager.assertEntities("elements_ids", Element.class, request, response);
			if (elements == null) {
				return;
			}
			if (!PedagRegManager.validateModulesCount(elements)) {
				path += "create_reg_finish.jsp";
				request.setAttribute("error_message", "You have selected Elements that accumulate to more than the allowed limit of Modules");
				request.setAttribute("view_size", PedagRegManager.STUDENT_MODULE_LIMIT);
				request.setAttribute("adminReg", administrativeRegistration);
				request.setAttribute("semester", semester);
				request.setAttribute("list", SemesterManager.getFilteredElements(semester));
			} else {
				PedagogicalRegistration pedagogicalRegistration = PedagRegManager.getPedagogicalRegistration(administrativeRegistration, semester);
				if (pedagogicalRegistration == null || PedagRegManager.canAddElements(pedagogicalRegistration, elements)) {
					pedagogicalRegistration = PedagRegManager.validatePedagogicalRegistration(pedagogicalRegistration, administrativeRegistration, semester, elements);
					if (pedagogicalRegistration == null) {
						ServletManager.setCorruptedData(request, response);
						return;
					} else {
						ServletManager.setCreatedSucces(pedagogicalRegistration.getFullName(), PedagogicalRegistration.class, request, response);
						return;
					}
				} else {
					path += "create_reg_finish.jsp";
					request.setAttribute("error_message", "You have selected Elements that accumulate (along side the other ones) to more than the allowed limit of Modules");
					request.setAttribute("view_size", PedagRegManager.STUDENT_MODULE_LIMIT);
					request.setAttribute("adminReg", administrativeRegistration);
					request.setAttribute("semester", semester);
					request.setAttribute("list", SemesterManager.getFilteredElements(semester));
				}
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
