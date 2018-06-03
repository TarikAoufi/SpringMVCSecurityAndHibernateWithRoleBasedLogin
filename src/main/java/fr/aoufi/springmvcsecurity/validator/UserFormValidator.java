package fr.aoufi.springmvcsecurity.validator;

import fr.aoufi.springmvcsecurity.model.User;
import fr.aoufi.springmvcsecurity.service.UserService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {
	
	@Autowired
	UserService userService;
	
	private Pattern pattern;
	
	private Matcher matcher;
	
	// RG1: Le nom et le  prénom doivent contenir au moins 3 caractères, et seulement des lettres!
	private static final String FIRSTNAME_PATTERN = "^[a-zA-Z]{3,}+$";
	private static final String LASTNAME_PATTERN = "^[a-zA-Z]{3,}+$";
	
	// RG2: Le nom d’utilisateur est composé de 3 à 8 caractères alphanumériques et les symboles ( . - _ )
	private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,8}$";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	// RG3: Le mot de passe est compris entre 4 et 8 caractères, contenant au moins une lettre minuscule, une lettre majuscule, 
	//      un chiffre et un caractère spécial de la liste ( _ # $ ! )
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_#$!]).{4,8})";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "registration.notempty.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "registration.notempty.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "registration.notempty.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "registration.notempty.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "registration.notempty.email");
		
		if (!this.userService.isUserUNAMEUnique(user.getId(), user.getUsername())) {
			errors.rejectValue("username", "registration.uniqueness.username");
		}

		if (user.getEmail().length() != 0 && !this.validField(user.getEmail(), EMAIL_PATTERN)) {
			errors.rejectValue("email", "Pattern.user.email");
		}
		
		if (user.getFirstName().length() != 0 && !this.validField(user.getFirstName(), FIRSTNAME_PATTERN)) {
			errors.rejectValue("firstName", "Pattern.user.firstName");
		}

		if (user.getLastName().length() != 0 && !this.validField(user.getLastName(), LASTNAME_PATTERN)) {
			errors.rejectValue("lastName", "Pattern.user.lastName");
		}

		if (user.getUsername().length() != 0 && !this.validField(user.getUsername(), USERNAME_PATTERN)) {
			errors.rejectValue("username", "Pattern.user.username");
		}

		if (user.getPassword().length() != 0
				&& !this.validField(user.getPassword(), PASSWORD_PATTERN)) {
			errors.rejectValue("password", "Pattern.user.password");
		}

		if (user.getRoles().size() == 0) {
			errors.rejectValue("roles", "registration.notempty.roles");
		}

	}

	public boolean validField(String field, String reg) {
		this.pattern = Pattern.compile(reg);
		this.matcher = this.pattern.matcher(field);
		return this.matcher.matches();
	}
}