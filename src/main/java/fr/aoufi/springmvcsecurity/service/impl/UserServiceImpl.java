package fr.aoufi.springmvcsecurity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.aoufi.springmvcsecurity.dao.UserDao;
import fr.aoufi.springmvcsecurity.model.User;
import fr.aoufi.springmvcsecurity.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User findById(int id) {
		return userDao.findById(id);
	}

	public User findByUserName(String uname) {
		return userDao.findByUserName(uname);
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
	}

	public void updateUser(User user) {
		User entity = userDao.findById(user.getId());
		if(entity!=null){
			entity.setUserName(user.getUserName());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setRoles(user.getRoles());
		}
	}

	public void deleteUserByUserName(String userName) {
		userDao.deleteByUserName(userName);
	}

	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	public boolean isUserUNAMEUnique(Integer id, String uname) {
		User user = findByUserName(uname);
		return user == null || id != null && user.getId().equals(id);
	}
}
