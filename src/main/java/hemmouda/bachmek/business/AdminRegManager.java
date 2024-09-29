package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.controllers.AdminRegServlet;
import hemmouda.bachmek.enums.BacHonour;
import hemmouda.bachmek.enums.BacSerie;
import hemmouda.bachmek.enums.Gender;
import hemmouda.bachmek.enums.Major;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.AdministrativeRegistration;
import hemmouda.bachmek.models.OnlineRegistration;
import hemmouda.bachmek.models.OnlineRegistrations;
import hemmouda.bachmek.models.Student;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.util.CollectionManager;
import hemmouda.bachmek.util.ExcelManager;
import hemmouda.bachmek.util.RegexManager;
import hemmouda.bachmek.util.StringManager;

import jakarta.servlet.http.HttpServletResponse;

public class AdminRegManager {

	public static void writeDownloadList (Boolean create, HttpServletResponse response) {
		if (create) {
			ExcelManager.write(AdminRegServlet.CREATION_TEMPLATE_NAME, "To be created admin. reg(s).", AdminRegServlet.CREATION_HEADERS, null, response);
		} else {
			ExcelManager.write(AdminRegServlet.DELETION_TEMPLATE_NAME, "To be deleted admin. reg(s).", AdminRegServlet.DELETION_HEADERS, null, response);
		}
	}
	
	public static int validateExcelCreation (List <String []> data) {
		if (!ExcelManager.assertUnique(data, 0)) { // Massar code
			System.out.println("Massar code");
			return 0;
		}
		if (!ExcelManager.assertUnique(data, 5)) { // CIN
			System.out.println("CIN");
			return 0;
		}
		if (!ExcelManager.assertUnique(data, 20)) { // CNE
			System.out.println("CNE");
			return 0;
		}
		List <AdministrativeRegistration> administrativeRegistration_list = new ArrayList <> ();
		for (String [] row : data) {
//			System.out.println("error:");
//			for (String s : row) {
//				System.out.println(s);
//			}
//			System.out.println(row);
			OnlineRegistration onlineRegistration = validateOnlineRegistration(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10], row[11], row[12], row[13], row[14], row[15], row[16], row[17], row[18], row[19]);
			if (onlineRegistration == null) {
				System.out.println("Here?1");
				return 0;
			}
			if (Manager.selectUnique(OnlineRegistration.class, "massar_code", onlineRegistration.getMassar_code()) != null) {
				System.out.println("Here?2");
				return 0;
			}
			if (onlineRegistration.getCin() != null && Manager.selectUnique(OnlineRegistration.class, "cin", onlineRegistration.getCin()) != null) {
				System.out.println("Here?3");
				return 0;
			}
			AcademicYear academicYear = AcadYearManager.getUniqueYear(row[26]);
			if (academicYear == null) {
				System.out.println("Here?4");
				return 0;
			}
			AcademicStage academicStage = AcadStepsManager.getAcademicStage(onlineRegistration.getMajor(), row[27]);
			if (academicStage == null) {
				System.out.println("Here?5");
				return 0;
			}
			AdministrativeRegistration administrativeRegistration = validateAdministrativeRegistration(row[20], row[21], row[22], row[23], row[24], row[25], academicYear, academicStage, onlineRegistration);
			if (administrativeRegistration == null) {
				System.out.println("Here?6");
				return 0;
			}
			if (Manager.selectUnique(AdministrativeRegistration.class, "cne", administrativeRegistration.getCne()) != null) {
				System.out.println("Here?7");
				return 0;
			}
			administrativeRegistration_list.add(administrativeRegistration);
		}
		
		for (AdministrativeRegistration administrativeRegistration : administrativeRegistration_list) {
			administrativeRegistration.getOnlineRegistration().insert();
			createAdministrativeRegistration(administrativeRegistration);
		}
		
