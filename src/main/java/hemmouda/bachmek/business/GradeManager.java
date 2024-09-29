package hemmouda.bachmek.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.AcademicStageBound;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Assessment;
import hemmouda.bachmek.models.AssessmentCoef;
import hemmouda.bachmek.models.AssessmentGrade;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.ElementCoef;
import hemmouda.bachmek.models.ElementInstructor;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.MajorRep;
import hemmouda.bachmek.models.ModuleCoefBound;
import hemmouda.bachmek.models.ModuleRep;
import hemmouda.bachmek.models.PedagogicalRegistration;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.SemesterBound;
import hemmouda.bachmek.models.SemesterModule;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.ExcelManager;
import hemmouda.bachmek.util.StringManager;

import jakarta.servlet.http.HttpServletResponse;


public class GradeManager {
	
	public static String [] FILE_HEADER = {"CNE", "Full name", "Major", "Normal session grade", "Catch up session grade"};
	
	//The main one that should be used for approving
	public static void approve (AcademicYear academicYear, Module module, boolean approve) {
		if (approve) {
			for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
				SemesterModule semesterModule = semester.getSemesterModule(module);
				if (semesterModule != null) {
					semesterModule.setIsApproved(true);
					semester.update();
					if (semester.isApproved()) {
						for (PedagogicalRegistration pedagogicalRegistration : MMManager.getRegisteredPedagRegList(semester)) {
							if (pedagogicalRegistration.getSemesters().size() == 1 && computeSemester(pedagogicalRegistration, semester).gradeState == GradeState.PASSED) {
								PedagRegManager.createNextPedagReg(pedagogicalRegistration, semester);
							}
						}
					}
				}
			}
		} else {
			for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
				SemesterModule semesterModule = semester.getSemesterModule(module);
				if (semesterModule != null) {
					semesterModule.setIsApproved(false);
					semester.update();
				}
			}
		}
	}
	
	public static StageGradeWrapper computeStage (List <PedagogicalRegistration> pedagogicalRegistrations, AcademicStage academicStage) {
		int coef_sum = 0;
		double grade_sum = 0;
		AcademicYear academicYear = pedagogicalRegistrations.get(0).getAcademicYear();
		String [] majorRep_fields = {"major", "academicYear"};
		Object [] majorRep_values = {pedagogicalRegistrations.get(0).getMajor(), academicYear};
		MajorRep majorRep = Manager.selectUnique(MajorRep.class, majorRep_fields, majorRep_values);
		String [] semester_fields = {"academicSemester", "academicYear"};
		for (AcademicSemester academicSemester : academicStage.getAcademicSemesters()) {
			Object [] semester_values = {academicSemester, academicYear};
			Semester semester = Manager.selectUnique(Semester.class, semester_fields, semester_values);
			for (PedagogicalRegistration pedagogicalRegistration : pedagogicalRegistrations) {
				if (pedagogicalRegistration.hasSemester(semester)) {
					coef_sum ++;
					grade_sum += computeSemester(pedagogicalRegistration, semester).grade;
				}
			}
		}
		double grade = (coef_sum == 0) ? 0 : grade_sum/coef_sum;
		String [] academicStageBound_fields = {"majorRep", "academicStage"};
		Object [] academicStageBound_values = {majorRep, academicStage};
		if (grade < Manager.selectUnique(AcademicStageBound.class, academicStageBound_fields, academicStageBound_values).getPassing_bound()) {
			return new StageGradeWrapper (pedagogicalRegistrations, academicStage, grade, GradeState.FAILED);
		} else {
			return new StageGradeWrapper (pedagogicalRegistrations, academicStage, grade, GradeState.PASSED);
		}
	}
	
	public static SemesterGradeWrapper computeSemester (PedagogicalRegistration pedagogicalRegistration, Semester semester) {
		int coef_sum = 0;
		double grade_sum = 0;
		boolean failed = false;
		boolean compensated = false;
		String [] majorRep_fields = {"major", "academicYear"};
		Object [] majorRep_values = {pedagogicalRegistration.getMajor(), pedagogicalRegistration.getAcademicYear()};
		MajorRep majorRep = Manager.selectUnique(MajorRep.class, majorRep_fields, majorRep_values);
		String [] moduleCoefBound_fields = {"majorRep", "module"};
		for (Module module : pedagogicalRegistration.getModules()) {
			if (semester.hasModule(module)) {
				Object [] moduleCoefBound_values = {majorRep, module};
				ModuleCoefBound moduleCoefBound = Manager.selectUnique(ModuleCoefBound.class, moduleCoefBound_fields, moduleCoefBound_values);
				if (moduleCoefBound.getCoefficient() != 0) {
					ModuleGradeWrapper moduleGradeWrapper = computeModule (pedagogicalRegistration, module, moduleCoefBound);
					if (moduleGradeWrapper.gradeState == GradeState.FAILED) {
						failed = true;
					} else if (moduleGradeWrapper.gradeState == GradeState.PASSED_COMPENSATED) {
						compensated = true;
					}
					coef_sum += moduleCoefBound.getCoefficient();
					grade_sum += moduleGradeWrapper.grade * moduleCoefBound.getCoefficient();
				}
			}
		}
		double grade = (coef_sum == 0) ? 0 : grade_sum/coef_sum;
		if (!failed) {
			String [] semesterBound_fields = {"majorRep", "academicSemester"};
			Object [] semesterBound_values = {majorRep, semester.getAcademicSemester()};
			SemesterBound semesterBound = Manager.selectUnique(SemesterBound.class, semesterBound_fields, semesterBound_values);
			if (grade < semesterBound.getPassing_bound()) {
				failed = true;
			}
		}
		
		return new SemesterGradeWrapper (pedagogicalRegistration, semester, grade, GradeState.computeGradeState(failed, compensated));
	}
	
	public static ModuleGradeWrapper computeModule (PedagogicalRegistration pedagogicalRegistration, Module module, ModuleCoefBound moduleCoefBound) {
		int coef_sum = 0;
		double grade_sum = 0;
		String [] moduleRep_fields = {"module", "academicYear"};
		Object [] moduleRep_values = {module, pedagogicalRegistration.getAcademicYear()};
		ModuleRep moduleRep = Manager.selectUnique(ModuleRep.class, moduleRep_fields, moduleRep_values);
		String [] element_fields = {"moduleRep", "element"};
		for (Element element : module.getElements()) {
			if (pedagogicalRegistration.hasElement(element)) {
				Object [] element_values = {moduleRep, element};
				ElementCoef elementCoef = Manager.selectUnique(ElementCoef.class, element_fields, element_values);
				if (elementCoef.getCoefficient() != 0) {
					coef_sum += elementCoef.getCoefficient();
					grade_sum += computeElement(pedagogicalRegistration, element).getGrade() * elementCoef.getCoefficient();
				}
			}
		}
		double grade = (coef_sum == 0) ? 0 : grade_sum/coef_sum;
		GradeState gradeState = null;
		if (moduleCoefBound != null) {
			if (grade < moduleCoefBound.getFailling_bound()) {
				gradeState = GradeState.FAILED;
			} else if (grade < moduleCoefBound.getPassing_bound()) {
				gradeState = GradeState.PASSED_COMPENSATED;
			} else {
				gradeState = GradeState.PASSED;
			}
		}
		return new ModuleGradeWrapper(pedagogicalRegistration, module, grade, gradeState);
	}
	
	public static ElementGradeWrapper computeElement (PedagogicalRegistration pedagogicalRegistration, Element element) {
		int coef_sum = 0;
		double grade_sum = 0;
		String [] element_fields = {"element", "academicYear"};
		Object [] element_values = {element, pedagogicalRegistration.getAcademicYear()};
		ElementInstructor elementInstructor = Manager.selectUnique(ElementInstructor.class, element_fields, element_values);
		String [] assessment_fields = {"elementInstructor", "assessment"};
		for (Assessment assessment : element.getAssessments()) {
			Object [] assessment_values = {elementInstructor, assessment};
			AssessmentCoef assessmentCoef = Manager.selectUnique(AssessmentCoef.class, assessment_fields, assessment_values);
			if (assessmentCoef.getCoefficient() != 0) {
				coef_sum += assessmentCoef.getCoefficient();
				grade_sum += getAssessmentGrade(pedagogicalRegistration, assessment).getFinalGrade() * assessmentCoef.getCoefficient();
			}
		}
		return new ElementGradeWrapper(element, (coef_sum == 0) ? 0 : grade_sum/coef_sum, pedagogicalRegistration);
	}
	
	public static AssessmentGrade getAssessmentGrade (PedagogicalRegistration pedagogicalRegistration, Assessment assessment) {
		String [] fields = {"pedagogicalRegistration", "assessment"};
		Object [] values = {pedagogicalRegistration, assessment};
		return Manager.selectUnique(AssessmentGrade.class, fields, values);
	}
	
	public static List <SemesterModuleWrapper> getSemesterModuleList (Semester semester) {
		List <SemesterModuleWrapper> list = new ArrayList <> ();
		if (semester == null) {
			return list;
		}
		for (SemesterModule semesterModule : semester.getModules()) {
			if (semesterModule.getIsApproved()) {
				list.add(new SemesterModuleWrapper(semesterModule, null));
			} else {
				list.add(new SemesterModuleWrapper(semesterModule, canBeApproved(semester, semesterModule.getModule())));
			}
		}
		return list;
	}
	
	private static boolean canBeApproved (Semester semester, Module module) {
		
		{ // Makes sure there is an AssessmentCoef for all of em
			String [] fields_1 = {"element", "academicYear"};
			String [] fields_2 = {"elementInstructor", "assessment"};
			for (Assessment assessment : module.getAssessments()) {
				Object [] values_1 = {assessment.getElement(), semester.getAcademicYear()};
				ElementInstructor elementInstructor = Manager.selectUnique(ElementInstructor.class, fields_1, values_1);
				if (elementInstructor == null) {
					return false;
				}
				Object [] values_2 = {elementInstructor, assessment};
				if (Manager.selectUnique(AssessmentCoef.class, fields_2, values_2) == null) {
					return false;
				}
			}
		}
		
		{ // Makes sure there is an ElementCoef for all of em
			String [] fields_1 = {"module", "academicYear"};
			String [] fields_2 = {"moduleRep", "element"};
			for (Element element : module.getElements()) {
				Object [] values_1 = {element.getModule(), semester.getAcademicYear()};
				ModuleRep moduleRep = Manager.selectUnique(ModuleRep.class, fields_1, values_1);
				if (moduleRep == null) {
					return false;
				}
				Object [] values_2 = {moduleRep, element};
				if (Manager.selectUnique(ElementCoef.class, fields_2, values_2) == null) {
					return false;
				}
			}
		}
		
		{ // Makes sure there is a moduleCoefBound as well as a acadStageBound and semBound 
			String [] fields_1 = {"major", "academicYear"};
			String [] fields_2 = {"module", "majorRep"};
			String [] fields_3 = {"academicStage", "majorRep"};
			String [] fields_4 = {"academicSemester", "majorRep"};
			Object [] values_1 = {semester.getAcademicSemester().getAcademicStage().getMajor(), semester.getAcademicYear()};
			MajorRep majorRep = Manager.selectUnique(MajorRep.class, fields_1, values_1);
			if (majorRep == null) {
				return false;
			}
			Object [] values_2 = {module, majorRep};
			if (Manager.selectUnique(ModuleCoefBound.class, fields_2, values_2) == null) {
				return false;
			}
			Object [] values_3 = {semester.getAcademicSemester().getAcademicStage(), majorRep};
			if (Manager.selectUnique(AcademicStageBound.class, fields_3, values_3) == null) {
				return false;
			}
			Object [] values_4 = {semester.getAcademicSemester(), majorRep};
			if (Manager.selectUnique(SemesterBound.class, fields_4, values_4) == null) {
				return false;
			}
		}
		
		
		{ // Makes sure all the grades are filled
			for (Assessment assessment : module.getAssessments()) {
				String [] fields = {"assessment", "pedagogicalRegistration"};
				for (PedagogicalRegistration pedagogicalRegistration : Manager.selectAll(PedagogicalRegistration.class)) { //QUERY 100
					if (semester.getAcademicYear().equalsId(pedagogicalRegistration.getAcademicYear()) && CollectionManager.boolContains(pedagogicalRegistration.getElements(), assessment.getElement())) {
						Object [] values = {assessment, pedagogicalRegistration};
						if (Manager.selectUnique(AssessmentGrade.class, fields, values) == null) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	public static void approve (AcademicYear academicYear, Assessment assessment) {
		approve(academicYear, assessment.getElement().getModule(), true);
	}
	
	public static void writeDownloadList (AcademicYear academicYear, Assessment assessment, HttpServletResponse response) {
		String [][] data = null;
		List <AssessmentGradeWrapper> list = getGradesList(academicYear, assessment);
		data = new String [list.size()][5];
		for (int i = 0; i < data.length; i++) {
			data [i][0] = list.get(i).getPedagogicalRegistration().getAdministrativeRegistration().getCne();
			data [i][1] = list.get(i).getPedagogicalRegistration().getFullName();
			data [i][2] = list.get(i).getPedagogicalRegistration().getAdministrativeRegistration().getAcademicStage().getMajor().getName();
			if (list.get(i).getHasGrade()) {
				data [i][3] = "" +list.get(i).getGrade().getNormal_session();
				data [i][4] = "" +list.get(i).getGrade().getCatch_up_session();
			} else {
				data [i][3] = null;
				data [i][4] = null;
			}
		}
		ExcelManager.write(assessment.getFullName() +"s' grades' list - " +academicYear.getABBR(), "Grades to be created - edited", FILE_HEADER, data, response);
	}
	
	public static int handleUploadList (List <String []> data, AcademicYear academicYear, Assessment assessment) {
		if (!ExcelManager.assertUnique(data, 0)) { // CNE
			return 0;
		}
		
		List <AssessmentGradeWrapper> list = new ArrayList <> ();
		
		List <AssessmentGradeWrapper> full_list = getGradesList(academicYear, assessment);
		for (String [] row : data) {
			Long cne_l = StringManager.parseLong(row[0]);
			if (cne_l == null) {
				return 0;
			}
			String cne = cne_l.toString();
			AssessmentGradeWrapper found = null;
			for (AssessmentGradeWrapper gradeWrapper : full_list) {
				if (gradeWrapper.getPedagogicalRegistration().getAdministrativeRegistration().getCne().equals(cne)) {
					list.add(gradeWrapper);
					found = gradeWrapper;
					break;
				}
			}
			if (found == null) {
				return 0;
			}
			if (found.getHasGrade()) {
				Float normal_session = StringManager.parseFloat(row[3]);
				if (normal_session == null) {
					return 0;
				}
				found.getGrade().setNormal_session(normal_session);
				Float catch_up_session = StringManager.parseFloat(row[4]);
				found.getGrade().setCatch_up_session(catch_up_session);
			} else {
				Float normal_session = StringManager.parseFloat(row[3]);
				if (normal_session == null) {
					return 0;
				}
				Float catch_up_session = StringManager.parseFloat(row[4]);
				found.setGrade(Optional.of(createNoInsert(academicYear, assessment, found.getPedagogicalRegistration(), normal_session, catch_up_session)));
			}
		}
		
		for (AssessmentGradeWrapper gradeWrapper : list) {
			if (gradeWrapper.getGrade().getId() == 0) {
				gradeWrapper.getGrade().insert();
			} else {
				gradeWrapper.getGrade().update();
			}
		}
		
		return list.size();
	}
	
	private static AssessmentGrade createNoInsert (AcademicYear academicYear, Assessment assessment, PedagogicalRegistration pedagogicalRegistration, Float normal_session, Float catch_up_session) {
		String [] fields = {"academicYear", "element"};
		Object [] values = {academicYear, assessment.getElement()};
		AssessmentGrade assessmentGrade = new AssessmentGrade ();
		assessmentGrade.setAssessment(assessment);
		assessmentGrade.setElementInstructor(Manager.selectUnique(ElementInstructor.class, fields, values));
		assessmentGrade.setPedagogicalRegistration(pedagogicalRegistration);
		assessmentGrade.setNormal_session(normal_session);
		assessmentGrade.setCatch_up_session(catch_up_session);
		return assessmentGrade;
	}
	
	public static AssessmentGrade create (AcademicYear academicYear, Assessment assessment, PedagogicalRegistration pedagogicalRegistration, Float normal_session) {
		String [] fields = {"academicYear", "element"};
		Object [] values = {academicYear, assessment.getElement()};
		AssessmentGrade assessmentGrade = new AssessmentGrade ();
		assessmentGrade.setAssessment(assessment);
		assessmentGrade.setElementInstructor(Manager.selectUnique(ElementInstructor.class, fields, values));
		assessmentGrade.setPedagogicalRegistration(pedagogicalRegistration);
		assessmentGrade.setNormal_session(normal_session);
		assessmentGrade.insert();
		return assessmentGrade;
	}
	
	public static AssessmentGrade edit (AssessmentGrade assessmentGrade, Float normal_session, Float catch_up_session) {
		assessmentGrade.setNormal_session(normal_session);
		assessmentGrade.setCatch_up_session(catch_up_session);
		assessmentGrade.update();
		return assessmentGrade;
	}
	
	public static boolean isApproved (AcademicYear academicYear, Assessment assessment) {
			for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
				if (semester.hasModule(assessment.getElement().getModule())) {
					if (!semester.isModuleApproved(assessment.getElement().getModule())) {
						return false;
					}
				}
			}
			return true;
	}
	
	/**
	 * To approve an assessment (and since i turned approvment 
	 * to be per module, maybe that was a mistake)
	 * First all the grades for all the student that study that assessment should exist
	 * Second the assessmentCoef for that assessment should exist as 
	 * well as its element elementCoef, BUT since it's 
	 * per module, all of the assessments and elements of the module should also check out
	 * 
	 * ALSO since the approving is what causes the grades to be computed, it also has
	 * to check for all the moduleCoefBound where that module is taught, as well as
	 * all the academicStageBound and SemBound ..
	 * 
	 * Ofc all of this for the current acadmeicYEar
	 * 
	 * Also not sure if i should check wether or not some of the list i recieve form the manager are empty
	 * in the first place (meaning they wont pass trought the loop and thus no check)
	 * 
	 * and BRUH AT THIS POINT ITS ACTUALLY HILARIOUS HOW SHITTY THE CODE IS, HHHHHHHHHHHHHHHHHHHHHHHHH
	 */
	public static boolean canBeApproved (AcademicYear academicYear, Assessment assessment) {
		
		{ // Makes sure there is an AssessmentCoef for all of em
			String [] fields_1 = {"element", "academicYear"};
			String [] fields_2 = {"elementInstructor", "assessment"};
			for (Assessment assmnt : assessment.getElement().getModule().getAssessments()) {
				Object [] values_1 = {assmnt.getElement(), academicYear};
				ElementInstructor elementInstructor = Manager.selectUnique(ElementInstructor.class, fields_1, values_1);
				if (elementInstructor == null) {
					return false;
				}
				Object [] values_2 = {elementInstructor, assmnt};
				if (Manager.selectUnique(AssessmentCoef.class, fields_2, values_2) == null) {
					return false;
				}
			}
		}
		
		{ // Makes sure there is an ElementCoef for all of em
			String [] fields_1 = {"module", "academicYear"};
			String [] fields_2 = {"moduleRep", "element"};
			for (Element element : assessment.getElement().getModule().getElements()) {
				Object [] values_1 = {element.getModule(), academicYear};
				ModuleRep moduleRep = Manager.selectUnique(ModuleRep.class, fields_1, values_1);
				if (moduleRep == null) {
					return false;
				}
				Object [] values_2 = {moduleRep, element};
				if (Manager.selectUnique(ElementCoef.class, fields_2, values_2) == null) {
					return false;
				}
			}
		}
		
		{ // Makes sure there is a moduleCoefBound where ever this assessment modules is taught as well as a acadStageBound and semBound 
			String [] fields_1 = {"major", "academicYear"};
			String [] fields_2 = {"module", "majorRep"};
			String [] fields_3 = {"academicStage", "majorRep"};
			String [] fields_4 = {"academicSemester", "majorRep"};
			for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
				if (semester.hasModule(assessment.getElement().getModule())) {
					Object [] values_1 = {semester.getAcademicSemester().getAcademicStage().getMajor(), academicYear};
					MajorRep majorRep = Manager.selectUnique(MajorRep.class, fields_1, values_1);
					if (majorRep == null) {
						return false;
					}
					Object [] values_2 = {assessment.getElement().getModule(), majorRep};
					if (Manager.selectUnique(ModuleCoefBound.class, fields_2, values_2) == null) {
						return false;
					}
					Object [] values_3 = {semester.getAcademicSemester().getAcademicStage(), majorRep};
					if (Manager.selectUnique(AcademicStageBound.class, fields_3, values_3) == null) {
						return false;
					}
					Object [] values_4 = {semester.getAcademicSemester(), majorRep};
					if (Manager.selectUnique(SemesterBound.class, fields_4, values_4) == null) {
						return false;
					}
				}
			}
		}
		
		
		{ // Makes sure all the grades are filled
			for (Assessment assmnt : assessment.getElement().getModule().getAssessments()) {
				String [] fields = {"assessment", "pedagogicalRegistration"};
				for (PedagogicalRegistration pedagogicalRegistration : Manager.selectAll(PedagogicalRegistration.class)) { //QUERY 100
					if (academicYear.equalsId(pedagogicalRegistration.getAcademicYear()) && CollectionManager.boolContains(pedagogicalRegistration.getElements(), assmnt.getElement())) {
						Object [] values = {assmnt, pedagogicalRegistration};
						if (Manager.selectUnique(AssessmentGrade.class, fields, values) == null) {
							return false;
						}
					}
				}
			}
		}
		
		return true; // had true mahatrj3 7ta ytghfr l JDBC w server ga3 donob
	}
	
	public static List <Assessment> getAssessmentsList (AcademicYear academicYear, Instructor instructor) {
		List <Assessment> list = new ArrayList <> ();
		for (ElementInstructor elementInstructor : getElementInstructorsList(academicYear, instructor)) {
			list.addAll(elementInstructor.getElement().getAssessments());
		}
		return list;
	}
	
	public static List <ElementInstructor> getElementInstructorsList (AcademicYear academicYear, Instructor instructor) {
		String [] fields = {"academicYear", "instructor"};
		Object [] values = {academicYear, instructor};
		return Manager.selectMultiple(ElementInstructor.class, fields, values);
	}
	
	/**
	 * @return list of student that study in this year and that have this assessment
	 */
	public static List <AssessmentGradeWrapper> getGradesList (AcademicYear academicYear, Assessment assessment) {
		List <AssessmentGradeWrapper> list = new ArrayList <> ();
		if (assessment == null) {
			return list;
		}
		String [] fields = {"assessment", "pedagogicalRegistration"};
		for (PedagogicalRegistration pedagogicalRegistration : Manager.selectAll(PedagogicalRegistration.class)) { //QUERY 100
			if (academicYear.equalsId(pedagogicalRegistration.getAcademicYear()) && CollectionManager.boolContains(pedagogicalRegistration.getElements(), assessment.getElement())) {
				Object [] values = {assessment, pedagogicalRegistration};
				list.add(new AssessmentGradeWrapper(pedagogicalRegistration, Manager.selectUnique(AssessmentGrade.class, fields, values)));
			}
		}
		return list;
	}

	
	
	public static class SemesterModuleWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		
		SemesterModule semesterModule;
		Boolean canBeApproved;
		
		public SemesterModuleWrapper () {
			this (null, null);
		}
		
		public SemesterModuleWrapper (SemesterModule semesterModule, Boolean canBeApproved) {
			this.semesterModule = semesterModule;
			this.canBeApproved = canBeApproved;
		}

		public SemesterModule getSemesterModule() {
			return semesterModule;
		}

		public void setSemesterModule(SemesterModule semesterModule) {
			this.semesterModule = semesterModule;
		}

		public Boolean getCanBeApproved() {
			return canBeApproved;
		}

		public void setCanBeApproved(Boolean canBeApproved) {
			this.canBeApproved = canBeApproved;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
	public static enum GradeState {
		PASSED,
		PASSED_COMPENSATED,
		FAILED;
		
		public static GradeState computeGradeState (boolean failed, boolean compensated) {
			return (failed) ? FAILED : (compensated) ? PASSED_COMPENSATED : PASSED;
		}
		
		public GradeState hasPassedState () {
			return (this == FAILED) ? FAILED : PASSED;
		}
		
		public boolean hasPassed () {
			return this != FAILED;
		}
		
		public static GradeState getGradeState (String name) {
			try {
				return (name == null) ? null : GradeState.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException exception) {
				return null;
			}
		}
	}
	
	public static class StageGradeWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private List <PedagogicalRegistration> pedagogicalRegistrations;
		
		private AcademicStage academicStage;
		
		private Double grade;
		
		private GradeState gradeState;

		public StageGradeWrapper () {
			this (null, null, null, null);
		}
		
		public StageGradeWrapper(List <PedagogicalRegistration> pedagogicalRegistrations, AcademicStage academicStage, Double grade, GradeState gradeState) {
			super();
			this.pedagogicalRegistrations = pedagogicalRegistrations;
			this.academicStage = academicStage;
			this.grade = grade;
			this.gradeState = gradeState;
		}

		public List<PedagogicalRegistration> getPedagogicalRegistrations() {
			return pedagogicalRegistrations;
		}

		public void setPedagogicalRegistrations(List<PedagogicalRegistration> pedagogicalRegistrations) {
			this.pedagogicalRegistrations = pedagogicalRegistrations;
		}

		public AcademicStage getAcademicStage() {
			return academicStage;
		}

		public void setAcademicStage(AcademicStage academicStage) {
			this.academicStage = academicStage;
		}

		public Double getGrade() {
			return grade;
		}

		public void setGrade(Double grade) {
			this.grade = grade;
		}

		public GradeState getGradeState() {
			return gradeState;
		}

		public void setGradeState(GradeState gradeState) {
			this.gradeState = gradeState;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
	public static class SemesterGradeWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private PedagogicalRegistration pedagogicalRegistration;
		
		private Semester semester;
		
		private Double grade;
		
		private GradeState gradeState;
		
		public SemesterGradeWrapper () {
			this (null, null, null, null);
		}

		public SemesterGradeWrapper(PedagogicalRegistration pedagogicalRegistration, Semester semester, Double grade, GradeState gradeState) {
			this.pedagogicalRegistration = pedagogicalRegistration;
			this.semester = semester;
			this.grade = grade;
			this.gradeState = gradeState;
		}

		public PedagogicalRegistration getPedagogicalRegistration() {
			return pedagogicalRegistration;
		}

		public void setPedagogicalRegistration(PedagogicalRegistration pedagogicalRegistration) {
			this.pedagogicalRegistration = pedagogicalRegistration;
		}

		public Semester getSemester() {
			return semester;
		}

		public void setSemester(Semester semester) {
			this.semester = semester;
		}

		public Double getGrade() {
			return grade;
		}

		public void setGrade(Double grade) {
			this.grade = grade;
		}

		public GradeState getGradeState() {
			return gradeState;
		}

		public void setGradeState(GradeState gradeState) {
			this.gradeState = gradeState;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
	public static class ModuleGradeWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private PedagogicalRegistration pedagogicalRegistration;
		
		private Module module;
		
		private Double grade;
		
		private GradeState gradeState;

		public ModuleGradeWrapper () {
			this (null, null, null, null);
		}
		
		public ModuleGradeWrapper(PedagogicalRegistration pedagogicalRegistration, Module module, Double grade, GradeState gradeState) {
			super();
			this.pedagogicalRegistration = pedagogicalRegistration;
			this.module = module;
			this.grade = grade;
			this.gradeState = gradeState;
		}

		public PedagogicalRegistration getPedagogicalRegistration() {
			return pedagogicalRegistration;
		}

		public void setPedagogicalRegistration(PedagogicalRegistration pedagogicalRegistration) {
			this.pedagogicalRegistration = pedagogicalRegistration;
		}

		public Module getModule() {
			return module;
		}

		public void setModule(Module module) {
			this.module = module;
		}

		public Double getGrade() {
			return grade;
		}

		public void setGrade(Double grade) {
			this.grade = grade;
		}

		public GradeState getGradeState() {
			return gradeState;
		}

		public void setGradeState(GradeState gradeState) {
			this.gradeState = gradeState;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
	public static class ElementGradeWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private PedagogicalRegistration pedagogicalRegistration;
		
		private Element element;
		
		private Double grade;
		
		public ElementGradeWrapper () {
			this (null, null, null);
		}


		public ElementGradeWrapper(Element element, Double grade, PedagogicalRegistration pedagogicalRegistration) {
			super();
			this.element = element;
			this.grade = grade;
			this.pedagogicalRegistration = pedagogicalRegistration;
		}

		public PedagogicalRegistration getPedagogicalRegistration() {
			return pedagogicalRegistration;
		}


		public void setPedagogicalRegistration(PedagogicalRegistration pedagogicalRegistration) {
			this.pedagogicalRegistration = pedagogicalRegistration;
		}


		public Element getElement() {
			return element;
		}


		public void setElement(Element element) {
			this.element = element;
		}


		public Double getGrade() {
			return grade;
		}


		public void setGrade(Double grade) {
			this.grade = grade;
		}


		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
	public static class AssessmentGradeWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private PedagogicalRegistration pedagogicalRegistration;
		private Optional <AssessmentGrade> grade;
		
		public AssessmentGradeWrapper () {
			this (null, null);
		}
		
		public AssessmentGradeWrapper (PedagogicalRegistration pedagogicalRegistration, AssessmentGrade grade) {
			this.pedagogicalRegistration = pedagogicalRegistration;
			if (grade == null) {
				this.grade = Optional.empty();
			} else {
				this.grade = Optional.of(grade);
			}
		}
		
		public boolean getHasGrade () {
			return grade.isPresent();
		}
		
		public AssessmentGrade getGrade () {
			return grade.get();
		}
		
		public PedagogicalRegistration getPedagogicalRegistration () {
			return pedagogicalRegistration;
		}

		public void setPedagogicalRegistration(PedagogicalRegistration pedagogicalRegistration) {
			this.pedagogicalRegistration = pedagogicalRegistration;
		}

		public void setGrade(Optional<AssessmentGrade> grade) {
			this.grade = grade;
		}
		
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
}
