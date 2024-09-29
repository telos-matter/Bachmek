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
import hemmouda.bachmek.models.MajorEnum;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.ServletManager;
import hemmouda.bachmek.util.StringManager;

@WebServlet (urlPatterns= {"/acadStage", "/acadStage/create", "/acadStage/edit", "/acadStage/delete", "/acadStage/activate"})
public class AcadStageMgmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AcadStageMgmtServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/acad_stage/";
		
		if (URN.equals("/acadStage")) {
			
			AcademicStage academicStage = ServletManager.assertEntity(AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			path += "stage_info.jsp";
			request.setAttribute("item", academicStage);
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", Activatable.filterActive(academicStage.getAcademicSemesters()));
			} else {
				request.setAttribute("list", academicStage.getAcademicSemesters());
			}
			
		} else if (URN.equals("/acadStage/create")) {
			
			MajorEnum major = ServletManager.assertEntity("major_id", MajorEnum.class, request, response);
			if (major == null) {
				return;
			}
			path += "create_stage.jsp";
			request.setAttribute("major", major);
			request.setAttribute("max_sequence", major.getAcademicStages().size() +1);
			
		} else if (URN.equals("/acadStage/edit")) {
			
			AcademicStage academicStage = ServletManager.assertEntity(AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			path += "edit_stage.jsp";
			request.setAttribute("item", academicStage);
			request.setAttribute("max_sequence", academicStage.getMajor().getAcademicStages().size());
			
		} else if (URN.equals("/acadStage/delete")) {
			
			AcademicStage academicStage = ServletManager.assertEntity(AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			if (Manager.deleteIfPossible(AcademicStage.class, academicStage.getId())) {
				if (academicStage.getSequence() != academicStage.getMajor().getAcademicStages().size()) {
					academicStage.getMajor().getAcademicStages().remove(academicStage);
					for (AcademicStage acadStage : AcadStepsManager.collapseStagesSequences(academicStage.getMajor())) {
						acadStage.update();
					}
				}
				ServletManager.setDeletedSucces("#" +academicStage.getSequence(), academicStage.getClass(), request, response);
				return;
			} else {
				ServletManager.setDeletedFailure("#" +academicStage.getSequence(), academicStage.getClass(), request, response);
				return;
			}
			
		} else if (URN.equals("/acadStage/activate")) {
			
			AcademicStage academicStage = ServletManager.assertEntity(AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			if (!academicStage.getMajor().getIsActive()) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			academicStage.setIsActive(!academicStage.getIsActive());
			academicStage.update();
			ServletManager.setUpdatedSucces("#" +academicStage.getSequence(), academicStage.getClass(), request, response);
			return;
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		
		if (URN.equals("/acadStage/create")) {
			
			MajorEnum major = ServletManager.assertEntity("major_id", MajorEnum.class, request, response);
			if (major == null) {
				return;
			}
			AcademicStage academicStage = AcadStepsManager.validateStage(major, request.getParameter("sequence"), request.getParameter("isActive"));
			if (academicStage == null || academicStage.getSequence() < 1 || academicStage.getSequence() > academicStage.getMajor().getAcademicStages().size() +1) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!major.getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (request.getParameter("isDiploma") != null) {
				academicStage.setIsDiploma(true);
			}
			AcademicStage similar_stage = AcadStepsManager.getStageWithSequence(academicStage.getSequence(), major);
			if (similar_stage != null) {
				similar_stage.setSequence(academicStage.getMajor().getAcademicStages().size() +1);
				similar_stage.update();
			}
			academicStage.insert();
			if (request.getParameter("create") != null) {
				AcademicSemester academicSemester_1 = new AcademicSemester ();
				academicSemester_1.setAcademicStage(academicStage);
				academicSemester_1.setSequence(1);
				academicSemester_1.setIsActive(academicStage.getIsActive());
				academicSemester_1.insert();
				AcademicSemester academicSemester_2 = new AcademicSemester ();
				academicSemester_2.setAcademicStage(academicStage);
				academicSemester_2.setSequence(2);
				academicSemester_2.setIsActive(academicStage.getIsActive());
				academicSemester_2.insert();
			}
			ServletManager.setCreatedSucces("#" +academicStage.getSequence(), academicStage.getClass(), request, response);
			return;
			
		} else if (URN.equals("/acadStage/edit")) {
			
			AcademicStage academicStage = ServletManager.assertEntity(AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			AcademicStage validated_academicStage = AcadStepsManager.validateStage(academicStage.getMajor(), request.getParameter("sequence"), request.getParameter("isActive"));
			if (validated_academicStage == null || validated_academicStage.getSequence() < 1 || validated_academicStage.getSequence() > academicStage.getMajor().getAcademicStages().size()) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (!academicStage.getMajor().getIsActive() && StringManager.parseBool(request.getParameter("isActive"))) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			academicStage.setIsActive(validated_academicStage.getIsActive());
			if (request.getParameter("isDiploma") != null) {
				academicStage.setIsDiploma(true);
			} else {
				academicStage.setIsDiploma(false);
			}
			if (validated_academicStage.getSequence() != academicStage.getSequence()) {
				AcademicStage swaped_academicStage = AcadStepsManager.getStageWithSequence(validated_academicStage.getSequence(), academicStage.getMajor());
				swaped_academicStage.setSequence(academicStage.getMajor().getAcademicStages().size() +1);
				swaped_academicStage.update();
				swaped_academicStage.setSequence(academicStage.getSequence());
				academicStage.setSequence(validated_academicStage.getSequence());
				academicStage.update();
				swaped_academicStage.update();
			} else {
				academicStage.update();
			}
			ServletManager.setUpdatedSucces("#" +academicStage.getSequence(), academicStage.getClass(), request, response);
			return;
		}
		
	}

}
