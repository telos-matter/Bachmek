package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.GradeManager.GradeState;
import hemmouda.bachmek.business.GradeManager.SemesterGradeWrapper;
import hemmouda.bachmek.business.GradeManager.StageGradeWrapper;
import hemmouda.bachmek.business.InstructorManager.InstructorRole;
import hemmouda.bachmek.listeners.InitListener;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.ModuleCoefBound;
import hemmouda.bachmek.models.ModuleRep;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Assessment;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.ElementInstructor;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.MajorRep;
import hemmouda.bachmek.models.PedagogicalRegistration;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.models.SemesterModule;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.ExcelManager;

import jakarta.servlet.http.HttpServletResponse;

public class MMManager {
	
	
	public static List <List <PedagogicalRegistration>> getRegisteredPedagRegList (AcademicStage academicStage, AcademicYear academicYear) {
		List <List <PedagogicalRegistration>> list = new ArrayList <> ();
		for (PedagogicalRegistration pedagogicalRegistration : Manager.selectAll(PedagogicalRegistration.class)) {
			if (academicYear.equalsId(pedagogicalRegistration.getAcademicYear()) && pedagogicalRegistration.hasAcademicStage(academicStage)) {
				addStageHelper(list, pedagogicalRegistration);
			}
		}
		return list;
	}
	
	private static void addStageHelper (List <List <PedagogicalRegistration>> list, PedagogicalRegistration pedagogicalRegistration) {
		for (List <PedagogicalRegistration> sub_list : list) {
			for (PedagogicalRegistration pedagReg : sub_list) {
				if (pedagReg.getCne().equals(pedagogicalRegistration.getCne())) {
					sub_list.add(pedagogicalRegistration);
					return;
				}
			}
		}
		List <PedagogicalRegistration> sub_list = new ArrayList <> ();
		sub_list.add(pedagogicalRegistration);
		list.add(sub_list);
	}
	
	private static PedagogicalRegistration getDownloadStageHelper (List <PedagogicalRegistration> list, Semester semester) {
		for (PedagogicalRegistration pedagogicalRegistration : list) {
			if (pedagogicalRegistration.hasSemester(semester)) {
				return pedagogicalRegistration;
			}
		}
		return null;
	}
	
	/**
	 * @param semester
	 * @return All {@link PedagogicalRegistration}s that are registered in this the {@link Semester}
	 */
	public static List <PedagogicalRegistration> getRegisteredPedagRegList (Semester semester) {
		List <PedagogicalRegistration> list = new ArrayList <> ();
		if (semester == null) {
			return list;
		}
		List <PedagogicalRegistration> full_list = Manager.selectAll(PedagogicalRegistration.class);
		for (PedagogicalRegistration pedagogicalRegistration : full_list) {
			if (CollectionManager.boolContains(pedagogicalRegistration.getSemesters(), semester)) {
				list.add(pedagogicalRegistration);
			}
		}
		return list;
	}
	
	public static List <PedagogicalRegistration> getRegisteredPedagRegList (Module module, AcademicYear academicYear) {
		List <PedagogicalRegistration> full_list = Manager.selectAll(PedagogicalRegistration.class);
		List <PedagogicalRegistration> list = new ArrayList <> ();
		for (PedagogicalRegistration pedagogicalRegistration : full_list) {
			if (academicYear.equalsId(pedagogicalRegistration.getAcademicYear()) && pedagogicalRegistration.hasModule(module)) {
				list.add(pedagogicalRegistration);
			}
		}
		return list;
	}
	
	public static List <PedagogicalRegistration> getRegisteredPedagRegList (Element element, AcademicYear academicYear) {
		List <PedagogicalRegistration> full_list = Manager.selectAll(PedagogicalRegistration.class);
		List <PedagogicalRegistration> list = new ArrayList <> ();
		for (PedagogicalRegistration pedagogicalRegistration : full_list) {
			if (academicYear.equalsId(pedagogicalRegistration.getAcademicYear()) && pedagogicalRegistration.hasElement(element)) {
				list.add(pedagogicalRegistration);
			}
		}
		return list;
	}
	
