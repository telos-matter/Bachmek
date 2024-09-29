package hemmouda.bachmek.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.models.SemesterModule;
import hemmouda.bachmek.util.CollectionManager;

public class SemesterManager {

	public static final int SEMESTER_MODULE_LIMIT = PedagRegManager.STUDENT_MODULE_LIMIT -0; // A Semester cannot have more modules than what the Student can have.
		
	public static List <Element> getFilteredElements (Semester semester) {
		List <Element> list = new ArrayList <> ();
		for (SemesterModule module : semester.getModules()) {
			if (!module.getIsApproved()) {
				for (Element element : module.getModule().getElements()) {
					if (element.getIsActive()) {
						list.add(element);
					}
				}
			}
		}
		return list;
	}
	
	public static List <Semester> getFilteredAboveOrEqual (AcademicYear academicYear, AcademicStage academicStage) {
		return filterNonApproved(getAboveOrEqual(academicYear, academicStage));
	}
	
	/**
	 * @return Semesters that are registered to the passed academicYear
	 * and to the passed academicStages' academicSemester or above.
	 */
	public static List <Semester> getAboveOrEqual (AcademicYear academicYear, AcademicStage academicStage) {
		List <Semester> all = Manager.selectAll(Semester.class);
		List <Semester> list = new ArrayList <> ();
		for (Semester semester : all) {
			if (semester.getAcademicYear().getId() == academicYear.getId() && semester.getAcademicSemester().getAcademicStage().getMajor().getMajor() == academicStage.getMajor().getMajor() && semester.getAcademicSemester().getAcademicStage().getSequence() >= academicStage.getSequence()) {
				list.add(semester);
			}
		}
		return list;
	}
	
	/**
	 * @return only non approved semesters of the list
	 */
	public static List <Semester> filterNonApproved (List <Semester> list) {
		List <Semester> filtered = new ArrayList <> ();
		for (Semester semester : list) {
			if (!semester.isApproved()) {
				filtered.add(semester);
			}
		}
		return filtered;
	}
	
	public static Semester validate (AcademicYear academicYear, AcademicSemester academicSemester, List <Module> modules) {
		String [] fields = {"academicYear", "academicSemester"};
		Object [] values = {academicYear, academicSemester};
		if (Manager.selectUnique(Semester.class, fields, values) != null) {
			return null;
		}
		if (modules.size() > SEMESTER_MODULE_LIMIT) {
			return null;
		}
		
		Semester semester = new Semester ();
		Set <SemesterModule> modules_set = new HashSet <> ();
		for (Module module : modules) {
			if (! module.getIsActive()) {
				return null;
			}
			SemesterModule semesterModule = new SemesterModule();
			semesterModule.setModule(module);
			semesterModule.setSemester(semester);
			modules_set.add(semesterModule);
		}
		
		semester.setAcademicYear(academicYear);
		semester.setAcademicSemester(academicSemester);
		semester.setModules(modules_set);
		return semester;
	}
	
	/**
	 * @return A list of AcademicSemesters that are not yet present in
	 * the passed academicYear
	 */
	public static List <AcademicSemester> filterSemesters (AcademicYear academicYear) {
		List <AcademicSemester> academicSemesters = Manager.selectAll(AcademicSemester.class);
		List <Semester> semesters = Manager.selectMultiple(Semester.class, "academicYear", academicYear);
		for (Semester semester : semesters) {
			CollectionManager.remove(academicSemesters, semester.getAcademicSemester());
		}
		return academicSemesters;	
	}
	
	public static List <ModuleWrapper> getLatestWrapped (AcademicSemester academicSemester) {
		if (academicSemester == null) {
			return wrapModules();
		}
		Semester latest = getLatest(academicSemester);
		return (latest == null) ? wrapModules() : wrapModules(latest);
	}
	
	public static Semester getLatest (AcademicSemester academicSemester) {
		List <Semester> list = Manager.selectMultiple(Semester.class, "academicSemester", academicSemester);
		if (list.isEmpty()) {
			return null;
		}
		Semester latest = list.get(0);
		for (Semester semester : list) {
			if (semester.getAcademicYear().getStart_date().after(latest.getAcademicYear().getStart_date())) {
				latest = semester;
			}
		}
		return latest;
	}
			
	public static List <ModuleWrapper> wrapModules (Semester semester) {
		List <ModuleWrapper> list = wrapModules();
		for (SemesterModule semesterModule : semester.getModules()) {
			for (ModuleWrapper moduleWrapper : list) {
				if (semesterModule.getModule().getId() == moduleWrapper.getModule().getId()) {
					moduleWrapper.setSelected(true);
					break;
				}
			}
		}
		
		list.sort((moduleWrapper_1, moduleWrapper_2) -> {
			if (moduleWrapper_1.isSelected == moduleWrapper_2.isSelected) {
				return 0;
			} else if (moduleWrapper_1.isSelected) {
				return -1;
			} else {
				return 1;
			}
		});
		return list;
	}
	
	public static List <ModuleWrapper> wrapModules () {
		List <ModuleWrapper> list = new ArrayList <> ();
		for (Module module : Manager.selectAll(Module.class)) {
			if (module.getIsActive()) {
				list.add(new ModuleWrapper(module));
			}
		}
		return list;
	}
	
	public static class ModuleWrapper implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public ModuleWrapper () {
			this (null);
		}
		
		public ModuleWrapper (Module module) {
			this (module, false);
		}
		
		public ModuleWrapper (Module module, boolean isSelected) {
			this.module = module;
			this.isSelected = isSelected;
		}
		
		private Module module;
		
		private boolean isSelected;

		public Module getModule() {
			return module;
		}

		public void setModule(Module module) {
			this.module = module;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
}
