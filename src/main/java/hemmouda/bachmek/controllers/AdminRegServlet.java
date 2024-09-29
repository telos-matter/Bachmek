package hemmouda.bachmek.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.business.AcadYearManager;
import hemmouda.bachmek.business.AdminRegManager;
import hemmouda.bachmek.enums.BacHonour;
import hemmouda.bachmek.enums.BacSerie;
import hemmouda.bachmek.enums.Gender;
import hemmouda.bachmek.enums.Major;
import hemmouda.bachmek.models.AcademicStage;
import hemmouda.bachmek.models.AcademicYear;
import hemmouda.bachmek.models.AdministrativeRegistration;
import hemmouda.bachmek.models.OnlineRegistration;
import hemmouda.bachmek.models.OnlineRegistrations;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.ExcelManager;
import hemmouda.bachmek.util.ServletManager;

@WebServlet (urlPatterns= {
		"/adminReg/list", "/adminReg", "/adminReg/create", "/adminReg/create/next", "/adminReg/delete", "/adminReg/activate", "/adminReg/edit", "/adminReg/file", "/adminReg/file/upload", "/adminReg/file/download",
		"/adminReg/onlineReg", "/adminReg/onlineReg/create", "/adminReg/onlineReg/list", "/adminReg/onlineReg/delete",
		"/adminReg/onlineRegs", "/adminReg/onlineRegs/delete"
			})
@MultipartConfig (
		  fileSizeThreshold = 1024 * 1024 * 1,
		  maxFileSize = 1024 * 1024 * 10,
		  maxRequestSize = 1024 * 1024 * 15
		)
