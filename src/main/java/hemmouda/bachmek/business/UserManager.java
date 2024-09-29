package hemmouda.bachmek.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hemmouda.bachmek.DAO.Manager;
import hemmouda.bachmek.enums.Gender;
import hemmouda.bachmek.models.GenderEnum;
import hemmouda.bachmek.models.User;
import hemmouda.bachmek.models.interfaces.Activatable;
import hemmouda.bachmek.util.RegexManager;
import hemmouda.bachmek.util.StringManager;

public class UserManager {
	
	private static final char [] CHARACTERS = {
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
				'`', '\"', '\'', ',', '/', '!', '#', '$', '%', '&', '(', ')', '*', '+', '-', '.', ':', ';','<' ,'=' ,'>' ,'?' ,'@' ,'[', ']', '^', '_', '{', '|', '}', '~'
			};	
	private static final int OFFSET = 13;
	private static final String SALT = "salt";
	
	public static User validateUser (String username, String password, String email, String first_name, String last_name, String gender, String birthdate, String phone_number, String personal_address, String cin) {
		if (RegexManager.isEmpty(email)) {
			email = null;
		}
		if (RegexManager.isEmpty(birthdate)){
			birthdate =  null;
		}
		if (RegexManager.isEmpty(phone_number)) {
			phone_number = null;
		}
		if (RegexManager.isEmpty(personal_address)) {
			personal_address = null;
		}
		if (RegexManager.isEmpty(cin)) {
			cin = null;
		}
		
		if (RegexManager.notMatches("^([A-Za-z0-9\\-_]{3,32})$", username)) {
			return null;
		}
		if (RegexManager.notMatches("^([A-Za-z0-9`\"',/!#\\$%&\\(\\)\\*\\+\\-\\.:;<=>\\?@\\[\\]\\^_\\{\\|\\}~]{4,64})$", password)) {
			return null;
		}
		if (email != null && RegexManager.notLength(2, 64, email)) { //Could do with an email regex check
			return null;
		}
		if (RegexManager.notMatches("^([A-Za-z\s]{2,64})$", first_name)) {
			return null;		
		}
		if (RegexManager.notMatches("^([A-Za-z\s]{2,64})$", last_name)) {
			return null;		
		}
		if (Gender.hasNotValue(gender)) {
			return null;
		}
		if (birthdate != null && RegexManager.notMatches("^(\\d{4}\\-\\d{2}\\-\\d{2})$", birthdate)){
			return null;
		}
		if (personal_address != null && RegexManager.notLength(2, 128, personal_address)) {
			return null;
		}
		if (phone_number != null && RegexManager.notMatches("^([0-9]{9,12})$", phone_number)) {
			return null;
		}
		if (cin != null  && RegexManager.notMatches("^([A-Z][0-9]{4,15})$", cin)) {
			return null;
		}
		
		User user = new User ();
		user.setUsername(username);
		user.setPassword(encrypt(password));
		user.setEmail(email);
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setGender(Gender.getGenderEnum(gender));
		user.setBirthdate(StringManager.parseDate(birthdate));
		user.setPhone_number(phone_number);
		user.setPersonal_address(personal_address);
		user.setCin(cin);

		return user;
	}
	
	public static boolean validatePassword (String password) {
		return RegexManager.matches("^([A-Za-z0-9`\"',/!#\\$%&\\(\\)\\*\\+\\-\\.:;<=>\\?@\\[\\]\\^_\\{\\|\\}~]{4,64})$", password);
	}
	
	public static boolean validateInstructorDiscipline (String discipline) {
		return (RegexManager.isEmpty(discipline) || RegexManager.length(2, 64, discipline));
	}
	
