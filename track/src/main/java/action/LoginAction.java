package action;

import java.util.List;

import model.Client;
import service.AppService;

public class LoginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String admin_name;
	
	private String password;
	
	private AppService appService;
	
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
	
	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	
	public String managerLogin(){
		if(appService.managerLogin(admin_name, password))
		{List<Client> clients=appService.getAllClients();
		request().setAttribute("clients", clients);
			return SUCCESS;}
		else return ERROR;
		
	}
	
	public String logout(){
		return null;	
	}



}