public class AdminRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// The Headers must match the order of the validate method of the Manager
	public static final String CREATION_TEMPLATE_NAME = "admin_reg_creation_template";
	public static final String DELETION_TEMPLATE_NAME = "admin_reg_deletion_template";
	public static final String [] CREATION_HEADERS = {"Massar code", "الإسم", "النسب", "First name", "Last name", "CIN", "Nationality", "Gender", "Birthdate", "مكان الولادة", "Birth place", "Residence", "Province", "Bac year", "High school", "Bac place", "Academy", "Bac serie", "Bac honour", "Major", "CNE", "Personal address", "Phone number", "Parents address", "Diplomas", "Has tuition?", "Academic Year", "Academic Stage Sequence"};
	public static final String [] DELETION_HEADERS = {"CNE"};
	public static int CREATION_TEMPLATE_WIDTH = CREATION_HEADERS.length;
	public static int DELETION_TEMPLATE_WIDTH = DELETION_HEADERS.length;
	
    public AdminRegServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/admin_reg/";
		
		if (URN.equals("/adminReg")) {
			
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity(AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			path += "reg_info.jsp";
			request.setAttribute("item", administrativeRegistration);
			
		} else if (URN.equals("/adminReg/list")) {
			
			Boolean hidden = ServletManager.getParameter("hidden", Boolean.class, request);
			if (hidden == null) {
				hidden = true;
			}
			path += "reg_list.jsp";
			request.setAttribute("hidden", hidden);
			if (hidden) {
				request.setAttribute("list", Activatable.filterActive(AdministrativeRegistration.class));
			} else {
				request.setAttribute("list", Manager.selectAll(AdministrativeRegistration.class));
			}
			
		}  else if (URN.equals("/adminReg/activate")) {
			
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity(AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			administrativeRegistration.setIsActive(!administrativeRegistration.getIsActive());
			administrativeRegistration.update();
			ServletManager.setUpdatedSucces(administrativeRegistration.getFullName(), administrativeRegistration.getClass(), request, response);
			return;
			
		}  else if (URN.equals("/adminReg/delete")) {
			
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity(AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			if (Manager.deleteIfPossible(AdministrativeRegistration.class, administrativeRegistration.getId())) {
				ServletManager.setDeletedSucces(administrativeRegistration.getFullName(), administrativeRegistration.getClass(), request, response);
				return;
			} else {
				ServletManager.setDeletedFailure(administrativeRegistration.getFullName(), administrativeRegistration.getClass(), request, response);
				return;
			}
			
		} else if (URN.equals("/adminReg/create")) {
			
			path += "create_reg.jsp";
			request.setAttribute("list", Manager.selectAll(OnlineRegistrations.class));
			
		} else if (URN.equals("/adminReg/onlineRegs/delete")) {
			
			OnlineRegistrations onlineRegistrations = ServletManager.assertEntity(OnlineRegistrations.class, request, response);
			if (onlineRegistrations == null) {
				return;
			}
			onlineRegistrations.delete();
			ServletManager.setDeletedSucces(onlineRegistrations.getFullName(), onlineRegistrations.getClass(), request, response);
			return;
			
		} else if (URN.equals("/adminReg/onlineRegs")) {
			
			OnlineRegistrations onlineRegistrations = ServletManager.assertEntity(OnlineRegistrations.class, request, response);
			if (onlineRegistrations == null) {
				return;
			}
			path += "onlineRegs_info.jsp";
			request.setAttribute("item", onlineRegistrations);
			
		} else if (URN.equals("/adminReg/onlineReg")) {
			
			OnlineRegistration onlineRegistration = ServletManager.assertEntity(OnlineRegistration.class, request, response);
			if (onlineRegistration == null) {
				return;
			}
			path += "onlineReg_info.jsp";
			request.setAttribute("item", onlineRegistration);
			
		} else if (URN.equals("/adminReg/onlineReg/create")) {
		
			path += "create_onlineReg.jsp";
			request.setAttribute("gender_list", Gender.values());
			request.setAttribute("bac_serie_list", BacSerie.values());
			request.setAttribute("bac_honour_list", BacHonour.values());
			request.setAttribute("major_list", Major.getActiveValues());
			request.setAttribute("gender", Gender.values()[0].toString());
			request.setAttribute("bac_serie", BacSerie.values()[0].toString());
			request.setAttribute("bac_honour", BacHonour.values()[0].toString());
			request.setAttribute("major", Major.values()[0].toString());
			
		} else if (URN.equals("/adminReg/create/next")) {
			
			OnlineRegistrations onlineRegistrations = ServletManager.getEntity("onlineRegs_id", OnlineRegistrations.class, request);
			if (onlineRegistrations == null) {
				OnlineRegistration onlineRegistration = ServletManager.assertEntity("onlineReg_id", OnlineRegistration.class, request, response);
				if (onlineRegistration == null) {
					return;
				}
				AcademicYear current_academicYear = AcadYearManager.getEarliestCurrent();
				if (current_academicYear == null) {	
					request.setAttribute("error_message", "First create a valide Academic Year. The Online Reg. has been created.");
					response.sendRedirect(request.getContextPath() +"/acadYear/create");
					return;
				}
				path += "create_reg_next.jsp";
				request.setAttribute("onlineRegistration", onlineRegistration);
				request.setAttribute("acadYear_list", AcadYearManager.getAfterOrEqual(current_academicYear));
				request.setAttribute("acadStage_list", Activatable.filterActive(onlineRegistration.getMajor().getAcademicStages()));
			} else {
				OnlineRegistration onlineRegistration = AdminRegManager.copyOnlineRegistration(onlineRegistrations);
				if (Manager.selectUnique(OnlineRegistration.class, "massar_code", onlineRegistration.getMassar_code()) != null) {
					path += "create_reg.jsp";
					request.setAttribute("error_message", "The Massar code of the selected Online Registrations already exists");
					request.setAttribute("list", Manager.selectAll(OnlineRegistrations.class));
				} else if (Manager.selectUnique(OnlineRegistration.class, "cin", onlineRegistration.getCin()) != null) {
					path += "create_reg.jsp";
					request.setAttribute("error_message", "The CIN of the selected Online Registrations already exists");
					request.setAttribute("list", Manager.selectAll(OnlineRegistrations.class));
				} else {
					onlineRegistration.insert();
					onlineRegistrations.delete(); // No need to check as nothing deprends on these
					AcademicYear current_academicYear = AcadYearManager.getEarliestCurrent();
					if (current_academicYear == null) {	
						request.setAttribute("error_message", "First create a valide Acad. Year. The OnlineRegs. has been converted to an OnlineReg..");
						response.sendRedirect(request.getContextPath() +"/acadYear/create");
						return;
					}
					path += "create_reg_next.jsp";
					request.setAttribute("onlineRegistration", onlineRegistration);
					request.setAttribute("acadYear_list", AcadYearManager.getAfterOrEqual(current_academicYear));
					request.setAttribute("acadStage_list", Activatable.filterActive(onlineRegistration.getMajor().getAcademicStages()));
				}
			}
			
		} else if (URN.equals("/adminReg/onlineReg/list")) {
			
			path += "onlineReg_list.jsp";
			request.setAttribute("list", Manager.selectMultiple(OnlineRegistration.class, "SELECT * FROM onlineRegistration WHERE id NOT IN (SELECT onlineRegistration_id FROM administrativeRegistration);"));
			
		} else if (URN.equals("/adminReg/onlineReg/delete")) {
			
			OnlineRegistration onlineRegistration = ServletManager.assertEntity(OnlineRegistration.class, request, response);
			if (onlineRegistration == null) {
				return;
			}
			if (Manager.deleteIfPossible(OnlineRegistration.class, onlineRegistration.getId())) {
				ServletManager.setDeletedSucces(onlineRegistration.getFullName(), onlineRegistration.getClass(), request, response);
				return;
			} else {
				ServletManager.setDeletedFailure(onlineRegistration.getFullName(), onlineRegistration.getClass(), request, response);
				return;
			}
			
		} else if (URN.equals("/adminReg/edit")) {
			
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity(AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			path += "edit_reg.jsp";
			request.setAttribute("item", administrativeRegistration);
			request.setAttribute("gender_list", Gender.values());
			request.setAttribute("bac_serie_list", BacSerie.values());
			request.setAttribute("bac_honour_list", BacHonour.values());
			
		} else if (URN.equals("/adminReg/file")) {
			
			path += "reg_file.jsp";
			
		} else if (URN.equals("/adminReg/file/download")) {
			
			Boolean create = ServletManager.assertParameter("create", Boolean.class, request, response);
			if (create == null) {
				return;
			}
			AdminRegManager.writeDownloadList(create, response);
			return;
			
		}

		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String URN = (String) request.getAttribute("URN");
		String path = "/WEB-INF/jsps/admin_reg/";
		
		if (URN.equals("/adminReg/onlineReg/create")) {
			
			OnlineRegistration onlineRegistration = AdminRegManager.validateOnlineRegistration(request.getParameter("massar_code"), request.getParameter("ar_first_name"), request.getParameter("ar_last_name"), request.getParameter("first_name"), request.getParameter("last_name"), request.getParameter("cin"), request.getParameter("nationality"), request.getParameter("gender"), request.getParameter("birthdate"), request.getParameter("ar_birth_place"), request.getParameter("birth_place"), request.getParameter("residence_town"), request.getParameter("province"), request.getParameter("bac_year"), request.getParameter("high_school"), request.getParameter("bac_place"), request.getParameter("academy"), request.getParameter("bac_serie"), request.getParameter("bac_honour"), request.getParameter("major"));
			if (onlineRegistration == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (Manager.selectUnique(OnlineRegistration.class, "massar_code", onlineRegistration.getMassar_code()) != null) {
				path += "create_onlineReg.jsp";
				request.setAttribute("gender_list", Gender.values());
				request.setAttribute("bac_serie_list", BacSerie.values());
				request.setAttribute("bac_honour_list", BacHonour.values());
				request.setAttribute("major_list", Major.getActiveValues());
				request.setAttribute("error_message", "This Massar code already exist.");
				request.setAttribute("massar_code", request.getParameter("massar_code"));
				request.setAttribute("ar_first_name", request.getParameter("ar_first_name"));
				request.setAttribute("ar_last_name", request.getParameter("ar_last_name"));
				request.setAttribute("first_name", request.getParameter("first_name"));
				request.setAttribute("last_name", request.getParameter("last_name"));
				request.setAttribute("cin", request.getParameter("cin"));
				request.setAttribute("nationality", request.getParameter("nationality"));
				request.setAttribute("gender", request.getParameter("gender"));
				request.setAttribute("birthdate", request.getParameter("birthdate"));
				request.setAttribute("ar_birth_place", request.getParameter("ar_birth_place"));
				request.setAttribute("birth_place", request.getParameter("birth_place"));
				request.setAttribute("residence_town", request.getParameter("residence_town"));
				request.setAttribute("province", request.getParameter("province"));
				request.setAttribute("bac_year", request.getParameter("bac_year"));
				request.setAttribute("high_school", request.getParameter("high_school"));
				request.setAttribute("bac_place", request.getParameter("bac_place"));
				request.setAttribute("academy", request.getParameter("academy"));
				request.setAttribute("bac_serie", request.getParameter("bac_serie"));
				request.setAttribute("bac_honour", request.getParameter("bac_honour"));
				request.setAttribute("major", request.getParameter("major"));
			} else if (onlineRegistration.getCin()!= null && Manager.selectUnique(OnlineRegistration.class, "cin", onlineRegistration.getCin()) != null) {
				path += "create_onlineReg.jsp";
				request.setAttribute("gender_list", Gender.values());
				request.setAttribute("bac_serie_list", BacSerie.values());
				request.setAttribute("bac_honour_list", BacHonour.values());
				request.setAttribute("major_list", Major.getActiveValues());
				request.setAttribute("error_message", "This CIN already exist.");
				request.setAttribute("massar_code", request.getParameter("massar_code"));
				request.setAttribute("ar_first_name", request.getParameter("ar_first_name"));
				request.setAttribute("ar_last_name", request.getParameter("ar_last_name"));
				request.setAttribute("first_name", request.getParameter("first_name"));
				request.setAttribute("last_name", request.getParameter("last_name"));
				request.setAttribute("cin", request.getParameter("cin"));
				request.setAttribute("nationality", request.getParameter("nationality"));
				request.setAttribute("gender", request.getParameter("gender"));
				request.setAttribute("birthdate", request.getParameter("birthdate"));
				request.setAttribute("ar_birth_place", request.getParameter("ar_birth_place"));
				request.setAttribute("birth_place", request.getParameter("birth_place"));
				request.setAttribute("residence_town", request.getParameter("residence_town"));
				request.setAttribute("province", request.getParameter("province"));
				request.setAttribute("bac_year", request.getParameter("bac_year"));
				request.setAttribute("high_school", request.getParameter("high_school"));
				request.setAttribute("bac_place", request.getParameter("bac_place"));
				request.setAttribute("academy", request.getParameter("academy"));
				request.setAttribute("bac_serie", request.getParameter("bac_serie"));
				request.setAttribute("bac_honour", request.getParameter("bac_honour"));
				request.setAttribute("major", request.getParameter("major"));
			} else {
				onlineRegistration.insert();
				response.sendRedirect(request.getContextPath() +"/adminReg/create/next?onlineReg_id=" +onlineRegistration.getId());
				return;
			}
			
		} else if (URN.equals("/adminReg/create/next")) {
			
			AcademicYear academicYear = ServletManager.assertEntity("academicYear_id", AcademicYear.class, request, response);
			if (academicYear == null) {
				return;
			}
			AcademicStage academicStage = ServletManager.assertEntity("academicStage_id", AcademicStage.class, request, response);
			if (academicStage == null) {
				return;
			}
			OnlineRegistration onlineRegistration = ServletManager.assertEntity("onlineRegistration_id", OnlineRegistration.class, request, response);
			if (onlineRegistration == null) {
				return;
			}
			AdministrativeRegistration administrativeRegistration = AdminRegManager.validateAdministrativeRegistration(request.getParameter("cne"), request.getParameter("personal_address"), request.getParameter("phone_number"), request.getParameter("parents_address"), request.getParameter("diplomas"), request.getParameter("isTuition"), academicYear, academicStage, onlineRegistration);
			if (administrativeRegistration == null) {
				ServletManager.setInvalideFormError(request, response);
				return;
			}
			if (Manager.selectUnique(AdministrativeRegistration.class, "cne", administrativeRegistration.getCne()) == null) {
				AdminRegManager.createAdministrativeRegistration(administrativeRegistration);
				ServletManager.setCreatedSucces(administrativeRegistration.getFullName(), AdministrativeRegistration.class, request, response);
				return;
			}
			AcademicYear current_academicYear = AcadYearManager.getEarliestCurrent();
			if (current_academicYear == null) {	
				ServletManager.setCorruptedData(request, response);
				return;
			}
			List <AcademicYear> acadYear_list = AcadYearManager.getAfterOrEqual(current_academicYear);
			path += "create_reg_next.jsp";
			request.setAttribute("error_message", "This CNE already exists.");
			request.setAttribute("cne", request.getParameter("cne"));
			request.setAttribute("personal_address", request.getParameter("personal_address"));
			request.setAttribute("phone_number", request.getParameter("phone_number"));
			request.setAttribute("parents_address", request.getParameter("parents_address"));
			request.setAttribute("diplomas", request.getParameter("diplomas"));
			request.setAttribute("isTuition", request.getParameter("isTuition"));
			request.setAttribute("onlineRegistration", onlineRegistration);
			request.setAttribute("acadYear_list", acadYear_list);
			request.setAttribute("acadStage_list",  Activatable.filterActive(onlineRegistration.getMajor().getAcademicStages()));
			
		} else if (URN.equals("/adminReg/edit")) {
			
			AdministrativeRegistration administrativeRegistration = ServletManager.assertEntity(AdministrativeRegistration.class, request, response);
			if (administrativeRegistration == null) {
				return;
			}
			OnlineRegistration validated_onlineRegistration = AdminRegManager.validateOnlineRegistration(request.getParameter("massar_code"), request.getParameter("ar_first_name"), request.getParameter("ar_last_name"), request.getParameter("first_name"), request.getParameter("last_name"), request.getParameter("cin"), request.getParameter("nationality"), request.getParameter("gender"), request.getParameter("birthdate"), request.getParameter("ar_birth_place"), request.getParameter("birth_place"), request.getParameter("residence_town"), request.getParameter("province"), request.getParameter("bac_year"), request.getParameter("high_school"), request.getParameter("bac_place"), request.getParameter("academy"), request.getParameter("bac_serie"), request.getParameter("bac_honour"), administrativeRegistration.getOnlineRegistration().getMajor().getMajor().toString());
			if (validated_onlineRegistration == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (! administrativeRegistration.getOnlineRegistration().getMassar_code().equals(validated_onlineRegistration.getMassar_code()) && Manager.selectUnique(OnlineRegistration.class, "massar_code", validated_onlineRegistration.getMassar_code()) != null) {
				path += "edit_reg.jsp";
				request.setAttribute("error_message", "This Massar code already exists.");
				request.setAttribute("item", administrativeRegistration);
				request.setAttribute("gender_list", Gender.values());
				request.setAttribute("bac_serie_list", BacSerie.values());
				request.setAttribute("bac_honour_list", BacHonour.values());
			} else if (validated_onlineRegistration.getCin() != null && !validated_onlineRegistration.getCin().equals(administrativeRegistration.getOnlineRegistration().getCin()) && Manager.selectUnique(OnlineRegistration.class, "cin", validated_onlineRegistration.getCin()) != null) {
				path += "edit_reg.jsp";
				request.setAttribute("error_message", "This CIN already exists.");
				request.setAttribute("item", administrativeRegistration);
				request.setAttribute("gender_list", Gender.values());
				request.setAttribute("bac_serie_list", BacSerie.values());
				request.setAttribute("bac_honour_list", BacHonour.values());
			} else {
				validated_onlineRegistration.setId(administrativeRegistration.getOnlineRegistration().getId());
				validated_onlineRegistration.setRegistration_date(administrativeRegistration.getOnlineRegistration().getRegistration_date());
				AdministrativeRegistration validated_administrativeRegistration = AdminRegManager.validateAdministrativeRegistration(request.getParameter("cne"), request.getParameter("personal_address"), request.getParameter("phone_number"), request.getParameter("parents_address"), request.getParameter("diplomas"), request.getParameter("isTuition"), administrativeRegistration.getAcademicYear(), administrativeRegistration.getAcademicStage(), validated_onlineRegistration);
				if (validated_administrativeRegistration == null) {
					ServletManager.setCorruptedData(request, response);
					return;
				}
				if (!validated_administrativeRegistration.getCne().equals(administrativeRegistration.getCne()) && Manager.selectUnique(AdministrativeRegistration.class, "cne", validated_administrativeRegistration.getCne()) != null) {
					path += "edit_reg.jsp";
					request.setAttribute("error_message", "This CNE already exists.");
					request.setAttribute("item", administrativeRegistration);
					request.setAttribute("gender_list", Gender.values());
					request.setAttribute("bac_serie_list", BacSerie.values());
					request.setAttribute("bac_honour_list", BacHonour.values());
				} else {
					validated_administrativeRegistration.setId(administrativeRegistration.getId());
					validated_administrativeRegistration.setIsActive(administrativeRegistration.getIsActive());
					validated_administrativeRegistration.setPedagogicalRegistations(administrativeRegistration.getPedagogicalRegistations());
					validated_administrativeRegistration.setRegistration_date(administrativeRegistration.getRegistration_date());
					validated_administrativeRegistration.update();
					ServletManager.setUpdatedSucces(validated_administrativeRegistration.getFullName(), AdministrativeRegistration.class, request, response);
					return;
				}
			}
			
		} else if (URN.equals("/adminReg/file/upload")) {
			
			Boolean create = ServletManager.assertParameter("create", Boolean.class, request, response);
			if (create == null) {
				return;
			}
			String file_name = ServletManager.saveExcelFile(request);
			if (file_name == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			List <String []> data = ExcelManager.read(file_name);
			ExcelManager.FileManager.deleteFile(file_name);
			if (data == null) {
				ServletManager.setCorruptedData(request, response);
				return;
			}
			if (create) {
				if (data.size() == 0) {
					path += "reg_file.jsp";
					request.setAttribute("error_message", "The uploaded sheet is empty.");
				} else if (data.get(0).length != CREATION_TEMPLATE_WIDTH) {
					path += "reg_file.jsp";
					request.setAttribute("error_message", "The uploaded sheet doesn't match the template.");
				} else {
					int count = AdminRegManager.validateExcelCreation(data);
					if (count == 0) {
						path += "reg_file.jsp";
						request.setAttribute("error_message", "The uploaded sheet has missing or contradictory data.");
					} else {
						ServletManager.setCreatedSucces("" +count +" Admin. Reg.(s)", null, request, response);
						return;
					}
				}
			} else {
				if (data.size() == 0) {
					path += "reg_file.jsp";
					request.setAttribute("error_message", "The uploaded sheet is empty.");
				} else if (data.get(0).length != DELETION_TEMPLATE_WIDTH) {
					path += "reg_file.jsp";
					request.setAttribute("error_message", "The uploaded sheet doesn't match the template.");
				} else {
					int count = AdminRegManager.validateExcelDeletion(data);
					if (count == 0) {
						path += "reg_file.jsp";
						request.setAttribute("error_message", "The uploaded sheet has missing or contradictory data.");
					} else if (count == -100) {
						ServletManager.setDeletedFailure("Not all Admin. Reg.(s)", null, request, response);
						return;
					} else {
						ServletManager.setDeletedSucces("" +count +" Admin. Reg.(s)", null, request, response);
						return;
					}
				}	
			}
			
		}
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
