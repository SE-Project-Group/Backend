package model;

public class Manager {
	
	private int admin_ID;
	private String admin_name;
	private String password;
	
	public Manager(){
		
	}
	
	public Manager(int admin_ID,String admin_name,String password){
		this.admin_ID=admin_ID;
		this.admin_name=admin_name;
		this.password=password;
	}
	
	public int getAdmin_ID() {
		return admin_ID;
	}
	public void setAdmin_ID(int admin_ID) {
		this.admin_ID = admin_ID;
	}
	
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
