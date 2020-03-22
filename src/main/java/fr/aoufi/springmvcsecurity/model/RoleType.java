package fr.aoufi.springmvcsecurity.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleType implements Serializable {
	
	ADMIN("ADMIN", 30), 
	DBA("DBA", 20), 
	USER("USER", 10);

	String role;
	int sessionTime;

}
