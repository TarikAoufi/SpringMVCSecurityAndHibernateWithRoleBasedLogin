package fr.aoufi.springmvcsecurity.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_PERSISTENT_LOGIN")
public class PersistentLogin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SERIES", length = 64)
	private String series;
	
	@Column(name = "USER_NAME", length = 64, unique = true, nullable = false)
	private String user_name;
	
	@Column(name = "TOKEN", length = 64, unique = true, nullable = false)
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_USED")
	private Date last_used;

	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUser_name() {
		return this.user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLast_used() {
		return this.last_used;
	}

	public void setLast_used(Date last_used) {
		this.last_used = last_used;
	}
}