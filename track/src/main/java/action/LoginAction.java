package action;

import java.util.List;

import model.Client;
import service.AppService;

public class LoginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String adminName;
	
	private String password;
	
	private AppService appService;
	
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
	
	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	
	public String managerLogin(){
		if(appService.managerLogin(adminName, password))
		{List<Client> clients=appService.getAllClients();
		request().setAttribute("clients", clients);
			return SUCCESS;}
		else return ERROR;
		
	}
	
	public String managerLogout(){
		return SUCCESS;
	}



}
