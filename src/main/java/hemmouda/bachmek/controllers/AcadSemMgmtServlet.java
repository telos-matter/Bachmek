package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadStepsManager;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.util.ServletManager;
import hemmouda.bachmek.util.StringManager;

@WebServlet (urlPatterns= {"/acadSem/create", "/acadSem/edit", "/acadSem/delete", "/acadSem/activate"})
public class AcadSemMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AcadSemMgmtServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/acad_sem/";
		
		if (URN.equals("/acadSem/create")) {
			
			AcademicStage academicStage = ServletManager.assertEntity("acadStage_id", AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			path += "create_semester.jsp";
			request.setAttribute("acadStage", academicStage);
			request.setAttribute("max_sequence", academicStage.getAcademicSemesters().size() +1);
			
		} else if (URN.equals("/acadSem/edit")) {
			
			AcademicSemester academicSemester = ServletManager.assertEntity(AcademicSemester.class, request, response);
			if (academicSemester == null) {
				return;
			}
			path += "edit_semester.jsp";
			request.setAttribute("item", academicSemester);
			request.setAttribute("max_sequence", academicSemester.getAcademicStage().getAcademicSemesters().size());
			
		} else if (URN.equals("/acadSem/delete")) {
			
			AcademicSemester academicSemester = ServletManager.assertEntity(AcademicSemester.class, request, response);
			if (academicSemester == null) {
				return;
			}
			if (Manager.deleteIfPossible(AcademicSemester.class, academicSemester.getId())) {
				if (academicSemester.getSequence() != academicSemester.getAcademicStage().getAcademicSemesters().size()) {
					academicSemester.getAcademicStage().getAcademicSemesters().remove(academicSemester);
					for (AcademicSemester acadSem : AcadStepsManager.collapseSemestersSequences(academicSemester.getAcademicStage())) {
						acadSem.update();
					}
				}
				ServletManager.setDeletedSucces("#" +academicSemester.getSequence(), academicSemester.getClass(), request, response);
				return;
			} else {
				ServletManager.setDeletedFailure("#" +academicSemester.getSequence(), academicSemester.getClass(), request, response);
				return;
			}
			
		} else if (URN.equals("/acadSem/activate")) {
			
			AcademicSemester academicSemester = ServletManager.assertEntity(AcademicSemester.class, request, response);
			if (academicSemester == null) {
				return;
			}
			if (!academicSemester.getAcademicStage().getIsActive()) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			academicSemester.setIsActive(!academicSemester.getIsActive());
			academicSemester.update();
			ServletManager.setUpdatedSucces("#" +academicSemester.getSequence(), academicSemester.getClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		
		if (URN.equals("/acadSem/create")) {
			
			AcademicStage academicStage = ServletManager.assertEntity("acadStage_id", AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			AcademicSemester academicSemester = AcadStepsManager.validateSemester(academicStage, request.getParameter("sequence"), request.getParameter("isActive"));
			if (academicSemester == null || academicSemester.getSequence() < 1 || academicSemester.getSequence() > academicSemester.getAcademicStage().getAcademicSemesters().size() +1) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!academicStage.getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			AcademicSemester similar_semester = AcadStepsManager.getSemesterWithSequence(academicSemester.getSequence(), academicStage);
			if (similar_semester != null) {
				similar_semester.setSequence(academicSemester.getAcademicStage().getAcademicSemesters().size() +1);
				similar_semester.update();
			}
			academicSemester.insert();
			ServletManager.setCreatedSucces("#" +academicSemester.getSequence(), academicSemester.getClass(), request, response);
			return;
			
		} else if (URN.equals("/acadSem/edit")) {
			
			AcademicSemester academicSemester = ServletManager.assertEntity(AcademicSemester.class, request, response);
			if (academicSemester == null) {
				return;
			}
			AcademicSemester validated_academicSemester = AcadStepsManager.validateSemester(academicSemester.getAcademicStage(), request.getParameter("sequence"), request.getParameter("isActive"));
			if (validated_academicSemester == null || validated_academicSemester.getSequence() < 1 || validated_academicSemester.getSequence() > academicSemester.getAcademicStage().getAcademicSemesters().size()) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!academicSemester.getAcademicStage().getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			academicSemester.setIsActive(validated_academicSemester.getIsActive());
			if (validated_academicSemester.getSequence() != academicSemester.getSequence()) {
				AcademicSemester swaped_academicSemester = AcadStepsManager.getSemesterWithSequence(validated_academicSemester.getSequence(), academicSemester.getAcademicStage());
				swaped_academicSemester.setSequence(academicSemester.getAcademicStage().getAcademicSemesters().size() +1);
				swaped_academicSemester.update();
				swaped_academicSemester.setSequence(academicSemester.getSequence());
				academicSemester.setSequence(validated_academicSemester.getSequence());
				academicSemester.update();
				swaped_academicSemester.update();
			} else {
				academicSemester.update();
			}
			ServletManager.setUpdatedSucces("#" +academicSemester.getSequence(), academicSemester.getClass(), request, response);
			return;
		}
		
	}

}
