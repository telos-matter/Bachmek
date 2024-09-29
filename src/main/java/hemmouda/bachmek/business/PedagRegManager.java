package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.GradeManager.ElementGradeWrapper;
import hemmouda.bachmek.listeners.InitListener;
import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.AdministrativeRegistration;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.PedagogicalRegistration;
import hemmouda.bachmek.models.Semester;
import hemmouda.bachmek.models.SemesterModule;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.ExcelManager;

import jakarta.servlet.http.HttpServletResponse;

import hemmouda.bachmek.models.Module;

public class PedagRegManager {

	public static final int STUDENT_MODULE_LIMIT = 7;
	
	public static void writeDownloadList (Semester semester, HttpServletResponse response) {
		List <Module> modules = new ArrayList <> ();
		for (SemesterModule semesterModule : semester.getModules()) {
			modules.add(semesterModule.getModule());
		}
		List <PedagogicalRegistration> pedagRegs_list = MMManager.getRegisteredPedagRegList(semester);
		String [] file_header = new String [3 +modules.size()];
		file_header [0] = "CNE";
		file_header [1] = "Last name";
		file_header [2] = "First name";
		for (int i = 0; i < modules.size(); i++) {
			file_header [i +3] = modules.get(i).getName();
		}
		String [][] data = new String [pedagRegs_list.size()][file_header.length];
		for (int i = 0; i < pedagRegs_list.size(); i++) {
			PedagogicalRegistration pedagogicalRegistration = pedagRegs_list.get(i);
			data [i][0] = "" +pedagogicalRegistration.getAdministrativeRegistration().getCne();
			data [i][1] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getLast_name();
			data [i][2] = "" +pedagogicalRegistration.getAdministrativeRegistration().getOnlineRegistration().getFirst_name();
			for (int j = 0; j < modules.size(); j++) {
				Module module = modules.get(j);
				data [i][j +3] = "" + (pedagogicalRegistration.hasModule(module) ? "Yes" : "No");
			}
		}
		
		ExcelManager.write(InitListener.getEstmName() +"s' Students' list", semester.getDashedName(), file_header, data, response);
	}
	
	public static List <ElementGradeWrapper> getPedagRegGrades (PedagogicalRegistration pedagogicalRegistration) {
		List <ElementGradeWrapper> list = new ArrayList <> ();
		for (Semester semester : pedagogicalRegistration.getSemesters()) {
			if (!semester.isApproved()) {
				return list;
			}
		}
		for (Element element : pedagogicalRegistration.getElements()) {
			list.add(GradeManager.computeElement(pedagogicalRegistration, element));
		}
		return list;
	}
	
	public static void createNewAdminRegPedagRegs (AdministrativeRegistration administrativeRegistration) {
//		AcademicYear academicYear = AcadYearManager.getEarliestCurrent();
//		if (academicYear == null) {
//			return;
//		}
		// The fck? No it should be the acadYeawr he was registered in, no?
		// Like this, right?
		AcademicYear academicYear = administrativeRegistration.getAcademicYear();
				
		String [] fields = {"academicYear", "academicSemester"};
		for (AcademicSemester academicSemester : administrativeRegistration.getAcademicStage().getAcademicSemesters()) {
			Object [] values = {academicYear, academicSemester};
			Semester semester = Manager.selectUnique(Semester.class, fields, values);
			if (semester != null) {
				PedagogicalRegistration pedagogicalRegistration = new PedagogicalRegistration ();
				pedagogicalRegistration.setAdministrativeRegistration(administrativeRegistration);
				pedagogicalRegistration.getSemesters().add(semester);
				pedagogicalRegistration.getElements().addAll(semester.getElements());
				pedagogicalRegistration.insert();
			}
		}
	}
	
