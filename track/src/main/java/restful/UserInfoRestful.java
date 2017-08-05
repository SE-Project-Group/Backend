package restful;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;

import model.Client;
import model.ReturnClient;
import model.ReturnFollow;
import model.ReturnUserInfo;
import model.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;
import service.ClientService;
import service.FollowService;
import service.JPushService;
import util.JSONUtil;
import util.SQLDateProcessor;
import util.SpringContextUtil;

@Path("/app/user")
public class UserInfoRestful {

	private JPushService jpushService=(JPushService) SpringContextUtil.getBean("jpushService");
	private ClientService clientService=(ClientService) SpringContextUtil.getBean("clientService");
	private FollowService followService=(FollowService) SpringContextUtil.getBean("followService");
	/**
	 * 用户登录
	 * @URL http://192.168.1.13:8088/track/rest/app/clientLogin?user_name=**&password=** 
	 * @param userName
	 * @param password
	 * @return JSON
	 * {
     *"token": "1771fcf8a5c149e48da138099fe2ff53",
     *"userId": 2
     * }
	 */
	@GET
	@Path("/clientLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public String clientLogin(
			@QueryParam("user_name") String userName,
    		@QueryParam("password") String password) 
	{
		Token token=clientService.clientLogin(userName,password);
		if(token==null){
			return "ERROR";
		}
		JSONObject json=JSONObject.fromObject(token);
		return json.toString();
	}
	/**
	 * 用户登出
	 * @param userId
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@GET
	@Path("/clientLogout")
	@Produces(MediaType.TEXT_PLAIN)
	public String clientLogout(
			@QueryParam("user_id") int userId) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
		clientService.logout(userId);
		return "success";
	}	
	/**
	 * 用户注册
	 * @param message
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/clientSignup")
	@Consumes(MediaType.APPLICATION_JSON)  
	@Produces("text/html")
	public String clientSignup(String message) throws JSONException{
		JSONObject obj = JSONObject.fromObject(message);
		int userId=clientService.signup((String)obj.get("user_name"),(String)obj.get("password"),(String)obj.get("phone"));
		if(userId>0)return String.valueOf(userId);
		else if(userId==-1)return "existing phone";
		else if(userId==-2)return "existing user name";
		else return "existing phone and user name";
	}
	
	/**
	 * 查询用户个人信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@GET
	@Path("/queryPersonalInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String queryPersonalInfo(
			@QueryParam("user_id") int userId) throws ClassNotFoundException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		Client client=clientService.getClientById(userId);
		ReturnClient returnClient=clientService.clientToReturnClient(client);
		return JSONObject.fromObject(returnClient).toString();
	}
	/**
	 * 修改用户个人信息
	 * @param message
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws ParseException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@PUT
	@Path("/modifyPersonalInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String modifyPersonalInfo(String message,
			@QueryParam("user_id") int userId) throws JSONException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		JSONObject obj=JSONObject.fromObject(message);
		Client client=clientService.getClientById(userId);
		client.setUserName(obj.getString("user_name"));
		client.setBirthday(java.sql.Date.valueOf(obj.getString("birthday")));
		client.setEmail(obj.getString("email"));
		client.setGender(obj.getString("gender"));
		clientService.updateClient(client);
		return "success";
	}

	/**
	 * 获取我的朋友的信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 */
	@GET
	@Path("getMyFriendInformationById")

	@Produces("text/html")
	public String getMyFriendInformationById(
			@QueryParam("user_id") int userId)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException, ClassNotFoundException{
		//if(!appService.checkSign(userId, "track/rest/app/getMyFriendInformationById", sign))return "status wrong"; 
		List<Client> list=followService.getMyFriendInformationById(userId);
		if(list!=null)
		{ 
			String shortFormat = "yyyy-MM-dd";  
			Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
			processors.put("java.sql.Date", new SQLDateProcessor(shortFormat)); 		
			JSONArray json = JSONUtil.toJsonArray(list,processors);
			return json.toString();
		}
		else return "error";
	}
	/**
	 * 获取我关注的人的信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 */
	@GET
	@Path("getFollowing")
	@Produces("text/html")
	public String getFollowing(
			@QueryParam("user_id") int userId,
			@QueryParam("who") int who)throws JSONException{
		List<ReturnFollow> list=followService.getFollowingInformationById(userId,who);
		JSONArray ja=JSONArray.fromObject(list);
		return ja.toString();
	}
	/**
	 * 获取关注我的人的信息
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 */
	@GET
	@Path("getFollower")
	@Produces("text/html")
	public String getFollower(
			@QueryParam("user_id") int userId,
			@QueryParam("who") int who)throws JSONException{
		List<ReturnFollow> list=followService.getFollowerInformationById(userId,who);
		JSONArray ja=JSONArray.fromObject(list);
		return ja.toString();
	}
	/**
	 * 获取单个用户的信息
	 * @param userId
	 * @param sign
	 * @param id
	 * @return
	 */
	@GET
	@Path("getHomeInfo")
	@Produces("text/html")
	public String getHomeInfo(
			@QueryParam("user_id") int userId,
			@QueryParam("who") int who){
		ReturnUserInfo rui=followService.getSomeoneInfo(userId,who);
		JSONObject obj=JSONObject.fromObject(rui);
		return obj.toString();
	}
	/**
	 * 关注某人
	 * @param tstring
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("newFollow")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String newFollow(String tstring,
			@QueryParam("user_id") int userId)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		//if(!appService.checkSign(userId, "track/rest/app/newFollow", sign))return "status wrong"; 
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		int followId= Integer.parseInt(tsinfo.getString("followId"));
		String res=followService.followSomeone(userId,followId);
		String msgContent="NewFollower";
		String sfollowId=String.valueOf(followId);
        jpushService.senMessageByAlias(sfollowId, msgContent);
		return res;
	}
	/**
	 * 取消对于某人的关注
	 * @param tstring
	 * @param userId
	 * @param sign
	 * @return
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@DELETE
	@Path("deleteFollow")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public String deleteFollow(String tstring,
			@QueryParam("user_id") int userId)throws JSONException, NoSuchAlgorithmException, UnsupportedEncodingException{
		JSONObject tsinfo = JSONObject.fromObject(tstring);
		int followId= Integer.parseInt(tsinfo.getString("followId"));	
		String res=followService.unFollowSomeone(userId,followId);
		return res;
	}
	
	@GET
	@Path("getPortraitUrl")
	@Produces("text/html")
	public String getPortraitUrl(
			@QueryParam("user_id") int userId){
		return clientService.getPortraitUrl(userId);
	}
}
