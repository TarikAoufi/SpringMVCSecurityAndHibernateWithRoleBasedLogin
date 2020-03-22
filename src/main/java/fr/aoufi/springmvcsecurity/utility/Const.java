package fr.aoufi.springmvcsecurity.utility;

public class Const {
	
	private Const() {
		throw new IllegalStateException("Constants class");
	}
	
	/*
	 * Constantes Rôles
	 */
	public static final String ADMIN_ROLE = "ROLE_ADMIN";
	public static final String DBA_ROLE = "ROLE_DBA";
	public static final String USER_ROLE = "ROLE_USER";

}
