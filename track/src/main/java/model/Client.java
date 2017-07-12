package model;

import java.sql.Date;

public class Client {
	
	private int userId;
	private String phone;
	private String gender;
	private Date birthday;
	private String userName;
	private String password;
	private String email;
	
	public Client(){
		
	}
	
	public Client(String phone,String gender,Date birthday,String userName,String password,String email){

		this.phone=phone;
		this.gender=gender;
		this.birthday=birthday;
		this.setUserName(userName);
		this.password=password;
		this.email=email;
	}
	

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
