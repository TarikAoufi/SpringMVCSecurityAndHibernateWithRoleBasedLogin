package fr.aoufi.springmvcsecurity.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "T_ROLE")
public class Role implements Serializable {
	
	private static final long serialVersionUID = -8195718884317992791L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "TYPE", length = 30, unique = true, nullable = false)
	private String type;

	public Role() {
		this.type = RoleType.USER.getRole();
	}
	
}
