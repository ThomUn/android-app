package at.technikum.unger.android_app.rest.pojo;

/**
 * @author Thomas
 *
 */
public class User {
	private String email;
	private String password;
	private String sessionToken;
	private double balance;

	public User() {
		super();
	}

	public User(String email, String hashedPassword, String sessionToken, double balance) {
		super();
		this.email = email;
		this.password = hashedPassword;
		this.sessionToken = sessionToken;
		this.balance = balance;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSessionToken() {
		return sessionToken;
	}


	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}
}