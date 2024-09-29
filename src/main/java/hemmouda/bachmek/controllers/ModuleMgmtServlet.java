package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.ServletManager;
import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.SubjectsManager;

@WebServlet (urlPatterns= {"/module/list", "/module", "/module/create", "/module/edit", "/module/delete", "/module/activate"})
public class ModuleMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ModuleMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/module/";
		
		if (URN.equals("/module")) {
			
			Module module = ServletManager.assertEntity(Module.class, request, response);
			if (module == null) {
				return;
			}
			path += "module_info.jsp";
			request.setAttribute("item", module);
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", Activatable.filterActive(module.getElements()));
			} else {
				request.setAttribute("list", module.getElements());
			}
			
		} else if (URN.equals("/module/list")) {
			
			path += "modules_list.jsp";
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", Activatable.filterActive(Module.class));
			} else {
				request.setAttribute("list", Manager.selectAll(Module.class));
			}
			
		} else if (URN.equals("/module/create")) {
			
			path += "create_module.jsp";
			
		} else if (URN.equals("/module/edit")) {
			
			Module module = ServletManager.assertEntity(Module.class, request, response);
			if (module == null) {
				return;
			}
			path += "edit_module.jsp";
			request.setAttribute("item", module);
			
		} else if (URN.equals("/module/delete")) {
			
			Module module = ServletManager.assertEntity(Module.class, request, response);
			if (module == null) {
				return;
			}
			if (Manager.deleteIfPossible(Module.class, module.getId())) {
				ServletManager.setDeletedSucces(module.getName(), module.getClass(), request, response);
				return;
			} else {
				ServletManager.setDeletedFailure(module.getName(), module.getClass(), request, response);
				return;
			}
			
		} else if (URN.equals("/module/activate")) {
			
			Module module = ServletManager.assertEntity(Module.class, request, response);
			if (module == null) {
				return;
			}
			module.setIsActive(!module.getIsActive());
			module.update();
			ServletManager.setUpdatedSucces(module.getName(), module.getClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/module/";
		
		if (URN.equals("/module/create")) {
			
			Module module = SubjectsManager.validateModule(request.getParameter("name"), request.getParameter("isActive"));
			if (module == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (SubjectsManager.moduleExist(module.getName()) == null) {
				module.insert();
				ServletManager.setCreatedSucces(module.getName(), module.getClass(), request, response);
				return;
			}
			path += "create_module.jsp";
			request.setAttribute("error_message", "This Module already exist.");
			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("isActive", request.getParameter("isActive"));
			
		} else if (URN.equals("/module/edit")) {
			
			Module module = ServletManager.assertEntity(Module.class, request, response);
			if (module == null) {
				return;
			}
			Module validated_module = SubjectsManager.validateModule(request.getParameter("name"), request.getParameter("isActive"));
			if (validated_module == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (module.getName().equals(validated_module.getName()) || SubjectsManager.moduleExist(validated_module.getName()) == null) {
				module.setName(validated_module.getName());
				module.setIsActive(validated_module.getIsActive());
				module.update();
				ServletManager.setUpdatedSucces(module.getName(), module.getClass(), request, response);
				return;
			} else {
				path += "edit_module.jsp";
				request.setAttribute("error_message", "This Module already exist.");
				request.setAttribute("item", module);
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);		
	}

}
