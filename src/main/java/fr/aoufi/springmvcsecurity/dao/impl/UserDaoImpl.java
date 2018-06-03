package fr.aoufi.springmvcsecurity.dao.impl;

import fr.aoufi.springmvcsecurity.dao.UserDao;
import fr.aoufi.springmvcsecurity.dao.impl.AbstractDao;
import fr.aoufi.springmvcsecurity.model.User;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	public User findById(int id) {
		User user = getByKey(id);
		if (user != null) {
			Hibernate.initialize(user.getRoles());
		}
		return user;
	}

	public List<User> findAllUsers() {

		CriteriaQuery<User> criteriaQuery = getCriteriaBuilder().createQuery(User.class);
		Root<User> userRoot = criteriaQuery.from(User.class);
		criteriaQuery.select(userRoot);

		Query<User> query = getSession().createQuery(criteriaQuery);
		List<User> users = query.getResultList();

		// Décommenter les lignes ci-dessous pour récupérer les roles
		// d'utilisateurs,
		// si on veux éventuellement les afficher sur la liste des utilisateurs.
		/*
		 * for(User user : users){ Hibernate.initialize(user.getRoles()); }
		 */
		return users;
	}

	@Override
	public void save(User user) {
		persist(user);
	 // getSession().save(user); // équivaut
	}

	@Override
	public void deleteByUname(String uname) {

		CriteriaQuery<User> criteriaQuery = getCriteriaBuilder().createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		criteriaQuery.where(getCriteriaBuilder().equal(root.get("username"), uname));
		TypedQuery<User> typedQuery = getSession().createQuery(criteriaQuery);
		try {
			User user = typedQuery.getSingleResult();
			delete(user);
		} catch (final NoResultException nre) {

		}
	}

	@Override
	public User findByUname(String uname) {
		logger.info("UNAME : {}", uname);

		CriteriaQuery<User> criteriaQuery = getCriteriaBuilder().createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		criteriaQuery.where(getCriteriaBuilder().equal(root.get("username"), uname));
		TypedQuery<User> typedQuery = getSession().createQuery(criteriaQuery);
		try {
			User user = typedQuery.getSingleResult();
			if (user != null) {
				Hibernate.initialize(user.getRoles());
			}
			return user;
		} catch (final NoResultException nre) {
			return null;
		}
	}

}