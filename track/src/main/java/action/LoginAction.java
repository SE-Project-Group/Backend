package action;

import java.util.List;

import model.Client;
import service.ClientService;
import service.WebService;

public class LoginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String adminName;
	
	private String password;
	
	private ClientService clientService;
	
	private WebService webService;
	
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
	
	public void setWebService(WebService webService) {
		this.webService = webService;
	}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	public String managerLogin(){
		if(webService.managerLogin(adminName, password))
		{List<Client> clients=clientService.getAllClients();
		request().setAttribute("clients", clients);
			return SUCCESS;}
		else return ERROR;
		
	}
	
	public String managerLogout(){
		return SUCCESS;
	}



}
