package models;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.mindrot.jbcrypt.BCrypt;

import play.Play;
import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class User extends Model {
	@Required
	@Unique
	public String username;

	@Password
	@MinSize(4)
	@MaxSize(32)
	public String password;
	
	
	public String passwordHash;
	
	@Required
	@Email
	public String email;
	
	
	@PrePersist
	@PreUpdate
	public void hashAndSaltPasswords() {
		if (password != null && !password.equals("")) {
			passwordHash = BCrypt.hashpw(password, Play.configuration.getProperty("password.salt"));
			password = "";
		}
	}
	
	public static boolean connect(String email, String password) {
		if (password == null || password.equals("")) {
			return false;
		}
		String hash = BCrypt.hashpw(password, Play.configuration.getProperty("password.salt"));
		return User.find("byEmailAndPasswordHash", email, hash).first() != null;
	}
	
}
