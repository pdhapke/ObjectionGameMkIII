package Model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name = "users")
public class AuthenticatedUser {
	@Id
	@Column(name="email")
	private String email; 
	@Transient
	private boolean verified; 
	@Column(name="admin")
	private boolean admin; 
	@Column(name="highscore")
	private int highscore; 
	@Column(name="firstname")
	private String firstname; 
	@Column(name="lastname")
	private String lastname;
	@Column(name = "session_id")
	private String session_id; 
	
	@Column(name = "session_exp")
	private Date session_exp; 
	
	
	public AuthenticatedUser(){};
	public AuthenticatedUser(String em, String fname, String lname){
		this.email = em;
		this.firstname = fname;
		this.lastname = lname;
		this.verified = false; 
		this.admin = false; 
		this.highscore = 0; 
		this.session_id = null; 
		}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public int getHighscore() {
		return highscore;
	}
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public Date getSession_exp() {
		return session_exp;
	}
	public void setSession_exp(Date session_exp) {
		this.session_exp = session_exp;
	}
}
