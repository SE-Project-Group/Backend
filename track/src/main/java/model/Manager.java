package model;

public class Manager {
	
	private int adminId;
	private String adminName;
	private String password;
	
	public Manager(){
		
	}
	
	public Manager(int adminId,String adminName,String password){
		this.setAdminId(adminId);
		this.setAdminName(adminName);
		this.setPassword(password);
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
