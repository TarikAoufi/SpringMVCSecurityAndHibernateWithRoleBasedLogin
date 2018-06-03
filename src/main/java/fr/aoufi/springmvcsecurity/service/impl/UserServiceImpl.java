package fr.aoufi.springmvcsecurity.service.impl;

import fr.aoufi.springmvcsecurity.dao.UserDao;
import fr.aoufi.springmvcsecurity.model.User;
import fr.aoufi.springmvcsecurity.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findById(int id) {
		return userDao.findById(id);
	}

	public User findByUname(String uname) {
		User user = userDao.findByUname(uname);
		return user;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
	}

	public void updateUser(User user) {
		User entity = userDao.findById(user.getId());
		if(entity!=null){
			entity.setUsername(user.getUsername());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setRoles(user.getRoles());
		}
	}

	public void deleteUserByUname(String uname) {
		userDao.deleteByUname(uname);
	}

	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	public boolean isUserUNAMEUnique(Integer id, String uname) {
		User user = findByUname(uname);
		return user == null || id != null && user.getId() == id;
	}
}