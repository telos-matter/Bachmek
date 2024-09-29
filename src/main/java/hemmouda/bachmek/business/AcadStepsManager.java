package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import hemmouda.bachmek.models.AcademicSemester;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.MajorEnum;
import hemmouda.bachmek.util.StringManager;

public class AcadStepsManager {

	public static AcademicStage getAcademicStage (MajorEnum major, String sequence) {
		Integer sequence_int = StringManager.parseInt(sequence);
		if (sequence_int == null) {
			return null;
		}
		for (AcademicStage academicStage : major.getAcademicStages()) {
			if (academicStage.getSequence() == sequence_int) {
				return academicStage;
			}
		}
		return null;
	}
	
	public static AcademicStage validateStage (MajorEnum major, String sequence, String isActive) {
		if (StringManager.parseInt(sequence) == null) {
			return null;
		}
		if (StringManager.parseBool(isActive) == null) {
			return null;
		}

		AcademicStage academicStage = new AcademicStage ();
		academicStage.setMajor(major);
		academicStage.setSequence(StringManager.parseInt(sequence));
		academicStage.setIsActive(StringManager.parseBool(isActive));

		return academicStage;
	}
	
	public static AcademicStage getStageWithSequence (int sequence, MajorEnum major) {
		for (AcademicStage academicStage : major.getAcademicStages()) {
			if (academicStage.getSequence() == sequence) {
				return academicStage;
			}
		}
		return null;
	}
	
	public static List <AcademicStage> collapseStagesSequences (MajorEnum major) {
		List <AcademicStage> list = new ArrayList <> (major.getAcademicStages());
		list.sort(Comparator.comparing(AcademicStage::getSequence));
		for (int i = 1; i <= list.size(); i++) {
			list.get(i -1).setSequence(i);
		}
		return list;
	}
	
	public static AcademicSemester validateSemester (AcademicStage academicStage, String sequence, String isActive) {
		if (StringManager.parseInt(sequence) == null) {
			return null;
		}
		if (StringManager.parseBool(isActive) == null) {
			return null;
		}

		AcademicSemester academicSemester = new AcademicSemester ();
		academicSemester.setAcademicStage(academicStage);
		academicSemester.setSequence(StringManager.parseInt(sequence));
		academicSemester.setIsActive(StringManager.parseBool(isActive));

		return academicSemester;
	}
	
	public static AcademicSemester getSemesterWithSequence (int sequence, AcademicStage academicStage) {
		for (AcademicSemester academicSemester : academicStage.getAcademicSemesters()) {
			if (academicSemester.getSequence() == sequence) {
				return academicSemester;
			}
		}
		return null;
	}
	
	public static List <AcademicSemester> collapseSemestersSequences (AcademicStage academicStage) {
		List <AcademicSemester> list = new ArrayList <> (academicStage.getAcademicSemesters());
		list.sort(Comparator.comparing(AcademicSemester::getSequence));
		for (int i = 1; i <= list.size(); i++) {
			list.get(i -1).setSequence(i);
		}
		return list;
	}
	
}
