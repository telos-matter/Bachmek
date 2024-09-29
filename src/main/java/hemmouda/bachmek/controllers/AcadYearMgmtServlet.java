package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadYearManager;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.util.DateManager;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {"/acadYear/list", "/acadYear/create", "/acadYear/edit", "/acadYear/delete"})
public class AcadYearMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AcadYearMgmtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/acad_year/";
		
		if (URN.equals("/acadYear/list")) {
			
			path += "years_list.jsp";
			request.setAttribute("list", Manager.selectAll(AcademicYear.class));
			
		} else if (URN.equals("/acadYear/create")) {
			
			path += "create_year.jsp";
			request.setAttribute("start_date", AcadYearManager.getDefaultStartDate());
			request.setAttribute("finish_date", AcadYearManager.getDefaultFinishDate());
			
		} else if (URN.equals("/acadYear/edit")) {
			
			AcademicYear academicYear = ServletManager.assertEntity(AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			path += "edit_year.jsp";
			request.setAttribute("item", academicYear);
			
		} else if (URN.equals("/acadYear/delete")) {
			
			AcademicYear academicYear = ServletManager.assertEntity(AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			if (Manager.deleteIfPossible(AcademicYear.class, academicYear.getId())) {
				ServletManager.setDeletedSucces(academicYear.getABBR(), AcademicYear.class, request, response);
				return;
			} else {
				ServletManager.setDeletedFailure(academicYear.getABBR(), AcademicYear.class, request, response);
				return;

			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/acad_year/";
		
		if (URN.equals("/acadYear/create")) {
			
			AcademicYear academicYear = AcadYearManager.validate(request.getParameter("start_date"), request.getParameter("finish_date"));
			if (academicYear == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			path += "create_year.jsp";
			if (academicYear.getStart_date().after(academicYear.getFinish_date()) || DateManager.equalsDate(academicYear.getStart_date(), academicYear.getFinish_date())) {
				request.setAttribute("start_date", request.getParameter("start_date"));
				request.setAttribute("finish_date", request.getParameter("finish_date"));
				request.setAttribute("error_message", "The two dates must be different and the start must be before the finish");
			} else if (AcadYearManager.intersects(academicYear, Manager.selectAll(AcademicYear.class), null)) {
				request.setAttribute("start_date", request.getParameter("start_date"));
				request.setAttribute("finish_date", request.getParameter("finish_date"));
				request.setAttribute("error_message", "The period of this Academic Year intersects with another");
			} else {
				academicYear.insert();
				ServletManager.setCreatedSucces(academicYear.getABBR(), AcademicYear.class, request, response);
				return;
			}
			
		} else if (URN.equals("/acadYear/edit")) {
			
			AcademicYear academicYear = ServletManager.assertEntity(AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			AcademicYear validated_academicYear = AcadYearManager.validate(request.getParameter("start_date"), request.getParameter("finish_date"));
			if (validated_academicYear == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			path += "edit_year.jsp";
			if (validated_academicYear.getStart_date().after(validated_academicYear.getFinish_date()) || DateManager.equalsDate(validated_academicYear.getStart_date(), validated_academicYear.getFinish_date())) {
				request.setAttribute("item", academicYear);
				request.setAttribute("error_message", "The two dates must be different and the start must be before the finish");
			} else if (AcadYearManager.intersects(validated_academicYear, Manager.selectAll(AcademicYear.class), academicYear)) {
				request.setAttribute("item", academicYear);
				request.setAttribute("error_message", "The period of this Academic Year intersects with another");
			} else {
				validated_academicYear.setId(academicYear.getId());
				validated_academicYear.update();
				ServletManager.setUpdatedSucces(validated_academicYear.getABBR(), AcademicYear.class, request, response);
				return;
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
