package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.DateManager;
import hemmouda.bachmek.util.RegexManager;
import hemmouda.bachmek.util.StringManager;

public class AcadYearManager {
	
	public static String DEFAULT_START_DATE = "-09-15"; // HTML format
	public static String DEFAULT_FINISH_DATE = "-06-15";
	
	public static String getDefaultStartDate () {
		return "" +DateManager.getCurrentYear() +DEFAULT_START_DATE;
	}
	
	public static String getDefaultFinishDate () {
		return "" +(DateManager.getCurrentYear() +1) +DEFAULT_FINISH_DATE;
	}
	
	public static AcademicYear getNext (AcademicYear academicYear) {
		AcademicYear next = null;
		for (AcademicYear acadYear : getAfterOrEqual(academicYear)) {
			if (academicYear.equalsId(acadYear)) {
				continue;
			}
			if (next == null) {
				next = acadYear;
			} else if (acadYear.getFinish_date().before(next.getStart_date())) {
				next = acadYear;
			}
		}
		if (next == null) {
			next = init(DateManager.getYearFrom(academicYear.getStart_date()) +1);
			if (DateManager.intersectsStrict(academicYear.getStart_date(), academicYear.getFinish_date(), next.getStart_date(), next.getFinish_date())) {
				return null;
			}
			next.insert();
		} 
		return next;
	}
	
	/**
	 * @return the Academic Year with the abbreviation, if
	 * there is multiple ones, returns null
	 */
	public static AcademicYear getUniqueYear (String abbr) {
		if (abbr == null) {
			return null;
		}
		List <AcademicYear> list = Manager.selectAll(AcademicYear.class);
		AcademicYear found = null;
		for (AcademicYear academicYear : list) {
			if (academicYear.getABBR().equals(abbr)) {
				if (found != null) {
					return null;
				} else {
					found = academicYear;
				}
			}
		}
		return found;
	}
	
	private static AcademicYear init (int year) {
		AcademicYear academicYear = new AcademicYear();
		academicYear.setStart_date(StringManager.parseDate("" +year +DEFAULT_START_DATE));
		academicYear.setFinish_date(StringManager.parseDate("" +(year +1) +DEFAULT_FINISH_DATE));
		return academicYear;
	}
	
	public static AcademicYear getLatest () {
		List <AcademicYear> list = Manager.selectAll(AcademicYear.class);
		if (list.isEmpty()) {
			AcademicYear academicYear = init(DateManager.getCurrentYear());
			academicYear.insert();
			return academicYear;
		} else {
			AcademicYear latest = list.get(0);
			for (AcademicYear academicYear : list) {
				if (academicYear.getStart_date().after(latest.getStart_date())) {
					latest = academicYear;
				}
			}
			return latest;
		}
	}
	
	public static List <AcademicYear> getAfterOrEqual (AcademicYear academicYear) {
		List <AcademicYear> list = new ArrayList <> ();
		for (AcademicYear acadYear : Manager.selectAll(AcademicYear.class)) {
			if (academicYear.getStart_date().before(acadYear.getFinish_date())) {
				list.add(acadYear);
			}
		}
		return list;
	}
	
	/**
	 * <p>If it doesn't exist it will try to create it with the default dates,
	 *if it fails to (due to intersection) it returns null.
	 * @return The current year corresponding earliest AcademicYear, or null. 
	 */
	public static AcademicYear getEarliestCurrent () {
		List <AcademicYear> list = Manager.selectAll(AcademicYear.class);
		list.sort((academicYear_1, academicYear_2) -> (academicYear_1.getStart_date().compareTo(academicYear_1.getStart_date())));
		Date current_date = DateManager.getCurrentDate();
		for (AcademicYear academicYear : list) {
			if (DateManager.intersectsYear(current_date, academicYear.getStart_date(), academicYear.getFinish_date())) {
				return academicYear;
			}
		}
		
		AcademicYear academicYear = init(DateManager.getCurrentYear());
		for (AcademicYear acadYear : list) {
			if (DateManager.intersectsStrict(academicYear.getStart_date(), academicYear.getFinish_date(), acadYear.getStart_date(), acadYear.getFinish_date())) {
				return null;
			}
		}
		academicYear.insert();
		return academicYear;
	}
	
	public static AcademicYear validate (String start_date, String finish_date) {
		if (RegexManager.notMatches("^(\\d{4}\\-\\d{2}\\-\\d{2})$", start_date)){
			return null;
		}
		if (RegexManager.notMatches("^(\\d{4}\\-\\d{2}\\-\\d{2})$", finish_date)){
			return null;
		}

		AcademicYear academicYear = new AcademicYear ();
		academicYear.setStart_date(StringManager.parseDate(start_date));
		academicYear.setFinish_date(StringManager.parseDate(finish_date));

		return academicYear;
	}
	
	/**
	 * <p>Checks whether or not the academicYear intersects with one of 
	 * the academicYears.
	 * <p>If a targeted_academicYear is passed, it will first remove it
	 * from the list by id then check.
	 */
	public static boolean intersects (AcademicYear academicYear, List <AcademicYear> academicYears, AcademicYear target_academicYear) {
		if (target_academicYear != null) {
			CollectionManager.remove(academicYears, target_academicYear);
		}
		for (AcademicYear acadYear : academicYears) {
			if (DateManager.intersectsStrict(academicYear.getStart_date(), academicYear.getFinish_date(), acadYear.getStart_date(), acadYear.getFinish_date())) {
				return true;
			}
		}
		return false;
	}
	
}
