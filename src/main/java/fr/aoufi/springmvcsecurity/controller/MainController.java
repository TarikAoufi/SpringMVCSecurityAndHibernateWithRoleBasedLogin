package fr.aoufi.springmvcsecurity.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.aoufi.springmvcsecurity.model.Role;
import fr.aoufi.springmvcsecurity.model.User;
import fr.aoufi.springmvcsecurity.service.RoleService;
import fr.aoufi.springmvcsecurity.service.UserService;
import fr.aoufi.springmvcsecurity.validator.UserFormValidator;

@Controller
@SessionAttributes({"user_roles"})
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	static final String LOGGEDONUSER = "loggedonuser";
	static final String REGISTRATION = "registration";
	
	@Autowired
	UserFormValidator userFormValidator;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	MessageSource messageSource;
	
	/**
	 * Pour stocker les informations de connexion persistantes 
	 * dans une table de la base de données ('PERSISTENT_LOGINS') entre les sessions.
	 */
	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
	
	/**
	 * Pour évaluer les jetons d'authentification
	 */
	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;
	
	/**
	 * Pour contrôler les champs du formulaire d'inscription
	 * @InitBinder: Permet d'dentifier les méthodes qui initialisent le WebDataBinder.
	 * WebDataBinder: est un DataBinder qui lie le paramètre de requête aux objets JavaBean.
	 * Il fournit des méthodes pour assigner nos classes de validateurs. 
	 * En utilisant les méthodes addValidators () et setValidator (), nous pouvons 
	 * assigner nos instances de validateurs.
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}

	@GetMapping(value = {"/", "/welcome"})
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "C'est la page d'accueil!");
		return "welcomePage";
	}
	
	/**
	 * Cette méthode permet d'ajouter un nouvel utilisateur.
	 */
	@GetMapping(value = {"/newuser", "/userregist"})
	public String newUser(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute(LOGGEDONUSER, getPrincipal());
		return REGISTRATION;
	}
	
	/**
	 * Cette méthode sera appelée lors de la soumission du formulaire, en gérant la requête POST pour 
	 * enregistrer l'utilisateur dans la bd. Il valide également l'entrée de l'utilisateur
	 */
	@PostMapping(value = {"/newuser", "/userregist"})
	public String saveUser(@Valid User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			logger.info("Retour à la page registration.jsp");
			return REGISTRATION;
		} else {
			userService.saveUser(user);
			model.addAttribute("success", messageSource.getMessage("", new String[]{user.getUserName()}, Locale.getDefault()) + " "
							+ user.getFirstName() + " " + user.getLastName() + " ");
			model.addAttribute(LOGGEDONUSER, getPrincipal());
			return "registrationsuccess";
		}
	}
	
	/**
	 * Cette méthode permet de mettre à jour un utilisateur existant.
	 */
	@GetMapping(value = {"/edit-user-{username}"})
	public String editUser(@PathVariable String username, ModelMap model) {
		User user = userService.findByUserName(username);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		model.addAttribute(LOGGEDONUSER, getPrincipal());
		return REGISTRATION;
	}
	
	/**
	 * Cette méthode sera appelée lors de la soumission du formulaire, en gérant la requête POST pour
	 * mettre à jour l'utilisateur dans la bd. Il valide également l'entrée de l'utilisateur
	 */
	@PostMapping(value = {"/edit-user-{username}"})
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable String username) {
		if (result.hasErrors()) {
			return REGISTRATION;
		} else {			
			/*
		    //décommenter ce bloc pour mettre à jour le "username".
			if(!userService.isUserUNAMEUnique(user.getId(), user.getUserName())){
				FieldError unameError = new FieldError("user", "userName", 
												messageSource.getMessage("registration.uniqueness.username", new String[]{user.getUserName()}, 
												Locale.getDefault())); 
			    result.addError(unameError); 
			    return "registration"; 
			}
			*/			
			userService.updateUser(user);
			model.addAttribute("success", messageSource.getMessage("", new String[]{user.getUserName()}, Locale.getDefault()) + " "
							+ user.getFirstName() + " " + user.getLastName() + " ");
			model.addAttribute(LOGGEDONUSER, getPrincipal());
			return "updatesuccess";
		}
	}
	
	/**
	 * Cette méthode permet de supprimer un utilisateur par son username
	 */
	@GetMapping(value = {"/delete-user-{username}"})
	public String deleteUser(@PathVariable String username) {
		userService.deleteUserByUserName(username);
		return "redirect:/list";
	}
	
	/**
	 * Lister tous les utilisateurs
	 */
	@GetMapping(value = {"/list"})
	public String listUsers(ModelMap model) {
		List<User>  users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute(LOGGEDONUSER, getPrincipal());
		return "userslist";
	}
	
	/**
	 * Fournir la liste des rôles d'utilisateurs aux vues
	 */
	@ModelAttribute("roleList")
	public List<Role> initializeUserRoles() {
		return roleService.findAll();
	}
	
	/**
	 * Cette méthode gère les requêtes GET de connexion. 
	 * Si les utilisateurs, déjà connectés, essayent d'accéder à nouveau à la
	 * page de connexion, ils seront redirigés vers la page qui affiche la liste des utilisateurs.
	 */
	@GetMapping(value = {"/login"})
	public String loginPage() {
		// Utilisateur déjà connecté ?
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			return "redirect:/list";
		}
	}
	
	/**
	 * Gère les requêtes de déconnexion
	 */
	@GetMapping(value = {"/logout"})
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			persistentTokenBasedRememberMeServices.logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication((null) );
		}
		return "redirect:/login?logout";
	}
	
	/**
	 * Redirection lors d'un accès refusé
	 */
	@GetMapping(value = {"/Access_Denied"})
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute(LOGGEDONUSER, getPrincipal());
		return "accessDenied";
	}
	
	/**
	 * Obtenir le principal [username] de l'utilisateur actuellement connecté.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	/**
	 * Renvoie true si l'utilisateur est déjà authentifié, sinon false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}
}
