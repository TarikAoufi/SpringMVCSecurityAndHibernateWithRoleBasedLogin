package fr.aoufi.springmvcsecurity.service.impl;

import fr.aoufi.springmvcsecurity.dao.RoleDao;
import fr.aoufi.springmvcsecurity.model.Role;
import fr.aoufi.springmvcsecurity.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Override
	public Role findById(int id) {
		return roleDao.findById(id);
	}

	@Override
	public Role findByType(String type) {
		return roleDao.findByType(type);
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

}
