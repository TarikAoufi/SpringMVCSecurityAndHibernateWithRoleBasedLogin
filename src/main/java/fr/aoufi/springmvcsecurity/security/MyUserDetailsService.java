package fr.aoufi.springmvcsecurity.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aoufi.springmvcsecurity.model.User;
import fr.aoufi.springmvcsecurity.service.UserService;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	
	static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userService.findByUserName(username);
		logger.info("Utilisateur : {} ", user);
		if (user == null)
			throw new UsernameNotFoundException("Aucun utilisateur trouvé avec le nom d'utilisateur: " + username);
		else
			return new MyUserPrincipal(user);
	}
}
