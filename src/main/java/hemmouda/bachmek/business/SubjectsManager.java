package hemmouda.bachmek.business;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.Assessment;
import hemmouda.bachmek.models.Element;
import hemmouda.bachmek.models.Module;
import hemmouda.bachmek.util.RegexManager;
import hemmouda.bachmek.util.StringManager;

public class SubjectsManager {
	
	public static Module validateModule (String name, String isActive) {
		if (RegexManager.notLength(2, 32, name)) {
			return null;
		}
		if (StringManager.parseBool(isActive) == null) {
			return null;
		}

		Module module = new Module ();
		module.setName(name);
		module.setIsActive(StringManager.parseBool(isActive));

		return module;
	}
	
	public static Module moduleExist (String name) {
		return Manager.selectUnique(Module.class, "name", StringManager.toLowerCase(name));
	}

	public static Element validateElement(Module module, String name, String isActive) {
		if (RegexManager.notLength(2, 32, name)) {
			return null;
		}
		if (StringManager.parseBool(isActive) == null) {
			return null;
		}

		Element element = new Element ();
		element.setModule(module);
		element.setName(name);
		element.setIsActive(StringManager.parseBool(isActive));

		return element;
	}
	
	public static Element elementExist (Module module, String name) {
		for (Element element : module.getElements()) {
			if (StringManager.equalsIgnoreCase(element.getName(), name)) {
				return element;
			}
		}
		return null;
	}

	public static Assessment validateAssessment(Element element, String name, String isActive) {
		if (RegexManager.notLength(2, 32, name)) {
			return null;
		}
		if (StringManager.parseBool(isActive) == null) {
			return null;
		}

		Assessment assessment = new Assessment ();
		assessment.setElement(element);
		assessment.setName(name);
		assessment.setIsActive(StringManager.parseBool(isActive));

		return assessment;
	}
	
	public static Assessment assessmentExist (Element element, String name) {
		for (Assessment assessment : element.getAssessments()) {
			if (StringManager.equalsIgnoreCase(assessment.getName(), name)) {
				return assessment;
			}
		}
		return null;
	}
	
}
