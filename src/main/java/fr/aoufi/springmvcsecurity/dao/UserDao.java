package fr.aoufi.springmvcsecurity.dao;

import fr.aoufi.springmvcsecurity.model.User;
import java.util.List;

public interface UserDao {
	
	User findById(int arg0);

	User findByUname(String arg0);

	void save(User arg0);

	void deleteByUname(String arg0);

	List<User> findAllUsers();
}