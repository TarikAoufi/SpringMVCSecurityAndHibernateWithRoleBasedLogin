package fr.aoufi.springmvcsecurity.model;

import java.io.Serializable;

public enum RoleType implements Serializable {
	
	ADMIN("ADMIN"), 
	DBA("DBA"), 
	USER("USER");

	String roleType;

	private RoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleType() {
		return this.roleType;
	}
}