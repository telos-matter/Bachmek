package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.MajorEnum;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/major/list", "/major", "/major/activate"})
public class MajorMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MajorMgmtServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/major/";
		
		if (URN.equals("/major")) {
			
			MajorEnum major = ServletManager.assertEntity(MajorEnum.class, request, response);
			if (major == null) {
				return;
			}
			path += "major_info.jsp";
			request.setAttribute("item", major);
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", Activatable.filterActive(major.getAcademicStages()));
			} else {
				request.setAttribute("list", major.getAcademicStages());
			}
			
		} else if (URN.equals("/major/list")) {
			
			path += "majors_list.jsp";
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", Activatable.filterActive(MajorEnum.class));
			} else {
				request.setAttribute("list", Manager.selectAll(MajorEnum.class));
			}
			
		} else if (URN.equals("/major/activate")) {
			
			MajorEnum major = ServletManager.assertEntity(MajorEnum.class, request, response);
			if (major == null) {
				return;
			}
			major.setIsActive(!major.getIsActive());
			major.update();
			ServletManager.setUpdatedSucces(major.getMajor().toString(), major.getClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
