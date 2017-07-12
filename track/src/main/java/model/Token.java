package model;


public class Token{

	private int userId;

	 private String token;

	 public Token(int userId, String token) {
	        this.setUserId(userId);
	        this.setToken(token);
	 }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
