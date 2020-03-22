package fr.aoufi.springmvcsecurity.service;

import fr.aoufi.springmvcsecurity.model.User;
import java.util.List;

public interface UserService {
	
	User findById(int id);

	User findByUserName(String userName);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserByUserName(String userName);

	List<User> findAllUsers();

	boolean isUserUNAMEUnique(Integer id, String userName);
}
