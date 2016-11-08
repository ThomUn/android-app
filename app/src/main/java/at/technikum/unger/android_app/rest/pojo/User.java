package at.technikum.unger.android_app.rest.pojo;

/**
 * @author Thomas
 *
 */
public class User {
	private String email;
	private String password;
	private String sessionToken;
	private int balance;

	public User() {
		super();
	}

	public User(String email, String hashedPassword, String sessionToken, int balance) {
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


	public int getBalance() {
		return balance;
	}


	public void setBalance(int balance) {
		this.balance = balance;
	}
}