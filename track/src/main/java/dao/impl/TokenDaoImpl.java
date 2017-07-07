package dao.impl;

import dao.TokenDao;

import model.Token;
import util.RedisUtil;

import java.util.UUID;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenDaoImpl implements TokenDao{

	private RedisUtil redisUtil;
	
	public RedisUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	
	@Override
	public Token createToken(int user_ID) {
		String s = UUID.randomUUID().toString(); 
		String tokenStr=s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
		Token token = new Token(user_ID,tokenStr);
		redisUtil.put(user_ID, tokenStr);
		return token;
	}

	@Override
	public boolean checkSign(int user_ID, String uri,String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String token=redisUtil.get(user_ID);
		MessageDigest md=MessageDigest.getInstance("MD5");
		byte[] bs = md.digest((uri+"?token="+token).getBytes("UTF-8"));
		String rightSign=new String(bs,"UTF-8");
		if(sign.equals(rightSign)){
			return true;
		}
		else return false;
	}

	@Override
	public void deleteToken(int user_ID) {
		redisUtil.delete(user_ID);
	}



}
