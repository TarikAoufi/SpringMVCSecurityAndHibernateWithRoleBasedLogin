package fr.aoufi.springmvcsecurity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "T_PERSISTENT_LOGIN")
public class PersistentLogin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SERIES", length = 64)
	private String series;
	
	@Column(name = "USER_NAME", length = 64, unique = true, nullable = false)
	private String userName;
	
	@Column(name = "TOKEN", length = 64, unique = true, nullable = false)
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_USED")
	private Date lastUsed;
}