		return administrativeRegistration_list.size();
	}
	
	public static int validateExcelDeletion (List <String[]> data) {
		List <AdministrativeRegistration> list = new ArrayList <> ();
		for (String [] row : data) {
			Long cne = StringManager.parseLong(row[0]);
			if (cne == null) {
				return 0;
			}
			AdministrativeRegistration administrativeRegistration = Manager.selectUnique(AdministrativeRegistration.class, "cne", "" +cne);
			if (administrativeRegistration == null) {
				return 0;
			}
			CollectionManager.addAsSet(list, administrativeRegistration);
		}
		for (AdministrativeRegistration administrativeRegistration : list) {
			if (! Manager.deleteIfPossible(AdministrativeRegistration.class, administrativeRegistration.getId())) {
				return -100;
			}
		}
		return list.size();
	}
	
	//Main one
	public static void createAdministrativeRegistration (AdministrativeRegistration administrativeRegistration) {
		administrativeRegistration.insert();
		User user = UserManager.createUser(StringManager.toLowerCase(administrativeRegistration.getOnlineRegistration().getLast_name()), administrativeRegistration.getOnlineRegistration().getFirst_name(), administrativeRegistration.getOnlineRegistration().getLast_name(), administrativeRegistration.getOnlineRegistration().getGender());
		user.setGender(administrativeRegistration.getOnlineRegistration().getGender());
		user.setBirthdate(administrativeRegistration.getOnlineRegistration().getBirthdate());
		user.setPhone_number(administrativeRegistration.getPhone_number());
		user.setPersonal_address(administrativeRegistration.getPersonal_address());
		user.setCin(administrativeRegistration.getOnlineRegistration().getCin());
		user.update();
		Student student = new Student();
		student.setAdministrativeRegistration(administrativeRegistration);
		student.setUser(user);
		student.insert();
		if (administrativeRegistration.getAcademicStage().getSequence() == 1) {
			PedagRegManager.createNewAdminRegPedagRegs(administrativeRegistration);
		}
	}
	
	public static AdministrativeRegistration validateAdministrativeRegistration (String cne, String personal_address, String phone_number, String parents_address, String diplomas, String isTuition, AcademicYear academicYear, AcademicStage academicStage, OnlineRegistration onlineRegistration) {
		if (RegexManager.isEmpty(personal_address)) {
			personal_address = null;
		}
		if (RegexManager.isEmpty(phone_number)) {
			phone_number = null;
		}
		if (RegexManager.isEmpty(parents_address)) {
			parents_address = null;
		}
		if (RegexManager.isEmpty(isTuition)) {
			isTuition = "0";
		}
		if (RegexManager.isEmpty(diplomas)) {
			diplomas = null;
		}		
		if (RegexManager.notMatches("^([0-9]{9})$", cne)) {
			System.out.println(onlineRegistration.getMassar_code());
			System.out.println(cne);
			System.out.println("this?2");
			return null;
		}
		if (personal_address != null && RegexManager.notLength(2, 128, personal_address)) {
			System.out.println("this?22");
			return null;
		}
		if (phone_number != null && RegexManager.notMatches("^(\\+?[0-9]{9,12})$", phone_number)) {
			System.out.println(onlineRegistration.getMassar_code());
			System.out.println(phone_number);
			System.out.println("this?1");
			return null;
		}
		if (parents_address != null && RegexManager.notLength(2, 128, parents_address)) {
			System.out.println("this?20");
			return null;
		}
		if (diplomas != null && RegexManager.notLength(2, 10000, diplomas)) {
			System.out.println("this?21");
			return null;
		}
		
		if (!academicStage.getIsActive()) {
			System.out.println("this?10");
			return null;
		}
		if (CollectionManager.contains(onlineRegistration.getMajor().getAcademicStages(), academicStage) == null) {
			System.out.println("this?11");
			return null;
		}

		AdministrativeRegistration administrativeRegistration = new AdministrativeRegistration ();
		administrativeRegistration.setCne(cne);
		administrativeRegistration.setPersonal_address(personal_address);
		administrativeRegistration.setPhone_number(phone_number);
		administrativeRegistration.setParents_address(parents_address);
		administrativeRegistration.setDiplomas(diplomas);
		administrativeRegistration.setIsTuition(StringManager.parseBool(isTuition));
		administrativeRegistration.setAcademicYear(academicYear);
		administrativeRegistration.setAcademicStage(academicStage);
		administrativeRegistration.setOnlineRegistration(onlineRegistration);

		return administrativeRegistration;
	}
	
	public static OnlineRegistration validateOnlineRegistration (String massar_code, String ar_first_name, String ar_last_name, String first_name, String last_name, String cin, String nationality, String gender, String birthdate, String ar_birth_place, String birth_place, String residence_town, String province, String bac_year, String high_school, String bac_place, String academy, String bac_serie, String bac_honour, String major) {
		if (RegexManager.isEmpty(ar_first_name)) {
			ar_first_name = null;
		}
		if (RegexManager.isEmpty(ar_last_name)) {
			ar_last_name = null;		
		}
		if (RegexManager.isEmpty(cin)) {
			cin = null;
		}
		if (RegexManager.isEmpty(nationality)) {
			nationality = null;
		}
		if (RegexManager.isEmpty(birthdate)){
			birthdate =  null;
		}
		if (RegexManager.isEmpty(ar_birth_place)) {
			ar_birth_place = null;
		}
		if (RegexManager.isEmpty(birth_place)) {
			birth_place = null;
		}
		if (RegexManager.isEmpty(residence_town)) {
			residence_town = null;
		}
		if (RegexManager.isEmpty(province)) {
			province = null;
		}
		if (RegexManager.isEmpty(high_school)) {
			high_school = null;
		}
		if (RegexManager.isEmpty(bac_place)) {
			bac_place = null;
		}
		if (RegexManager.isEmpty(academy)) {
			academy = null;
		}
		if (RegexManager.notMatches("^([A-Z][0-9]{9})$", massar_code)) {
			System.out.println("out0");
			return null;		
		}
		if (ar_first_name != null && RegexManager.notMatches("^([ء-ي\s]{2,64})$", ar_first_name)) {
			System.out.println("out0.1");
			return null;		
		}
		if (ar_last_name != null && RegexManager.notMatches("^([ء-ي\s]{2,64})$", ar_last_name)) {
			System.out.println("out0.2");
			return null;				
		}
		if (RegexManager.notMatches("^([A-Za-z\s]{2,64})$", first_name)) {
			System.out.println("out1");
			return null;		
		}
		if (RegexManager.notMatches("^([A-Za-z\s\\-\\']{2,64})$", last_name)) {
			System.out.println("out2");
			return null;			
		}
		if (cin != null  && RegexManager.notMatches("^([A-Z][0-9]{4,15})$", cin)) {
			System.out.println("out3");
			return null;		
		}
		if (nationality != null && RegexManager.notLength(3, 32, nationality)) {
			return null;
		}
		if (Gender.hasNotValue(gender)) {
			System.out.println("out4");
			return null;
		}
		if (birthdate != null && RegexManager.notMatches("^(\\d{4}\\-\\d{2}\\-\\d{2})$", birthdate)){
			System.out.println("out5" +birthdate);
			System.out.println(massar_code);
			return null;
		}
		if (ar_birth_place != null && RegexManager.notLength(2, 128, ar_birth_place)) {
			return null;
		}
		if (birth_place != null && RegexManager.notLength(2, 128, birth_place)) {
			return null;
		}
		if (residence_town != null && RegexManager.notLength(2, 64, residence_town)) {
			return null;
		}
		if (province != null && RegexManager.notLength(2, 64, province)) {
			return null;
		}
		if (RegexManager.notMatches("^(\\d{4})$", bac_year)) {
			return null;
		}
		if (high_school != null && RegexManager.notLength(2, 64, high_school)) {
			System.out.println("out7");
			return null;
		}
		if (bac_place != null && RegexManager.notLength(2, 64, bac_place)) {
			System.out.println("out8");
			return null;
		}
		if (academy != null && RegexManager.notLength(2, 64, academy)) {
			System.out.println("out9");
			return null;
		}
		if (BacSerie.hasNotValue(bac_serie)) {
			return null;
		}
		if (BacHonour.hasNotValue(bac_honour)) {
			return null;
		}
		if (Major.hasNotValue(major) || !Major.getMajorEnum(major).getIsActive()) {
			return null;
		}
		
		OnlineRegistration onlineRegistration = new OnlineRegistration ();
		onlineRegistration.setMassar_code(massar_code);
		onlineRegistration.setAr_first_name(ar_first_name);
		onlineRegistration.setAr_last_name(ar_last_name);
		onlineRegistration.setFirst_name(first_name);
		onlineRegistration.setLast_name(last_name);
		onlineRegistration.setCin(cin);
		onlineRegistration.setNationality(nationality);
		onlineRegistration.setGender(Gender.getGenderEnum(gender));
		onlineRegistration.setBirthdate(StringManager.parseDate(birthdate));
		onlineRegistration.setAr_birth_place(ar_birth_place);
		onlineRegistration.setBirth_place(birth_place);
		onlineRegistration.setResidence_town(residence_town);
		onlineRegistration.setProvince(province);
		onlineRegistration.setBac_year(Integer.parseInt(bac_year));
		onlineRegistration.setHigh_school(high_school);
		onlineRegistration.setBac_place(bac_place);
		onlineRegistration.setAcademy(academy);
		onlineRegistration.setBac_serie(BacSerie.getBacSerieEnum(bac_serie));
		onlineRegistration.setBac_honour(BacHonour.getBacHonourEnum(bac_honour));
		onlineRegistration.setMajor(Major.getMajorEnum(major));

		return onlineRegistration;
	}
	
	public static OnlineRegistration copyOnlineRegistration (OnlineRegistrations onlineRegistrations) {
		OnlineRegistration onlineRegistration = new OnlineRegistration();
		
		onlineRegistration.setMassar_code(onlineRegistrations.getMassar_code());
		onlineRegistration.setAr_first_name(onlineRegistrations.getAr_first_name());
		onlineRegistration.setAr_last_name(onlineRegistrations.getAr_last_name());
		onlineRegistration.setFirst_name(onlineRegistrations.getFirst_name());
		onlineRegistration.setLast_name(onlineRegistrations.getLast_name());
		onlineRegistration.setCin(onlineRegistrations.getCin());
		onlineRegistration.setNationality(onlineRegistrations.getNationality());
		onlineRegistration.setGender(onlineRegistrations.getGender());
		onlineRegistration.setBirthdate(onlineRegistrations.getBirthdate());
		onlineRegistration.setAr_birth_place(onlineRegistrations.getAr_birth_place());
		onlineRegistration.setBirth_place(onlineRegistrations.getBirth_place());
		onlineRegistration.setResidence_town(onlineRegistrations.getResidence_town());
		onlineRegistration.setProvince(onlineRegistrations.getProvince());
		onlineRegistration.setBac_year(onlineRegistrations.getBac_year());
		onlineRegistration.setHigh_school(onlineRegistrations.getHigh_school());
		onlineRegistration.setBac_place(onlineRegistrations.getBac_place());
		onlineRegistration.setAcademy(onlineRegistrations.getAcademy());
		onlineRegistration.setBac_serie(onlineRegistrations.getBac_serie());
		onlineRegistration.setBac_honour(onlineRegistrations.getBac_honour());
		onlineRegistration.setMajor(onlineRegistrations.getMajor());
		
		return onlineRegistration;
	}

}
