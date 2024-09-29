package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.AcademicStageBound;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Assessment;
import hemmouda.bachmek.models.AssessmentCoef;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.ElementCoef;
import hemmouda.bachmek.models.ElementInstructor;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.MajorEnum;
import hemmouda.bachmek.models.MajorRep;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.ModuleCoefBound;
import hemmouda.bachmek.models.ModuleRep;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.models.SemesterBound;
import hemmouda.bachmek.models.SemesterModule;
import hemmouda.bachmek.util.CollectionManager;

// ma boi if this is not some of nastiest code i've written, then idk what is
public class CoefManager {
	
	public static void create (CoefEnum coefEnum, AcademicYear academicYear, Instructor instructor, Object associated_item, Integer coefficient, Float passing_bound, Float failling_bound) {
		switch (coefEnum) {
		case ASSMNT_COEF: {
			Assessment assessment = ((Assessment) associated_item);
			Element element = assessment.getElement();
			String [] fields = {"element", "academicYear"};
			Object [] values = {element, academicYear};
			AssessmentCoef assessmentCoef = new AssessmentCoef ();
			assessmentCoef.setElementInstructor(Manager.selectUnique(ElementInstructor.class, fields, values));
			assessmentCoef.setAssessment(assessment);
			assessmentCoef.setCoefficient(coefficient);
			assessmentCoef.insert();
			//elementInstructor.getAssessmentCoefs().add(assessmentCoef);
			break;
		}
		case ELEMENT_COEF: {
			Element element = ((Element) associated_item);
			Module module = element.getModule();
			String [] fields = {"module", "academicYear"};
			Object [] values = {module, academicYear};
			ElementCoef elementCoef = new ElementCoef ();
			elementCoef.setModuleRep(Manager.selectUnique(ModuleRep.class, fields, values));
			elementCoef.setElement(element);
			elementCoef.setCoefficient(coefficient);
			elementCoef.insert();
			break;
		}
		case ACAD_STAGE_BOUND: {
			AcademicStage academicStage = ((AcademicStage) associated_item);
			MajorEnum major = academicStage.getMajor();
			String [] fields = {"major", "academicYear"};
			Object [] values = {major, academicYear};
			AcademicStageBound academicStageBound = new AcademicStageBound ();
			academicStageBound.setMajorRep(Manager.selectUnique(MajorRep.class, fields, values));
			academicStageBound.setAcademicStage(academicStage);
			academicStageBound.setPassing_bound(passing_bound);
			academicStageBound.insert();
			break;
		}
		case SEMESTER_BOUND: {
			AcademicSemester academicSemester = ((AcademicSemester) associated_item);
			MajorEnum major = academicSemester.getAcademicStage().getMajor();
			String [] fields = {"major", "academicYear"};
			Object [] values = {major, academicYear};
			SemesterBound semesterBound = new SemesterBound ();
			semesterBound.setMajorRep(Manager.selectUnique(MajorRep.class, fields, values));
			semesterBound.setAcademicSemester(academicSemester);
			semesterBound.setPassing_bound(passing_bound);
			semesterBound.insert();
			break;
		}
		case MODULE_COEF_BOUND: {
			ModuleCoefBound moduleCoefBound = new ModuleCoefBound();
			moduleCoefBound.setCoefficient(coefficient);
			moduleCoefBound.setPassing_bound(passing_bound);
			moduleCoefBound.setFailling_bound(failling_bound);
			moduleCoefBound.setModule(((Module) associated_item));
			moduleCoefBound.setMajorRep((MajorRep) getInstructor(coefEnum, academicYear, instructor));
			moduleCoefBound.insert();
			break;
		}
		}
	}
	
