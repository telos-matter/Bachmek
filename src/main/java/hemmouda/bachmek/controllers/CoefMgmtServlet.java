package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadYearManager;
import hemmouda.bachmek.business.CoefManager;
import hemmouda.bachmek.business.CoefManager.CoefEnum;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Assessment;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/coef/list", "/coef/create"})
public class CoefMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CoefMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/coef/";
		
		Instructor instructor = ((User) request.getSession().getAttribute("user")).getInstructor();
		
		if (URN.equals("/coef/list")) {
			
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				academicYear = AcadYearManager.getLatest();
			}
			CoefEnum coefEnum = CoefEnum.getCoefEnum(request.getParameter("coefEnum"));
			if (coefEnum == null) {
				coefEnum = CoefEnum.ASSMNT_COEF;
			}
			switch (coefEnum) {
			case ASSMNT_COEF: path += "assmt_coef_list.jsp"; break;
			case ELEMENT_COEF: path += "element_coef_list.jsp"; break;
			case MODULE_COEF_BOUND: path += "module_coef_bound_list.jsp"; break;
			case ACAD_STAGE_BOUND: path += "acad_stage_bound_list.jsp"; break;
			case SEMESTER_BOUND: path += "semester_bound_list.jsp"; break;
			}
			request.setAttribute("canCreate", (CoefManager.getInstructor(coefEnum, academicYear, instructor) != null));
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
			request.setAttribute("coefEnum", coefEnum);
			request.setAttribute("coefEnums_list", CoefEnum.values());
			request.setAttribute("list", CoefManager.getCoefList(coefEnum, academicYear, instructor));
			
		} else if (URN.equals("/coef/create")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			CoefEnum coefEnum = CoefEnum.getCoefEnum(request.getParameter("coefEnum"));
			if (coefEnum == null) {
				ServletManager.setError(400, "The HTTP request is incomplete.", request, response);
				return;
			}
			switch (coefEnum) {
			case ASSMNT_COEF:
				case ELEMENT_COEF:
					path += "create_coef.jsp"; break;
			case MODULE_COEF_BOUND: 
				case ACAD_STAGE_BOUND: 
				case SEMESTER_BOUND: 
					path += "create_bound.jsp"; break;
			}
			request.setAttribute("extra", (coefEnum == CoefEnum.MODULE_COEF_BOUND));
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("coefEnum", coefEnum);
			request.setAttribute("associated_list", CoefManager.getPossibleCreate(coefEnum, academicYear, instructor));
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/coef/";
		
		Instructor instructor = ((User) request.getSession().getAttribute("user")).getInstructor();
		
		if (URN.equals("/coef/create")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			CoefEnum coefEnum = CoefEnum.getCoefEnum(request.getParameter("coefEnum"));
			if (coefEnum == null) {
				ServletManager.setError(400, "The HTTP request is incomplete.", request, response);
				return;
			}
			switch (coefEnum) {
			case ASSMNT_COEF: {
				Assessment assessment = ServletManager.assertEntity("associated_id", Assessment.class, request, response);
				if (assessment == null) {
					return;
				}
				Integer coefficient = ServletManager.assertParameter("coefficient", Integer.class, request, response);
				if (coefficient == null) {
					return;
				}
				CoefManager.create(coefEnum, academicYear, instructor, assessment, coefficient, null, null);
				break;
			}
			case ELEMENT_COEF: {
				Element element = ServletManager.assertEntity("associated_id", Element.class, request, response);
				if (element == null) {
					return;
				}
				Integer coefficient = ServletManager.assertParameter("coefficient", Integer.class, request, response);
				if (coefficient == null) {
					return;
				}
				CoefManager.create(coefEnum, academicYear, instructor, element, coefficient, null, null);
				break;
			}
			case MODULE_COEF_BOUND: {
				Module module = ServletManager.assertEntity("associated_id", Module.class, request, response);
				if (module == null) {
					return;
				}
				Integer coefficient = ServletManager.assertParameter("coefficient", Integer.class, request, response);
				if (coefficient == null) {
					return;
				}
				Float passing_bound = ServletManager.assertParameter("passing_bound", Float.class, request, response);
				if (passing_bound == null) {
					return;
				}
				Float failling_bound = ServletManager.assertParameter("failling_bound", Float.class, request, response);
				if (failling_bound == null) {
					return;
				}
				CoefManager.create(coefEnum, academicYear, instructor, module, coefficient, passing_bound, failling_bound);
				break;
			}
			case ACAD_STAGE_BOUND: {
				AcademicStage academicStage = ServletManager.assertEntity("associated_id", AcademicStage.class, request, response);
				if (academicStage == null) {
					return;
				}
				Float passing_bound = ServletManager.assertParameter("passing_bound", Float.class, request, response);
				if (passing_bound == null) {
					return;
				}
				CoefManager.create(coefEnum, academicYear, instructor, academicStage, null, passing_bound, null);
				break;
			}
			case SEMESTER_BOUND: {
				AcademicSemester academicSemester = ServletManager.assertEntity("associated_id", AcademicSemester.class, request, response);
				if (academicSemester == null) {
					return;
				}
				Float passing_bound = ServletManager.assertParameter("passing_bound", Float.class, request, response);
				if (passing_bound == null) {
					return;
				}
				CoefManager.create(coefEnum, academicYear, instructor, academicSemester, null, passing_bound, null);
				break;
			}
			}
			ServletManager.setCreatedSucces(coefEnum.getName(), null, request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);	
	}

}
