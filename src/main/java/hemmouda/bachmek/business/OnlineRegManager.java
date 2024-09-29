package hemmouda.bachmek.business;

import hemmouda.bachmek.enums.BacHonour;
import hemmouda.bachmek.enums.BacSerie;
import hemmouda.bachmek.enums.Gender;
import hemmouda.bachmek.enums.Major;
import hemmouda.bachmek.models.OnlineRegistrations;
import hemmouda.bachmek.util.DateManager;
import hemmouda.bachmek.util.RegexManager;
import hemmouda.bachmek.util.StringManager;

public class OnlineRegManager {

	public static OnlineRegistrations validate (String massar_code, String ar_first_name, String ar_last_name, String first_name, String last_name, String cin, String nationality, String gender, String birthdate, String ar_birth_place, String birth_place, String residence_town, String province, String bac_year, String high_school, String bac_place, String academy, String bac_serie, String bac_honour, String major) {
		if (RegexManager.notMatches("^([A-Z][0-9]{9})$", massar_code)) {
			return null;
		}
		if (RegexManager.notMatches("^([ء-ي\s]{2,64})$", ar_first_name)) {
			return null;
		}
		if (RegexManager.notMatches("^([ء-ي\s]{2,64})$", ar_last_name)) {
			return null;
		}
		if (RegexManager.notMatches("^([A-Za-z\s]{2,64})$", first_name)) {
			return null;
		}
		if (RegexManager.notMatches("^([A-Za-z\s]{2,64})$", last_name)) {
			return null;
		}
		if (RegexManager.notMatches("^([A-Z][0-9]{4,15})$", cin)) {
			return null;
		}
		if (RegexManager.notLength(3, 32, nationality)) {
			return null;
		}
		if (Gender.hasNotValue(gender)) {
			return null;
		}
		if (RegexManager.notMatches("^(\\d{4}\\-\\d{2}\\-\\d{2})$", birthdate) || DateManager.isAgeInferior(17, birthdate)){
			return null;
		}
		if (RegexManager.notLength(2, 128, ar_birth_place)) {
			return null;
		}
		if (RegexManager.notLength(2, 128, birth_place)) {
			return null;
		}
		if (RegexManager.notLength(2, 64, residence_town)) {
			return null;
		}
		if (RegexManager.notLength(2, 64, province)) {
			return null;
		}
		if (RegexManager.notMatches("^(\\d{4})$", bac_year) && (Integer.parseInt(bac_year) >= 1900 && Integer.parseInt(bac_year) <= (DateManager.getCurrentYear() +1))) {
			return null;
		}
		if (RegexManager.notLength(2, 64, high_school)) {
			return null;
		}
		if (RegexManager.notLength(2, 64, bac_place)) {
			return null;
		}
		if (RegexManager.notLength(2, 64, academy)) {
			return null;
		}
		if (BacSerie.hasNotValue(bac_serie)) {
			return null;
		}
		if (BacHonour.hasNotValue(bac_honour)) {
			return null;
		}
		if (Major.hasNotValue(major)) {
			return null;
		}
		
		OnlineRegistrations onlineRegistrations = new OnlineRegistrations ();
		onlineRegistrations.setMassar_code(massar_code);
		onlineRegistrations.setAr_first_name(ar_first_name);
		onlineRegistrations.setAr_last_name(ar_last_name);
		onlineRegistrations.setFirst_name(first_name);
		onlineRegistrations.setLast_name(last_name);
		onlineRegistrations.setCin(cin);
		onlineRegistrations.setNationality(nationality);
		onlineRegistrations.setGender(Gender.getGenderEnum(gender));
		onlineRegistrations.setBirthdate(StringManager.parseDate(birthdate));
		onlineRegistrations.setAr_birth_place(ar_birth_place);
		onlineRegistrations.setBirth_place(birth_place);
		onlineRegistrations.setResidence_town(residence_town);
		onlineRegistrations.setProvince(province);
		onlineRegistrations.setBac_year(Integer.parseInt(bac_year));
		onlineRegistrations.setHigh_school(high_school);
		onlineRegistrations.setBac_place(bac_place);
		onlineRegistrations.setAcademy(academy);
		onlineRegistrations.setBac_serie(BacSerie.getBacSerieEnum(bac_serie));
		onlineRegistrations.setBac_honour(BacHonour.getBacHonourEnum(bac_honour));
		onlineRegistrations.setMajor(Major.getMajorEnum(major));

		return onlineRegistrations;
	}
	
}
