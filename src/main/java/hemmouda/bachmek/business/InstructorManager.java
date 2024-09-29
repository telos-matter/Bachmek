package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.listeners.InitListener;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.models.ElementInstructor;
import hemmouda.bachmek.models.Instructor;
import hemmouda.bachmek.models.MajorEnum;
import hemmouda.bachmek.models.MajorRep;
import hemmouda.bachmek.models.ModuleRep;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.ExcelManager;

import jakarta.servlet.http.HttpServletResponse;

public class InstructorManager {
	
	public static final String [] FILE_HEADER = {"Instructors' CIN", "Instructors' full name", "Associated item"};

	public static void writeDownloadList (AcademicYear academicYear, InstructorRole instructorRole, HttpServletResponse response) {
		String [][] data = null;
		switch (instructorRole) {
		case ELEMENT_INSTRUCTOR: 
			List <ElementInstructor> list_1 = Manager.selectMultiple(ElementInstructor.class, "academicYear", academicYear);
			data = new String [list_1.size()][3];
			for (int i = 0; i < data.length; i++) {
				data [i][0] = list_1.get(i).getInstructor().getUser().getCin();
				data [i][1] = list_1.get(i).getInstructor().getUser().getFullName();
				data [i][2] = list_1.get(i).getAssociatedItem().getName();
			}
			break;
		case MODULE_REP: 
			List <ModuleRep> list_2 = Manager.selectMultiple(ModuleRep.class, "academicYear", academicYear);
			data = new String [list_2.size()][3];
			for (int i = 0; i < data.length; i++) {
				data [i][0] = list_2.get(i).getInstructor().getUser().getCin();
				data [i][1] = list_2.get(i).getInstructor().getUser().getFullName();
				data [i][2] = list_2.get(i).getAssociatedItem().getName();
			}
			break;
		case MAJOR_REP: 
			List <MajorRep> list_3 = Manager.selectMultiple(MajorRep.class, "academicYear", academicYear);
			data = new String [list_3.size()][3];
			for (int i = 0; i < data.length; i++) {
				data [i][0] = list_3.get(i).getInstructor().getUser().getCin();
				data [i][1] = list_3.get(i).getInstructor().getUser().getFullName();
				data [i][2] = list_3.get(i).getAssociatedItem().getName();
			}
			break;
		}
		
		ExcelManager.write(InitListener.getEstmName() +"s' Instructors list - " +academicYear.getABBR(), instructorRole.getName() +" list", FILE_HEADER, data, response);
	}
	
	public static Object validateCreation (AcademicYear academicYear, InstructorRole instructorRole, Instructor instructor, Object item) {
		if (instructor.getIsNotActive()) {
			return null;
		}
		switch (instructorRole) {
		case ELEMENT_INSTRUCTOR:
			if (item.getClass() != Element.class) {
				return null;
			}
			Element element = (Element) item;
			if (element.getIsNotActive()) {
				return null;
			}
			List <Element> list_1 = new ArrayList <> ();
			for (ElementInstructor elementInstructor : Manager.selectMultiple(ElementInstructor.class, "academicYear", academicYear)) {
				list_1.add(elementInstructor.getElement());
			}
			if (CollectionManager.boolContains(list_1, element)) {
				return null;
			}
			ElementInstructor elementInstructor = new ElementInstructor();
			elementInstructor.setAcademicYear(academicYear);
			elementInstructor.setElement(element);
			elementInstructor.setInstructor(instructor);
			elementInstructor.insert();
			return elementInstructor;
		case MODULE_REP:
			if (item.getClass() != Module.class) {
				return null;
			}
			Module module = (Module) item;
			if (module.getIsNotActive()) {
				return null;
			}
			List <Module> list_2 = new ArrayList <> ();
			for (ModuleRep moduleRep : Manager.selectMultiple(ModuleRep.class, "academicYear", academicYear)) {
				list_2.add(moduleRep.getModule());
			}
			if (CollectionManager.boolContains(list_2, module)) {
				return null;
			}
			ModuleRep moduleRep = new ModuleRep();
			moduleRep.setAcademicYear(academicYear);
			moduleRep.setModule(module);
			moduleRep.setInstructor(instructor);
			moduleRep.insert();
			return moduleRep;
		case MAJOR_REP:
			if (item.getClass() != MajorEnum.class) {
				return null;
			}
			MajorEnum major = (MajorEnum) item;
			if (major.getIsNotActive()) {
				return null;
			}
			List <MajorEnum> list_3 = new ArrayList <> ();
			for (MajorRep majorRep : Manager.selectMultiple(MajorRep.class, "academicYear", academicYear)) {
				list_3.add(majorRep.getMajor());
			}
			if (CollectionManager.boolContains(list_3, major)) {
				return null;
			}
			MajorRep majorRep = new MajorRep();
			majorRep.setAcademicYear(academicYear);
			majorRep.setMajor(major);
			majorRep.setInstructor(instructor);
			majorRep.insert();
			return majorRep;
		}
		return null;
	}
	
