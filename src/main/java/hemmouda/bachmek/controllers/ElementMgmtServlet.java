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
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.ServletManager;
import hemmouda.bachmek.util.StringManager;

@WebServlet (urlPatterns= {"/element", "/element/create", "/element/edit", "/element/delete", "/element/activate"})
public class ElementMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ElementMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/element/";
		
		if (URN.equals("/element")) {
			
			Element element = ServletManager.assertEntity(Element.class, request, response);
			if (element == null) {
				return;
			}
			path += "element_info.jsp";
			request.setAttribute("item", element);
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", Activatable.filterActive(element.getAssessments()));
			} else {
				request.setAttribute("list", element.getAssessments());
			}
			
		} else if (URN.equals("/element/create")) {
			
			Module module = ServletManager.assertEntity("module_id", Module.class, request, response);
			if (module == null) {
				return;
			}
			path += "create_element.jsp";
			request.setAttribute("module", module);
			request.setAttribute("create", true);
			
		} else if (URN.equals("/element/edit")) {
			
			Element element = ServletManager.assertEntity(Element.class, request, response);
			if (element == null) {
				return;
			}
			path += "edit_element.jsp";
			request.setAttribute("item", element);
			
		} else if (URN.equals("/element/delete")) {
			
			Element element = ServletManager.assertEntity(Element.class, request, response);
			if (element == null) {
				return;
			}
			if (Manager.deleteIfPossible(Element.class, element.getId())) {
				ServletManager.setDeletedSucces(element.getName(), element.getClass(), request, response);
				return;
			} else {
				ServletManager.setDeletedFailure(element.getName(), element.getClass(), request, response);
				return;
			}
			
		} else if (URN.equals("/element/activate")) {
			
			Element element = ServletManager.assertEntity(Element.class, request, response);
			if (element == null) {
				return;
			}
			if (!element.getModule().getIsActive()) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			element.setIsActive(!element.getIsActive());
			element.update();
			ServletManager.setUpdatedSucces(element.getName(), element.getClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/element/";
		
		if (URN.equals("/element/create")) {
			
			Module module = ServletManager.assertEntity("module_id", Module.class, request, response);
			if (module == null) {
				return;
			}
			Element element = SubjectsManager.validateElement(module, request.getParameter("name"), request.getParameter("isActive"));
			if (element == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!module.getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (SubjectsManager.elementExist(module, element.getName()) == null) {
				element.insert();
				if (request.getParameter("create") != null) {
					Assessment pw = new Assessment ();
					pw.setElement(element);
					pw.setName("pw");
					pw.setIsActive(element.getIsActive());
					pw.insert();
					Assessment exam = new Assessment ();
					exam.setElement(element);
					exam.setName("exam");
					exam.setIsActive(element.getIsActive());
					exam.insert();
				}
				ServletManager.setCreatedSucces(element.getName(), element.getClass(), request, response);
				return;
			}
			path += "create_element.jsp";
			request.setAttribute("error_message", "This Element already exist.");
			request.setAttribute("module", module);
			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("isActive", request.getParameter("isActive"));
			request.setAttribute("create", request.getParameter("create"));
			
		} else if (URN.equals("/element/edit")) {
			
			Element element = ServletManager.assertEntity(Element.class, request, response);
			if (element == null) {
				return;
			}
			Element validated_element = SubjectsManager.validateElement(element.getModule(), request.getParameter("name"), request.getParameter("isActive"));
			if (validated_element == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!element.getModule().getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (element.getName().equals(validated_element.getName()) || SubjectsManager.elementExist(element.getModule(), validated_element.getName()) == null) {
				element.setName(validated_element.getName());
				element.setIsActive(validated_element.getIsActive());
				element.update();
				ServletManager.setUpdatedSucces(element.getName(), element.getClass(), request, response);
				return;
			} else {
				path += "edit_element.jsp";
				request.setAttribute("error_message", "This Element already exist.");
				request.setAttribute("item", element);
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);		
	}

}
