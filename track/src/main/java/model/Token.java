package model;


public class Token{

	private int user_ID;

	 private String token;

	 public Token(int user_ID, String token) {
	        this.user_ID = user_ID;
	        this.token = token;
	    }

	    public int getUser_ID() {
	        return user_ID;
	    }

	    public void setUser_ID(int user_ID) {
	        this.user_ID = user_ID;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }
}
