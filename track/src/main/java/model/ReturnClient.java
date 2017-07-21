package model;

public class ReturnClient {
	private String user_name;
	private String gender;
	private String birthday;
	private String email;
	private String portrait_url;
	
	public ReturnClient(String user_name,String gender,String birthday,String email,String portrait_url){
		this.user_name=user_name;
		this.gender=gender;
		this.birthday=birthday;
		this.email=email;
		this.portrait_url=portrait_url;
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPortrait_url() {
		return portrait_url;
	}
	public void setPortrait_url(String portrait_url) {
		this.portrait_url = portrait_url;
	}
}
