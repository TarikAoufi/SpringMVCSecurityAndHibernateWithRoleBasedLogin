package fr.aoufi.springmvcsecurity.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import fr.aoufi.springmvcsecurity.model.RoleType;
import fr.aoufi.springmvcsecurity.utility.Const; 

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		String targetUrl = targetUrlSessionTimeoutByRole(request, authentication);

		if (response.isCommitted()) {
			logger.info("La réponse a déjà été envoyée. Impossible de rediriger vers {}", targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);

		clearAuthenticationAttributes(request);

	}

	/**
	 * Permet de déterminer l'URL vers laquelle l'utilisateur sera redirigé après la
	 * connexion, et de gérer l'expiration de la session, en fonction du rôle
	 * utilisateur.
	 * 
	 * @param request
	 * @param authentication
	 * @return determineTargetUrl
	 */
	protected String targetUrlSessionTimeoutByRole(HttpServletRequest request, Authentication authentication) {

		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		HttpSession session = request.getSession(false);

		switch (String.join("", roles)) {
		case Const.ADMIN_ROLE:
			session.setMaxInactiveInterval(RoleType.ADMIN.getSessionTime());
			return "/list";
		case Const.DBA_ROLE:
			session.setMaxInactiveInterval(RoleType.DBA.getSessionTime());
			return "/list";
		case Const.USER_ROLE:
			session.setMaxInactiveInterval(RoleType.USER.getSessionTime());
			return "/welcome";
		default:
			throw new IllegalStateException();
		}

	}

	/**
	 * Pour effacer les attributs d'authentification
	 * 
	 */
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
