package fr.aoufi.springmvcsecurity.dao;

import fr.aoufi.springmvcsecurity.model.Role;
import java.util.List;

public interface RoleDao {
	
	Role findById(int arg0);

	Role findByType(String arg0);

	List<Role> findAll();
}