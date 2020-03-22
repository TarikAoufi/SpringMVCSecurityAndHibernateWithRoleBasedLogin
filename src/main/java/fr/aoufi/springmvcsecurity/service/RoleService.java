package fr.aoufi.springmvcsecurity.service;

import fr.aoufi.springmvcsecurity.model.Role;
import java.util.List;

public interface RoleService {
	
	Role findById(int id);

	Role findByType(String type);

	List<Role> findAll();
}