	public static boolean refuteUserLogin (String username, String password) {
		if (RegexManager.notMatches("^([A-Za-z0-9\\-_]{3,32})$", username)) {
			return true;
		}
		if (RegexManager.notMatches("^([A-Za-z0-9`\"',/!#\\$%&\\(\\)\\*\\+\\-\\.:;<=>\\?@\\[\\]\\^_\\{\\|\\}~]{4,64})$", password)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return a user with the specified username
	 * <p> If the username already exists then it creates it with
	 * usernamex ..
	 * @implSpec It assumes that the username is valid
	 * @implSpec The password is the same as the username
	 */
	public static User createUser (String username, String first_name, String last_name, GenderEnum gender){
		int i = 0;
		String valid_username = username;
		while (Manager.selectUnique(User.class, "username", valid_username) != null) {
			valid_username = username +(i++);
		}
		User user = new User ();
		user.setUsername(valid_username);
		user.setPassword(encrypt(username));
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setGender(gender);
		user.insert();
		return user;
	}
	
	public static User authentificate (String username, String password) {
		User user = exist(username);
		if (user != null && user.getIsActive() && decrypt(user.getPassword()).equals(password)) {
			return user;
		} else {
			return null;
		}
	}
	
	public static User exist (String username) {
		return Manager.selectUnique(User.class, "username", username);
	}
	
	@SuppressWarnings("unchecked")
	public static Collection <User> getFilteredUsers (Role role) {
		return (Collection <User>) Activatable.filterActive(getUsers(role));
	}
	
	public static List <User> getUsers (Role role) {
		List <User> list = new ArrayList <> ();
		for (User user : Manager.selectAll(User.class)) {
			if (getRole (user) == role) {
				list.add(user);
			}
		}
		return list;
	}
	
	public static Role getRole (User user) {
		if (user.getStudent() != null) {
			return Role.STUDENT;
		} else if (user.getInstructor() != null) {
			return Role.INSTRUCTOR;
		} else if (user.getAdministrator() != null) {
			return Role.ADMINISTRATOR;
		} else {
			return null;
		}
	}
	
	public static String encrypt (String decrypted_password) {
		decrypted_password = decrypted_password.concat(SALT);
		
		StringBuilder temp = new StringBuilder(decrypted_password);
		for (int i = 0; i < temp.length(); i++) {
			temp.setCharAt(i, getOffsetChar(temp.charAt(i), i +OFFSET));	
		}
		
		StringBuilder encrypted_password = new StringBuilder(temp.length()*2);
		for (int i = 0; i < encrypted_password.capacity(); i++) {
			if (i%2 == 0) {
				encrypted_password.append(temp.charAt(i/2));
			} else {
				encrypted_password.append(getRandomChar());
			}
		}
		
		return encrypted_password.toString();
	}
	
	public static String decrypt (String encrypted_password) {
		StringBuilder decrypted_password = new StringBuilder(encrypted_password.length()/2);
		
		for (int i = 0; i < encrypted_password.length(); i++) {
			if (i%2 == 0) {
				decrypted_password.append(encrypted_password.charAt(i));
			}
		}
		
		for (int i = 0; i < decrypted_password.length(); i++) {
			decrypted_password.setCharAt(i, getOffsetChar(decrypted_password.charAt(i), -i -OFFSET));	
		}
		
		decrypted_password.replace(decrypted_password.lastIndexOf(SALT), decrypted_password.length(), "");
		
		return decrypted_password.toString();
	}
	
	private static char getOffsetChar (char c, int offset) {
		int index = -1;
		for (int i = 0; i < CHARACTERS.length; i++) {
			if (CHARACTERS[i] == c) {
				index  = i;
				break;
			}
		}
		index += offset;
		
		index %= CHARACTERS.length;
		if (index < 0) {
			return CHARACTERS [CHARACTERS.length +index];
		} else {
			return CHARACTERS [index];
		}
	}
	
	private static char getRandomChar () {
		return CHARACTERS [(int) (Math.random()*(CHARACTERS.length))];
	}
	
	public static enum Role {
		STUDENT,
		INSTRUCTOR,
		ADMINISTRATOR;
		
		public static Role getRole (String name) {
			try {
				return (name == null) ? null : Role.valueOf(name.toUpperCase());
			} catch (IllegalArgumentException exception) {
				return null;
			}
		}
	}

}
