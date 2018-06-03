package fr.aoufi.springmvcsecurity.dao.impl;

import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.aoufi.springmvcsecurity.model.PersistentLogin;

@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepositoryImpl extends AbstractDao<String, PersistentLogin>
		implements PersistentTokenRepository {
	
	static final Logger logger = LoggerFactory.getLogger(HibernateTokenRepositoryImpl.class);

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		logger.info("Création de jeton pour l'utilisateur : {}", token.getUsername());
		PersistentLogin persistentLogin = new PersistentLogin();
		persistentLogin.setUser_name(token.getUsername());
		persistentLogin.setSeries(token.getSeries());
		persistentLogin.setToken(token.getTokenValue());
		persistentLogin.setLast_used(token.getDate());
		persist(persistentLogin); // getSession().save(logins);
	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		logger.info("Mise à jour du jeton pour seriesId : {}", seriesId);		
		PersistentLogin persistentLogin = getByKey(seriesId);
		persistentLogin.setToken(tokenValue);
		persistentLogin.setLast_used(lastUsed);
		update(persistentLogin);
	}
	
	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		logger.info("Récupérer un jeton s'il y en a pour seriesId : ", seriesId);
		PersistentLogin persistentLogin = getSession().get(PersistentLogin.class, seriesId);
		if (persistentLogin != null) {
			return new PersistentRememberMeToken(persistentLogin.getUser_name(), persistentLogin.getSeries(),
					persistentLogin.getToken(), persistentLogin.getLast_used());
		} else {
			logger.info("Jeton non trouvé...");
			return null;
		}
	}

	@Override
	public void removeUserTokens(String user_name) {
		logger.info("Suppression du jeton s'il y en a un pour l'utilisateur : {}", user_name);		
		CriteriaQuery<PersistentLogin> criteriaQuery = getCriteriaBuilder().createQuery(PersistentLogin.class);
		Root<PersistentLogin> root = criteriaQuery.from(PersistentLogin.class);
		criteriaQuery.select(root);
		criteriaQuery.where(getCriteriaBuilder().equal(root.get("user_name"), user_name));		
		TypedQuery<PersistentLogin> typedQuery = getSession().createQuery(criteriaQuery);
		try {
			PersistentLogin persistentLogin = typedQuery.getSingleResult();
			if (persistentLogin != null) {
				logger.info("souviens-moi a été sélectionné");
				delete(persistentLogin);
			}
		} catch (final NoResultException nre) {
			
		}
	}
	
	/*// Autre manière : On peut utiliser une requête hql
	@Override
	public void removeUserTokens(String user_name) {
		getSession().createQuery("delete from PersistentLogin"
				+ " where user_name=:user_name")
		        .setParameter("user_name", user_name).executeUpdate();
	}
	*/
}