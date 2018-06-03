package fr.aoufi.springmvcsecurity.security;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.aoufi.springmvcsecurity.model.Role;
import fr.aoufi.springmvcsecurity.model.User;

public class MyUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = -5770481993511453324L;
	
	static final Logger logger = LoggerFactory.getLogger(MyUserPrincipal.class);
	
	private User user;

	public MyUserPrincipal(User user) {
		this.user = user;
	}

	@Override
    public List<GrantedAuthority> getAuthorities(){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();		
		for(Role role : user.getRoles()){
			logger.info("Rôle : ", role);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getType()));
		}
		logger.info("Autorités : ", authorities);
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}