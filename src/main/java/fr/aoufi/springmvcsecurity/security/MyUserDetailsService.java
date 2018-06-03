package fr.aoufi.springmvcsecurity.security;

import fr.aoufi.springmvcsecurity.model.User;
import fr.aoufi.springmvcsecurity.security.MyUserPrincipal;
import fr.aoufi.springmvcsecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	
	static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUname(username);
		logger.info("Utilisateur : ", user);
		if ( user.equals(null) ) {
			logger.info("Aucun utilisateur trouvé avec le nom d'utilisateur: " + username);
			throw new UsernameNotFoundException("Aucun utilisateur trouvé avec le nom d'utilisateur: " + username);
		} else {
			return new MyUserPrincipal(user);
		}
	}
}