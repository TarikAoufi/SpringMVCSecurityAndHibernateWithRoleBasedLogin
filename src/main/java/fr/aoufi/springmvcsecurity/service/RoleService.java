package fr.aoufi.springmvcsecurity.service;

import fr.aoufi.springmvcsecurity.model.Role;
import java.util.List;

public interface RoleService {
	
	Role findById(int arg0);

	Role findByType(String arg0);

	List<Role> findAll();
}