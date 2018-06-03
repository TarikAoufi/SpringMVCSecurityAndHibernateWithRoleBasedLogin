package fr.aoufi.springmvcsecurity.service;

import fr.aoufi.springmvcsecurity.model.User;
import java.util.List;

public interface UserService {
	
	User findById(int arg0);

	User findByUname(String arg0);

	void saveUser(User arg0);

	void updateUser(User arg0);

	void deleteUserByUname(String arg0);

	List<User> findAllUsers();

	boolean isUserUNAMEUnique(Integer arg0, String arg1);
}