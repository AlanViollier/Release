package application;

/**
 * Account object.
 * 
 * @author Alan Viollier
 */
public class Account {
	
	private String userName;
	private String password;
	private String email;
	private String securityQuestion;
	private String securityAnswer;
	
	public Account() {}
	
	// Constructor
	public Account(String userName, String password, String email, String securityQuestion, String securityAnswer) {
		this.setUserName(userName);
		this.setPassword(password);
		this.setEmail(email);
		this.setSecurityQuestion(securityQuestion);
		this.setSecurityAnswer(securityAnswer);
	}
	
	// Getters and Setters
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
