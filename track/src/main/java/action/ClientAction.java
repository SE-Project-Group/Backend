package action;

import java.sql.Date;
import java.util.List;

import model.Client;

import service.ClientService;

public class ClientAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private int userId;
	
	private String phone;
	
	private String gender;
	
	private Date birthday;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private ClientService clientService;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	public String allClient(){
		List<Client> clients=clientService.getAllClients();
		request().setAttribute("clients", clients);
		return SUCCESS;
	}
	
	public String addClient(){
		Client client=new Client(phone,gender,birthday,userName,password,email);
		clientService.insertClient(client);
		return SUCCESS;
	}
	
	public String updateClient(){
		Client client=clientService.getClientById(userId);
		client.setBirthday(birthday);
		client.setEmail(email);
		client.setGender(gender);
		client.setPassword(password);
		client.setPhone(phone);
		client.setUserName(userName);
		clientService.updateClient(client);
		return SUCCESS;
	}
	
	public String deleteClient(){
		Client client=clientService.getClientById(userId);
	    clientService.deleteClient(client);
		return SUCCESS;
	}
}