	// The associated item that can be created atm by this instructor
	public static List <?> getPossibleCreate (CoefEnum coefEnum, AcademicYear academicYear, Instructor instructor) {
		List <?> instructor_list = getInstructorList(coefEnum, academicYear, instructor);
		List <Object> items = new ArrayList <> ();
		switch (coefEnum) {
		case ASSMNT_COEF: {
			for (Object element_instructor : instructor_list) {
				for (Assessment assessment : ((ElementInstructor) element_instructor).getElement().getAssessments()) {
					items.add(assessment);
				}
			}
			for (Object element_instructor : instructor_list) {
				for (AssessmentCoef existing_coef : ((ElementInstructor) element_instructor).getAssessmentCoefs()) {
					items.remove(existing_coef.getAssessment());
				}
			}
			return items;
		}
		case ELEMENT_COEF: {
			for (Object module_rep : instructor_list) {
				for (Element element : ((ModuleRep) module_rep).getModule().getElements()) {
					items.add(element);
				}
			}
			for (Object module_rep : instructor_list) {
				for (ElementCoef existing_coef : ((ModuleRep) module_rep).getElementCoefs()) {
					items.remove(existing_coef.getElement());
				}
			}
			return items;
		}
		case MODULE_COEF_BOUND: {
			//all semesters dyal lmajor dyal had major_rep fhad had l3am (though actually la kano ktr mn 1 it ll cause problems in creating)
			//take all modules li kit9raw fihom - the ones already existing
			List <Module> list = new ArrayList <> ();
			String [] fields = {"academicSemester", "academicYear"};
			for (Object major_rep : instructor_list) {
				for (AcademicSemester academicSemester : ((MajorRep) major_rep).getMajor().getAcademicSemesters()) {
					Object [] values = {academicSemester, academicYear};
					Semester semester = Manager.selectUnique(Semester.class, fields, values);
					if (semester != null) {
						for (SemesterModule semesterModule : semester.getModules()) {
							CollectionManager.addAsSet(list, semesterModule.getModule());
						}
					}
				}
			}
			for (Object major_rep : instructor_list) {
				for (ModuleCoefBound existing_bound : ((MajorRep) major_rep).getModuleCoefBounds()) {
					CollectionManager.remove(list, existing_bound.getModule());
				}
			}
			return list;
		}
		case ACAD_STAGE_BOUND: {
			for (Object major_rep : instructor_list) {
				for (AcademicStage academicStage : ((MajorRep) major_rep).getMajor().getAcademicStages()) {
					items.add(academicStage);
				}
			}
			for (Object major_rep : instructor_list) {
				for (AcademicStageBound existing_bound : ((MajorRep) major_rep).getAcademicStageBounds()) {
					items.remove(existing_bound.getAcademicStage());
				}
			}
			return items;
		}
		case SEMESTER_BOUND: {
			for (Object major_rep : instructor_list) {
				for (AcademicSemester academicSemester : ((MajorRep) major_rep).getMajor().getAcademicSemesters()) {
					items.add(academicSemester);
				}
			}
			for (Object major_rep : instructor_list) {
				for (SemesterBound existing_bound : ((MajorRep) major_rep).getSemesterBounds()) {
					items.remove(existing_bound.getAcademicSemester());
				}
			}
			return items;
		}
		}
		
		
		return null;
	}
	
	public static List <?> getInstructorList (CoefEnum coefEnum, AcademicYear academicYear, Instructor instructor) {
		String [] fields = {"academicYear", "instructor"};
		Object [] values = {academicYear, instructor};
		return Manager.selectMultiple(coefEnum.getInstructorClass(), fields, values);
	}
	
	public static Object getInstructor (CoefEnum coefEnum, AcademicYear academicYear, Instructor instructor) {
		String [] fields = {"academicYear", "instructor"};
		Object [] values = {academicYear, instructor};
		List <?> instructor_list = Manager.selectMultiple(coefEnum.getInstructorClass(), fields, values);
		Object typed_instructor = null;
		if (instructor_list.size() != 0) {
			typed_instructor = instructor_list.get(0);
		}
		return typed_instructor;
	}
	
	public static List <?> getCoefList (CoefEnum coefEnum, AcademicYear academicYear, Instructor instructor) {
		List <Object> list = new ArrayList <> ();
		for (Object typed_instructor : getInstructorList(coefEnum, academicYear, instructor)) {
			for (Object item : Manager.selectMultiple(coefEnum.getCorrespondingClass(), coefEnum.getInstructorFieldName(), typed_instructor)) {
				list.add(item);
			}
		}
		return list;
	}

	public static enum CoefEnum {
		
		ASSMNT_COEF,
		ELEMENT_COEF,
		MODULE_COEF_BOUND,
		ACAD_STAGE_BOUND,
		SEMESTER_BOUND;
		
		public Class <?> getAssociatedClass () {
			switch (this) {
			case ASSMNT_COEF: return Assessment.class;
			case ELEMENT_COEF: return Element.class;
			case MODULE_COEF_BOUND: return Module.class; 
			case ACAD_STAGE_BOUND: return AcademicStage.class;
			case SEMESTER_BOUND: return AcademicSemester.class;
			}
			return null;
		}
		
		public String getInstructorFieldName () {
			switch (this) {
			case ASSMNT_COEF: return "elementInstructor";
			case ELEMENT_COEF: return "moduleRep";
			case MODULE_COEF_BOUND: 
				case ACAD_STAGE_BOUND: 
				case SEMESTER_BOUND: return "majorRep";
			}
			return null;
		}
		
		public Class <?> getInstructorClass () {
			switch (this) {
			case ASSMNT_COEF: return ElementInstructor.class;
			case ELEMENT_COEF: return ModuleRep.class;
			case MODULE_COEF_BOUND: 
				case ACAD_STAGE_BOUND: 
				case SEMESTER_BOUND: return MajorRep.class;
			}
			return null;
		}
		
		public Class <?> getCorrespondingClass() {
			switch (this) {
			case ASSMNT_COEF: return AssessmentCoef.class;
			case ELEMENT_COEF: return ElementCoef.class;
			case MODULE_COEF_BOUND: return ModuleCoefBound.class;
			case ACAD_STAGE_BOUND: return AcademicStageBound.class;
			case SEMESTER_BOUND: return SemesterBound.class;
			}
			return null;
		}
		
		public static CoefEnum getCoefEnum (String name) {
			try {
				return (name == null) ? null : CoefEnum.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException exception) {
				return null;
			}
		}
		
		public String getName () {
			switch (this) {
			case ASSMNT_COEF: return "Assessment coefficient";
			case ELEMENT_COEF: return "Element coefficient";
			case MODULE_COEF_BOUND: return "Module coefficient-bound";
			case ACAD_STAGE_BOUND: return "Academic Stage bound";
			case SEMESTER_BOUND: return "Academic Semester bound";
			}
			return null;
		}
		
	}
	
}
