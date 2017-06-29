package model;

import java.sql.Date;

public class Client {
	
	private int user_ID;
	private String phone;
	private String gender;
	private Date birthday;
	private String user_name;
	private String password;
	private String email;
	
	public Client(){
		
	}
	
	public Client(String phone,String gender,Date birthday,String user_name,String password,String email){

		this.phone=phone;
		this.gender=gender;
		this.birthday=birthday;
		this.user_name=user_name;
		this.password=password;
		this.email=email;
	}
	
	public int getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
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
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
}
