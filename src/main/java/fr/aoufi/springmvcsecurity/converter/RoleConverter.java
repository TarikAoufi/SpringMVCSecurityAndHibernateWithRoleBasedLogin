package fr.aoufi.springmvcsecurity.converter;

import fr.aoufi.springmvcsecurity.model.Role;
import fr.aoufi.springmvcsecurity.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<Object, Role> {
	
	static final Logger logger = LoggerFactory.getLogger(RoleConverter.class);
	
	@Autowired
	RoleService roleService;
	
	/**
	 * Converter<S,T>: Un convertisseur convertit un objet source de type S en cible de type T.
	 * Une classe de conversion utilisée dans les vues pour mapper les ID aux objets Role 
	 */
	public Role convert(Object element) {
		Integer id = Integer.parseInt((String)element);
		Role role= roleService.findById(id);
		logger.info("Rôle : ", role);
		return role;
	}
}