	/**
	 * @return list of the instructorRoles' corresponding item, that is active
	 * and that doesn't already exist for the current year
	 * <p>For example, MajorRep will return a list of Majors that are active
	 * and that are not already associated with a MajorRep for the academicYear 
	 */
	@SuppressWarnings("unchecked")
	public static List <?> getFilteredList (AcademicYear academicYear, InstructorRole instructorRole) {
		switch (instructorRole) {
		case ELEMENT_INSTRUCTOR:
			Collection <Element> list_1 =  (Collection <Element>) Activatable.filterActive(Element.class);
			for (ElementInstructor elementInstructor : Manager.selectMultiple(ElementInstructor.class, "academicYear", academicYear)) {
				CollectionManager.remove(list_1, elementInstructor.getElement());
			}
			return (List <?>) list_1;
		case MODULE_REP:
			Collection <Module> list_2 =  (Collection <Module>) Activatable.filterActive(Module.class);
			for (ModuleRep moduleRep : Manager.selectMultiple(ModuleRep.class, "academicYear", academicYear)) {
				CollectionManager.remove(list_2, moduleRep.getModule());
			}
			return (List <?>) list_2;
		case MAJOR_REP:
			Collection <MajorEnum> list_3 =  (Collection <MajorEnum>) Activatable.filterActive(MajorEnum.class);
			for (MajorRep majorRep : Manager.selectMultiple(MajorRep.class, "academicYear", academicYear)) {
				CollectionManager.remove(list_3, majorRep.getMajor());
			}
			return (List <?>) list_3;
		}
		return null;
	}
	
	public static enum InstructorRole {
		MAJOR_REP,
		MODULE_REP,
		ELEMENT_INSTRUCTOR;
		
		public static List <InstructorRole> getInstructorRoles (Instructor instructor, AcademicYear academicYear) {
			List <InstructorRole> list = new ArrayList <> ();
			String [] fields = {"instructor", "academicYear"};
			Object [] values = {instructor, academicYear};
			for (InstructorRole value : InstructorRole.values()) {
				if (Manager.selectMultiple(value.getCorrespondingClass(), fields, values).size() != 0) {
					list.add(value);
				}
			}
			return list;
		}
		
		public static InstructorRole getInstructorRole (String name) {
			try {
				return (name == null) ? null : InstructorRole.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException exception) {
				return null;
			}
		}
		
		public Class <?> getCorrespondingClass () {
			switch (this) {
			case MAJOR_REP: return MajorRep.class;
			case MODULE_REP: return ModuleRep.class;
			case ELEMENT_INSTRUCTOR: return ElementInstructor.class;
			}
			return null;
		}
		
		public Class <? extends Activatable> getAssociatedClass () {
			switch (this) {
			case MAJOR_REP: return MajorEnum.class;
			case MODULE_REP: return Module.class;
			case ELEMENT_INSTRUCTOR: return Element.class;
			}
			return null;
		}
		
		public String getName () {
			switch (this) {
			case MAJOR_REP: return "Major representative";
			case MODULE_REP: return "Module representative";
			case ELEMENT_INSTRUCTOR: return "Element instructor";
			}
			return null;
		}
	}
	
}
