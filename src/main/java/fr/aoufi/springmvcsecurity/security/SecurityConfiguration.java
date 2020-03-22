package fr.aoufi.springmvcsecurity.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("myUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	PersistentTokenRepository persistentTokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Exécution de configureGlobalSecurity() - SecurityConfiguration");
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		return new MyAuthenticationSuccessHandler();
	}
	
	/**
	 * WebSecurityConfigurerAdapter fournie une configuration par défaut 
	 * dans la méthode configure(HttpSecurity http).
	 * 
	 * L'accès aux URL est régi comme suit:
	 * '/' '/welcome' & '/list': accessible à tous
	 * '/newuser' & '/delete-user-*': Accessible uniquement à Admin
	 * '/edit-user-*': Accessible à Admin & DBA
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/welcome", "/login", "/logout")
				.permitAll();
		
		// Pour tous les rôles: Lister tous les utilisateurs
		http.authorizeRequests().antMatchers("/list")
				.access("hasAnyRole('USER', 'ADMIN', 'DBA')");
		
		// Pour Admin seulement: Suppression et création d'un utilisateur
		http.authorizeRequests().antMatchers("/newuser/**", "/delete-user-*")
				.access("hasRole('ADMIN')");
		
		// Pour DBA et Admin: Modification d'un compte utilisateur 
		http.authorizeRequests().antMatchers("/edit-user-*")
				.access("hasAnyRole('ADMIN', 'DBA')");
		
		http
			.authorizeRequests()
				.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.defaultSuccessUrl("/list")
				.usernameParameter("username")
				.passwordParameter("password")
				.failureUrl("/login?error=true")
                .successHandler(new MyAuthenticationSuccessHandler())	               
				.and()
			.rememberMe()
				.rememberMeParameter("remember-me")
			.tokenRepository(persistentTokenRepository)
				.tokenValiditySeconds(1800) 
				.and().csrf().and().exceptionHandling()
			.accessDeniedPage("/Access_Denied");
		
		http.sessionManagement().sessionFixation().migrateSession();
		
		http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired=true");
	}
	
	/**
	 * Cette méthode permet de crypter le mot de passe dans la base de données, 
	 * on utilisant la classe BCryptPasswordEncoder. (voir la démo - package runtime)
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Puisque nous stockons les informations d'identification dans la base de données, 
	 * la configuration de DaoAuthenticationProvider avec UserDetailsService serait utile.
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return authenticationProvider;
	}
	
	/**
	 * Cette méthode fournie la fonctionnalité "RememberMe", qui permettra d'identifier l'utilisateur à travers plusieurs sessions. 
	 * "RememberMe" ne démarre qu'après la fin de la session (avec un délai d'inactivité). 
	 * 
	 * Et pour garder la trace des données de jetons dans la base de données, 
	 * nous avons configuré une implémentation PersistentTokenRepository.
	 * @return
	 */
	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		return new PersistentTokenBasedRememberMeServices("remember-me", userDetailsService, persistentTokenRepository);
	}
	
	/**
	 * AuthenticationTrustResolver: Indique si le jeton d'authentification transmis 
	 * représente un utilisateur anonyme. AuthenticationTrustResolverImpl et son implémentation correspondante.  
	 * Cette interface fournit une méthode isAnonymous (Authentication), 
	 * qui permet aux classes intéressées de prendre en compte ce type particulier d'état d'authentification. 
	 * @return
	 */
	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}
}
