package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadYearManager;
import hemmouda.bachmek.business.GradeManager;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Assessment;
import hemmouda.bachmek.models.AssessmentGrade;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.PedagogicalRegistration;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.ExcelManager;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/grade/list", "/grade/create", "/grade/edit", "/grade/approve", "/grade/file", "/grade/upload", "/grade/download"})
@MultipartConfig (
		  fileSizeThreshold = 1024 * 1024 * 1,
		  maxFileSize = 1024 * 1024 * 10,
		  maxRequestSize = 1024 * 1024 * 15
		)
public class GradeMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GradeMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/grade/";
		
		Instructor instructor = ((User) request.getSession().getAttribute("user")).getInstructor();
		
		if (URN.equals("/grade/list")) {
			
			AcademicYear academicYear = ServletManager.getEntity("acadYear_id", AcademicYear.class, request);
			if (academicYear == null) {
				academicYear = AcadYearManager.getLatest();
			}
			List <Assessment> assessments_list = GradeManager.getAssessmentsList(academicYear, instructor);
			Assessment assessment = ServletManager.getEntity("assessment_id", Assessment.class, request);
			if (assessment == null && assessments_list.size() != 0) {
				assessment = assessments_list.get(0);
				
			} 
			if (assessments_list.size() != 0) {
				path += "grades_list.jsp";
				boolean isApproved = GradeManager.isApproved(academicYear, assessment);
				request.setAttribute("isApproved", isApproved);
				request.setAttribute("canBeApproved", (GradeManager.canBeApproved(academicYear, assessment) && !isApproved));
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
				request.setAttribute("assessment", assessment);
				request.setAttribute("assessments_list", assessments_list);
				request.setAttribute("list", GradeManager.getGradesList(academicYear, assessment));
			} else {
				path += "no_assessment_grades_list.jsp";
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("acadYears_list", Manager.selectAll(AcademicYear.class));
				request.setAttribute("error_message", "You have no assessments for this year!");
			}
		
		} else if (URN.equals("/grade/create")) {
			
			PedagogicalRegistration pedagogicalRegistration = ServletManager.assertEntity("pedagogicalRegistration_id", PedagogicalRegistration.class, request, response);
			if (pedagogicalRegistration == null) {
				return;
			}
			Assessment assessment = ServletManager.assertEntity("assessment_id", Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			AcademicYear academicYear = ServletManager.assertEntity("academicYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			path += "create_grade.jsp";
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("assessment", assessment);
			request.setAttribute("pedagogicalRegistration", pedagogicalRegistration);
			
		} else if (URN.equals("/grade/edit")) {
			
			AssessmentGrade assessmentGrade = ServletManager.assertEntity(AssessmentGrade.class, request, response);
			if (assessmentGrade == null) {
				return;
			}
			path += "edit_grade.jsp";
			request.setAttribute("item", assessmentGrade);
			
		} else if (URN.equals("/grade/file")) {
			
			Assessment assessment = ServletManager.assertEntity("assessment_id", Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			path += "grade_file.jsp";
			request.setAttribute("acadYear", academicYear);
			request.setAttribute("assessment", assessment);
			
		} else if (URN.equals("/grade/download")) {
			
			Assessment assessment = ServletManager.assertEntity("assessment_id", Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			GradeManager.writeDownloadList(academicYear, assessment, response);
			return;
			
		} else if (URN.equals("/grade/approve")) {
			
			Assessment assessment = ServletManager.assertEntity("assessment_id", Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			GradeManager.approve(academicYear, assessment);
			ServletManager.setUpdatedSucces("The Assessments' approving", null, request, response);
			return; 
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/grade/";
		
		if (URN.equals("/grade/create")) {
			
			PedagogicalRegistration pedagogicalRegistration = ServletManager.assertEntity("pedagogicalRegistration_id", PedagogicalRegistration.class, request, response);
			if (pedagogicalRegistration == null) {
				return;
			}
			Assessment assessment = ServletManager.assertEntity("assessment_id", Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			AcademicYear academicYear = ServletManager.assertEntity("academicYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			Float normal_session = ServletManager.assertParameter("normal_session", Float.class, request, response);
			if (normal_session == null) {
				return;
			}
			GradeManager.create(academicYear, assessment, pedagogicalRegistration, normal_session);
			ServletManager.setCreatedSucces("", AssessmentGrade.class, request, response);
			return;
			
		} else if (URN.equals("/grade/edit")) {
			
			AssessmentGrade assessmentGrade = ServletManager.assertEntity(AssessmentGrade.class, request, response);
			if (assessmentGrade == null) {
				return;
			}
			Float normal_session = ServletManager.assertParameter("normal_session", Float.class, request, response);
			if (normal_session == null) {
				return;
			}
			Float catch_up_session = ServletManager.getParameter("catch_up_session", Float.class, request);
			GradeManager.edit(assessmentGrade, normal_session, catch_up_session);
			ServletManager.setUpdatedSucces("", AssessmentGrade.class, request, response);
			return;
			
		} else if (URN.equals("/grade/upload")) {
			
			Assessment assessment = ServletManager.assertEntity("assessment_id", Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			AcademicYear academicYear = ServletManager.assertEntity("acadYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			String file_name = ServletManager.saveExcelFile(request);
			if (file_name == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			List <String []> data = ExcelManager.read(file_name);
			ExcelManager.FileManager.deleteFile(file_name);
			if (data == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (data.size() == 0) {
				path += "grade_file.jsp";
				request.setAttribute("error_message", "The uploaded sheet is empty.");
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("assessment", assessment);
			} else if (data.get(0).length != GradeManager.FILE_HEADER.length) {
				path += "grade_file.jsp";
				request.setAttribute("error_message", "The uploaded sheet doesn't match the template.");
				request.setAttribute("acadYear", academicYear);
				request.setAttribute("assessment", assessment);
			} else {
				int count = GradeManager.handleUploadList(data, academicYear, assessment);
				if (count == 0) {
					path += "grade_file.jsp";
					request.setAttribute("error_message", "The uploaded sheet has missing or contradictory data.");
				} else {
					ServletManager.setSucces(201,  "" +count +" Grade total has been created/updated succesfully.", request, response);
					return;
				}
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
