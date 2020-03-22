package fr.aoufi.springmvcsecurity.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "T_USER")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	@Column(name = "FIRSTNAME", length = 30, nullable = false)
	private String firstName;
	
	@NotEmpty
	@Column(name = "LASTNAME", length = 30, nullable = false)
	private String lastName;
	
	@NotEmpty
	@Column(name = "USERNAME", length = 30, unique = true, nullable = false)
	private String userName;
	
	@NotEmpty
	@Column(name = "PASSWORD", length = 100, nullable = false)
	private String password;
	
	@NotEmpty
	@Column(name = "EMAIL", length = 30, nullable = false)
	private String email;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_USER_ROLE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles = new HashSet<>();

}
