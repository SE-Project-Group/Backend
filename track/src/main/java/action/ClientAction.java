package action;

import java.sql.Date;
import java.util.List;

import model.Client;

import service.AppService;

public class ClientAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private int user_ID;
	
	private String phone;
	
	private String gender;
	
	private Date birthday;
	
	private String user_name;
	
	private String password;
	
	private String email;
	
	private AppService appService;

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
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	
	public String allClient(){
		List<Client> clients=appService.getAllClients();
		request().setAttribute("clients", clients);
		System.out.println(clients.get(1).getUser_ID());
		return SUCCESS;
	}
	
	public String addClient(){
		Client client=new Client(phone,gender,birthday,user_name,password,email);
		appService.insertClient(client);
		return SUCCESS;
	}
	
	public String updateClient(){
		Client client=appService.getClientByID(user_ID);
		client.setBirthday(birthday);
		client.setEmail(email);
		client.setGender(gender);
		client.setPassword(password);
		client.setPhone(phone);
		client.setUser_name(user_name);
		appService.updateClient(client);
		return SUCCESS;
	}
	
	public String deleteClient(){
		Client client=appService.getClientByID(user_ID);
		appService.deleteClient(client);
		return SUCCESS;
	}
}
