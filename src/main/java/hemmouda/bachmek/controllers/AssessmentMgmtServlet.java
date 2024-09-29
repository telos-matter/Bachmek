package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.SubjectsManager;
import hemmouda.bachmek.models.Assessment;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.util.ServletManager;
import hemmouda.bachmek.util.StringManager;

@WebServlet (urlPatterns= {"/assessment/create", "/assessment/edit", "/assessment/delete", "/assessment/activate"})
public class AssessmentMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AssessmentMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/assessment/";
		
		if (URN.equals("/assessment/create")) {
			
			Element element = ServletManager.assertEntity("element_id", Element.class, request, response);
			if (element == null) {
				return;
			}
			path += "create_assessment.jsp";
			request.setAttribute("element", element);
			
		} else if (URN.equals("/assessment/edit")) {
			
			Assessment assessment = ServletManager.assertEntity(Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			path += "edit_assessment.jsp";
			request.setAttribute("item", assessment);
			
		} else if (URN.equals("/assessment/delete")) {
			
			Assessment assessment = ServletManager.assertEntity(Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			if (Manager.deleteIfPossible(Assessment.class, assessment.getId())) {
				ServletManager.setDeletedSucces(assessment.getName(), assessment.getClass(), request, response);
				return;
			} else {
				ServletManager.setDeletedFailure(assessment.getName(), assessment.getClass(), request, response);
				return;
			}
			
		} else if (URN.equals("/assessment/activate")) {
			
			Assessment assessment = ServletManager.assertEntity(Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			if (!assessment.getElement().getIsActive()) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			assessment.setIsActive(!assessment.getIsActive());
			assessment.update();
			ServletManager.setUpdatedSucces(assessment.getName(), assessment.getClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/assessment/";
		
		if (URN.equals("/assessment/create")) {
			
			Element element = ServletManager.assertEntity("element_id", Element.class, request, response);
			if (element == null) {
				return;
			}
			Assessment assessment = SubjectsManager.validateAssessment(element, request.getParameter("name"), request.getParameter("isActive"));
			if (assessment == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!element.getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (SubjectsManager.assessmentExist(element, assessment.getName()) == null) {
				assessment.insert();
				ServletManager.setCreatedSucces(assessment.getName(), assessment.getClass(), request, response);
				return;
			}
			path += "create_assessment.jsp";
			request.setAttribute("error_message", "This Assessment already exist.");
			request.setAttribute("element", element);
			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("isActive", request.getParameter("isActive"));
			
		} else if (URN.equals("/assessment/edit")) {
			
			Assessment assessment = ServletManager.assertEntity(Assessment.class, request, response);
			if (assessment == null) {
				return;
			}
			Assessment validated_assessment = SubjectsManager.validateAssessment(assessment.getElement(), request.getParameter("name"), request.getParameter("isActive"));
			if (validated_assessment == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!assessment.getElement().getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (assessment.getName().equals(validated_assessment.getName()) || SubjectsManager.assessmentExist(assessment.getElement(), validated_assessment.getName()) == null) {
				assessment.setName(validated_assessment.getName());
				assessment.setIsActive(validated_assessment.getIsActive());
				assessment.update();
				ServletManager.setUpdatedSucces(assessment.getName(), assessment.getClass(), request, response);
				return;
			} else {
				path += "edit_assessment.jsp";
				request.setAttribute("error_message", "This Assessment already exist.");
				request.setAttribute("item", assessment);
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);		
	}

}
