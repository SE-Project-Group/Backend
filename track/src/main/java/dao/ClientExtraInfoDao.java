package dao;

import java.util.List;


import model.ClientExtraInfo;

public interface ClientExtraInfoDao {
	public void update(ClientExtraInfo clientExtraInfo);
	public void delete(ClientExtraInfo clientExtraInfo);
	public void insert(ClientExtraInfo clientExtraInfo);
	
	
	public ClientExtraInfo getClientExtraInfoById(int id);
	public List<ClientExtraInfo> getClientByHobby(String hobby);
	public List<ClientExtraInfo> getAllClientExtraInfos();
	public List<ClientExtraInfo> getClientByCareer(String career);
	public List<ClientExtraInfo> getClientByEducation(String education);
}