	public static void writeDownloadList (MMType type, AcademicYear academicYear, Object item, Boolean normal, HttpServletResponse response) {
		String name = null;
		String [][] data = null;
		String [] file_header = null;
		
		switch (type) {
		case STAGE: {
			AcademicStage academicStage = (AcademicStage) item;
			List <AcademicSemester> academicSemesters = new ArrayList <> (academicStage.getAcademicSemesters());
			List <List <PedagogicalRegistration>> pedagRegs_list = getRegisteredPedagRegList(academicStage, academicYear);
			String [] semester_fields = {"academicSemester", "academicYear"};
			name = academicStage.getName() +" Stage Grades list";
			file_header = new String [5 +academicSemesters.size()];
			file_header [0] = "CNE";
			file_header [1] = "Last name";
			file_header [2] = "First name";
			for (int i = 0; i < academicSemesters.size(); i++) {
				file_header [i +3] = academicSemesters.get(i).getSimpleName();
			}
			file_header [file_header.length -2] = "Final grade"; 
			file_header [file_header.length -1] = "Result"; 
			data = new String [pedagRegs_list.size()][file_header.length];
			for (int i = 0; i < pedagRegs_list.size(); i++) {
				PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i).get(0);
				data [i][0] = "" +pedagogicalRegistration.getAdministrativeRegistration().getCne();
				data [i][1] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getLast_name();
				data [i][2] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getFirst_name();
			}
			for (int j = 0; j < academicSemesters.size(); j++) {
				Object [] semester_values = {academicSemesters.get(j), academicYear};
				Semester semester = Manager.selectUnique(Semester.class, semester_fields, semester_values);
				for (int i = 0; i < pedagRegs_list.size(); i++) {
					PedagogicalRegistration pedagogicalRegistration = getDownloadStageHelper(pedagRegs_list.get(i), semester);
					if (pedagogicalRegistration != null) {
						data [i][j +3] = "" +GradeManager.computeSemester(pedagogicalRegistration, semester).getGrade();
					}
				}
			}
			for (int i = 0; i < pedagRegs_list.size(); i++) {
				StageGradeWrapper stageGradeWrapper = GradeManager.computeStage(pedagRegs_list.get(i), academicStage);
				data [i][academicSemesters.size() +3] = "" +stageGradeWrapper.getGrade();
				data [i][academicSemesters.size() +4] = "" +stageGradeWrapper.getGradeState().hasPassedState();
			}
			break;
		}
		case SEMESTER: {
			AcademicSemester academicSemester = (AcademicSemester) item;
			String [] semester_fields = {"academicSemester", "academicYear"};
			Object [] semester_values = {academicSemester, academicYear};
			Semester semester = Manager.selectUnique(Semester.class, semester_fields, semester_values);
			List <Module> modules = new ArrayList <> ();
			for (SemesterModule semesterModule : semester.getModules()) {
				modules.add(semesterModule.getModule());
			}
			String [] majorRep_fields = {"major", "academicYear"};
			Object [] majorRep_values = {academicSemester.getAcademicStage().getMajor(), academicYear};
			MajorRep majorRep = Manager.selectUnique(MajorRep.class, majorRep_fields, majorRep_values);
			List <PedagogicalRegistration> pedagRegs_list = getRegisteredPedagRegList(semester);
			String [] moduleCoefBound_fields = {"module", "majorRep"};
			if (normal) {
				name = semester.getDashedName() +" Semester Grades list";
				file_header = new String [5 +modules.size()];
				file_header [0] = "CNE";
				file_header [1] = "Last name";
				file_header [2] = "First name";
				for (int i = 0; i < modules.size(); i++) {
					file_header [i +3] = modules.get(i).getName();
				}
				file_header [file_header.length -2] = "Final grade"; 
				file_header [file_header.length -1] = "Result"; 
				data = new String [pedagRegs_list.size()][file_header.length];
				for (int i = 0; i < pedagRegs_list.size(); i++) {
					PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i);
					data [i][0] = "" +pedagogicalRegistration.getAdministrativeRegistration().getCne();
					data [i][1] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getLast_name();
					data [i][2] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getFirst_name();
				}
				for (int j = 0; j < modules.size(); j++) {
					Module module = modules.get(j);
					Object [] moduleCoefBound_values = {module, majorRep};
					ModuleCoefBound moduleCoefBound = Manager.selectUnique(ModuleCoefBound.class, moduleCoefBound_fields, moduleCoefBound_values);
					for (int i = 0; i < pedagRegs_list.size(); i++) {
						PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i);
						if (pedagogicalRegistration.hasModule(module)) {
							data [i][j +3] = "" +GradeManager.computeModule(pedagogicalRegistration, module, moduleCoefBound).getGrade();
						}
					}
				}
				for (int i = 0; i < pedagRegs_list.size(); i++) {
					PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i);
					SemesterGradeWrapper semesterGradeWrapper = GradeManager.computeSemester(pedagogicalRegistration, semester);
					data [i][modules.size() +3] = "" +semesterGradeWrapper.getGrade();
					data [i][modules.size() +4] = "" +semesterGradeWrapper.getGradeState().hasPassedState();
				}
			} else {
				name = semester.getDashedName() +" Semester Catch up session Students list";
				file_header = new String [5];
				file_header [0] = "CNE";
				file_header [1] = "Last name";
				file_header [2] = "First name";
				file_header [3] = "Final grade";
				file_header [4] = "Result";
				List <String []> data_list = new ArrayList <> ();
				for (int i = 0; i < pedagRegs_list.size(); i++) {
					PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i);
					SemesterGradeWrapper semesterGradeWrapper = GradeManager.computeSemester(pedagogicalRegistration, semester);
					if (semesterGradeWrapper.getGradeState() == GradeState.FAILED) {
						String [] data_line = new String [5];
						data_line [0] = "" +pedagogicalRegistration.getAdministrativeRegistration().getCne();
						data_line [1] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getLast_name();
						data_line [2] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getFirst_name();
						data_line [3] = "" +semesterGradeWrapper.getGrade();
						data_line [4] = "" +semesterGradeWrapper.getGradeState().hasPassedState();
						data_list.add(data_line);
					}
				}
				data = new String [data_list.size()][file_header.length];
				for (int i = 0; i < data_list.size(); i++) {
					data [i] = data_list.get(i);
				}
			}
			break;
		}
		case MODULE: {
			Module module = (Module) item;
			List <Element> elements = new ArrayList <> (module.getElements());
			List <PedagogicalRegistration> pedagRegs_list = getRegisteredPedagRegList(module, academicYear);
			name = module.getName() +" Module Grades list";
			file_header = new String [4 +elements.size()];
			file_header [0] = "CNE";
			file_header [1] = "Last name";
			file_header [2] = "First name";
			for (int i = 0; i < elements.size(); i++) {
				file_header [i +3] = elements.get(i).getName();
			}
			file_header [file_header.length -1] = "Final grade"; 
			data = new String [pedagRegs_list.size()][file_header.length];
			for (int i = 0; i < pedagRegs_list.size(); i++) {
				PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i);
				data [i][0] = "" +pedagogicalRegistration.getAdministrativeRegistration().getCne();
				data [i][1] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getLast_name();
				data [i][2] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getFirst_name();
				for (int j = 0; j < elements.size(); j++) {
					Element element = elements.get(j);
					if (pedagogicalRegistration.hasElement(element)) {
						data [i][j +3] = "" +GradeManager.computeElement(pedagogicalRegistration, element).getGrade();
					}
				}
				data [i][data [i].length -1] = "" +GradeManager.computeModule(pedagogicalRegistration, module, null).getGrade();
			}
			break;
		}
		case ELEMENT: {
			Element element = (Element) item;
			List <Assessment> assessments = new ArrayList <> (element.getAssessments());
			List <PedagogicalRegistration> pedagRegs_list = getRegisteredPedagRegList(element, academicYear);
			name = element.getDashedFullName() +" Element Grades list";
			file_header = new String [4 +assessments.size()];
			file_header [0] = "CNE";
			file_header [1] = "Last name";
			file_header [2] = "First name";
			for (int i = 0; i < assessments.size(); i++) {
				file_header [i +3] = assessments.get(i).getName();
			}
			file_header [file_header.length -1] = "Final grade"; 
			data = new String [pedagRegs_list.size()][file_header.length];
			for (int i = 0; i < pedagRegs_list.size(); i++) {
				PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i);
				data [i][0] = "" +pedagogicalRegistration.getAdministrativeRegistration().getCne();
				data [i][1] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getLast_name();
				data [i][2] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getFirst_name();
				for (int j = 0; j < assessments.size(); j++) {
					Assessment assessment = assessments.get(j);
					data [i][j +3] = "" +GradeManager.getAssessmentGrade(pedagogicalRegistration, assessment).getFinalGrade();
				}
				data [i][data [i].length -1] = "" +GradeManager.computeElement(pedagogicalRegistration, element).getGrade();
			}
			break;
		}
		}
		
		if (type == MMType.MODULE || type == MMType.ELEMENT) {
			ExcelManager.write(InitListener.getEstmName() +"s' MM list - " +academicYear.getABBR(), name, file_header, data, response);
		} else {
			ExcelManager.writeGrades(InitListener.getEstmName() +"s' MM list - " +academicYear.getABBR(), name, file_header, data, response);
		}
	}
	
	public static List <?> getList (AcademicYear academicYear, MMType type, Instructor instructor) {
		List <Object> list = new ArrayList <> ();
		if (type == null) {
			return list;
		}
		
		switch (type) {
		case STAGE: {
			String [] majorRep_fields = {"instructor", "academicYear"};
			Object [] majorRep_values = {instructor, academicYear};
			String [] fields = {"academicSemester", "academicYear"};
			for (MajorRep majorRep : Manager.selectMultiple(MajorRep.class, majorRep_fields, majorRep_values)) {
				boolean valid = true;
				for (AcademicStage academicStage : majorRep.getMajor().getAcademicStages()) {
					valid = true;
					for (AcademicSemester academicSemester : academicStage.getAcademicSemesters()) {
						Object [] values = {academicSemester, academicYear};
						Semester semester = Manager.selectUnique(Semester.class, fields, values);
						if (semester == null) {
							valid = false;
							break;
						}
						if (!semester.isApproved()) {
							valid = false;
							break;
						}
					}
					if (valid) {
						list.add(academicStage);
					}
				}
			}
			break;
		}
		case SEMESTER: {
			String [] majorRep_fields = {"instructor", "academicYear"};
			Object [] majorRep_values = {instructor, academicYear};
			String [] semester_fields = {"academicSemester", "academicYear"};
			for (MajorRep majorRep : Manager.selectMultiple(MajorRep.class, majorRep_fields, majorRep_values)) {
				for (AcademicSemester academicSemester : majorRep.getMajor().getAcademicSemesters()) {
					Object [] semester_values = {academicSemester, academicYear};
					for (Semester semester : Manager.selectMultiple(Semester.class, semester_fields, semester_values)) {
						if (semester.isApproved()) {
							list.add(semester);
						}
					}
				}
			}
			break;
		}
		case MODULE: {
			String [] moduleRep_fields = {"instructor", "academicYear"};
			Object [] moduleRep_values = {instructor, academicYear};
			boolean valid;
			boolean exists;
			for (ModuleRep moduleRep : Manager.selectMultiple(ModuleRep.class, moduleRep_fields, moduleRep_values)) {
					valid = true;
					exists = false;
					for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
						if (semester.hasModule(moduleRep.getModule())) {
							exists = true;
							if (!semester.isModuleApproved(moduleRep.getModule())) {
								valid = false;
								break;
							}
						}
					}
					if (exists && valid) {
						list.add(moduleRep.getModule());
					}
			}
			break;
		}
		case ELEMENT: {
			String [] elementInstructor_fields = {"instructor", "academicYear"};
			Object [] elementInstructor_values = {instructor, academicYear};
			boolean valid;
			boolean exists;
			for (ElementInstructor elementInstructor : Manager.selectMultiple(ElementInstructor.class, elementInstructor_fields, elementInstructor_values)) {
					valid = true;
					exists = false;
					for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
						if (semester.hasModule(elementInstructor.getElement().getModule())) {
							exists = true;
							if (!semester.isModuleApproved(elementInstructor.getElement().getModule())) {
								valid = false;
								break;
							}
						}
					}
					if (exists && valid) {
						list.add(elementInstructor.getElement());
					}
			}
			break;
		}
		}
		
		return list;
	}
	
	public static List <?> getList (AcademicYear academicYear, MMType type) {
		List <Object> list = new ArrayList <> ();
		if (type == null) {
			return list;
		}
		
		switch (type) {
		case STAGE: {
			String [] fields = {"academicSemester", "academicYear"};
			boolean valid = true;
			boolean exists = false;
			for (AcademicStage academicStage : Manager.selectAll(AcademicStage.class)) {
				valid = true;
				exists = false;
				for (AcademicSemester academicSemester : academicStage.getAcademicSemesters()) {
					exists = true;
					Object [] values = {academicSemester, academicYear};
					Semester semester = Manager.selectUnique(Semester.class, fields, values);
					if (semester == null) {
						valid = false;
						break;
					}
					if (!semester.isApproved()) {
						valid = false;
						break;
					}
				}
				if (exists && valid) {
					list.add(academicStage);
				}
			}
			break;
		}
		case SEMESTER: {
			for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
				if (semester.isApproved()) {
					list.add(semester);
				}
			}
			break;
		}
		case MODULE: {
			boolean valid;
			boolean exists;
			for (Module module : Manager.selectAll(Module.class)) {
				valid = true;
				exists = false;
				for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
					if (semester.hasModule(module)) {
						exists = true;
						if (!semester.isModuleApproved(module)) {
							valid = false;
							break;
						}
					}
				}
				if (exists && valid) {
					list.add(module);
				}
			}
			break;
		}
		case ELEMENT: {
			boolean valid;
			boolean exists;
			for (Module module : Manager.selectAll(Module.class)) {
				valid = true;
				exists = false;
				for (Semester semester : Manager.selectMultiple(Semester.class, "academicYear", academicYear)) {
					if (semester.hasModule(module)) {
						exists = true;
						if (!semester.isModuleApproved(module)) {
							valid = false;
							break;
						}
					}
				}
				if (exists && valid) {
					list.addAll(module.getElements());
				}
			}
			break;
		}
		}
		
		return list;
	}
	
	public static enum MMType {
		STAGE,
		SEMESTER,
		MODULE,
		ELEMENT;
		
		public String getName () {
			switch (this) {
			case STAGE: return "Academic stage";
			case SEMESTER: return "Academic Semester";
			case MODULE: return "Module";
			case ELEMENT: return "Element";
			}
			return null;
		}
		
		public static List <MMType> instructorValues (Instructor instructor, AcademicYear academicYear) {
			Set <MMType> set = new HashSet <> ();
			for (InstructorRole role : InstructorRole.getInstructorRoles(instructor, academicYear)) {
				switch (role) {
				case MAJOR_REP: {
					set.add(STAGE);
					set.add(SEMESTER);
					break;
				}
				case MODULE_REP: {
					set.add(MODULE);
					break;
				}
				case ELEMENT_INSTRUCTOR: {
					set.add(ELEMENT);
					break;
				}
				}
			}
			return new ArrayList <MMType> (set);
		}
		
		public static List <MMType> valuesAsList () {
			List <MMType> list = new ArrayList <> ();
			for (MMType value : MMType.values()) {
				list.add(value);
			}
			return list;
		}
		
		public static MMType getMMType (String name) {
			try {
				return (name == null) ? null : MMType.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException exception) {
				return null;
			}
		}
		
		public Class <?> getCorrespondingClass () {
			switch (this) {
			case STAGE: return AcademicStage.class;
			case SEMESTER: return AcademicSemester.class;
			case MODULE: return Module.class;
			case ELEMENT: return Element.class;
			}
			return null;
		}
	}
	
}