	public static PedagogicalRegistration createNextPedagReg (PedagogicalRegistration pedagogicalRegistration, Semester current_semester) {
		AcademicSemester next_academicSemester = current_semester.getAcademicSemester().getSameNextStage();
		if (next_academicSemester == null) {
			return null;
		}
		AcademicYear next_academicYear = AcadYearManager.getNext(current_semester.getAcademicYear());
		if (next_academicYear == null) {
			return null;
		}
		for (PedagogicalRegistration pedagReg : Manager.selectAll(PedagogicalRegistration.class)) {
			if (pedagogicalRegistration.getCne().equals(pedagReg.getCne()) && next_academicYear.equalsId(pedagReg.getAcademicYear()) && CollectionManager.boolContains(pedagReg.getAcademicSemesters(), next_academicSemester)) { //No sure this certain, probably not (not all cases inclusive)
				return null;
			}
		}
		String [] fields = {"academicYear", "academicSemester"};
		Object [] values = {next_academicYear, next_academicSemester};
		Semester semester = Manager.selectUnique(Semester.class, fields, values);
		if (semester == null) {
			return null;
		}
		PedagogicalRegistration new_pedagogicalRegistration = new PedagogicalRegistration ();
		new_pedagogicalRegistration.setAdministrativeRegistration(pedagogicalRegistration.getAdministrativeRegistration());
		new_pedagogicalRegistration.getSemesters().add(semester);
		new_pedagogicalRegistration.getElements().addAll(semester.getElements());
		new_pedagogicalRegistration.insert();
		return new_pedagogicalRegistration;
	}

	public static PedagogicalRegistration validatePedagogicalRegistration (PedagogicalRegistration pedagogicalRegistration, AdministrativeRegistration administrativeRegistration, Semester semester, List <Element> elements) {
		boolean created = false;
		if (pedagogicalRegistration == null) {
			created = true;
			if (elements.size() == 0) {
				return null;
			}
			pedagogicalRegistration = new PedagogicalRegistration();
			pedagogicalRegistration.setAdministrativeRegistration(administrativeRegistration);
		} else {
			if (CollectionManager.boolContains(pedagogicalRegistration.getSemesters(), semester)) {
				return null;
			}
			if (pedagogicalRegistration.getSemesters().size() == 0) {
				System.out.println("/!\\ validatePedagogicalRegistration has an \"empty\" PedagogicalRegistration: " +pedagogicalRegistration);
				return null;
			} 
			if (pedagogicalRegistration.getAcademicYear().getId() != semester.getAcademicYear().getId()) {
				return null;
			}
			// Could do with adding a check if the Semester is in same Sequence
			// But at this point it could do with removing bunch other checks that
			// are repetitive
		}
		
		for (Element element : elements) {
			if (!element.getIsActive()) {
				return null;
			}
		}
		
		pedagogicalRegistration.getElements().addAll(elements);
		pedagogicalRegistration.getSemesters().add(semester);
		
		if (created) {
			pedagogicalRegistration.insert();
		} else {
			pedagogicalRegistration.update();
		}
		
		return pedagogicalRegistration;
	}
	
	/**
	 * @return the {@link PedagogicalRegistration} that is registered with
	 * that {@link AdministrativeRegistration} and with same sequence 
	 * of the {@link Semester}s' {@link AcademicSemester} and {@link AcademicYear}.
	 * <ul> For example:
	 * <li> If an 2020 BGI S1 is passed, then it'll return (if found) the 
	 * {@link PedagogicalRegistration} that has S1 or S3 or S5 (2020) or a combination 
	 */
	public static PedagogicalRegistration getPedagogicalRegistration (AdministrativeRegistration administrativeRegistration, Semester semester) {
		for (PedagogicalRegistration pedagogicalRegistration : Manager.selectMultiple(PedagogicalRegistration.class, "administrativeRegistration", administrativeRegistration)) {
			if (pedagogicalRegistration.getAcademicYear() != null && pedagogicalRegistration.getAcademicYear().getId() == semester.getAcademicYear().getId()) {
				if (CollectionManager.intersection(semester.getAcademicSemester().getSameSequenceAcademicSemesters(), pedagogicalRegistration.getAcademicSemesters()).size() > 0) {
					return pedagogicalRegistration;
				}
			}
		}
		return null;
	}
	
	public static boolean canAddElements (PedagogicalRegistration pedagogicalRegistration) {
		return countModules(pedagogicalRegistration.getElements()) < STUDENT_MODULE_LIMIT;
	}
	
	public static boolean canAddElements (PedagogicalRegistration pedagogicalRegistration, List <Element> elements) {
		return countModules(pedagogicalRegistration.getElements()) +countModules(elements) <= STUDENT_MODULE_LIMIT;
	}
	
	public static boolean validateModulesCount (Collection <Element> elements) {
		int count = countModules(elements);
		return count > 0 && count <= STUDENT_MODULE_LIMIT;
	}
	
	public static int countModules (Collection <Element> elements) {
		Set <Module> modules = new HashSet <> ();
		for (Element element : elements) {
			CollectionManager.addAsSet(modules, element.getModule());
		}
		return modules.size();
	}
	
}
