package dao.impl;

import dao.TokenDao;
import model.Token;
import util.RedisUtil;

import java.util.UUID;

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
		Token token = new Token(user_ID,s);
		redisUtil.put(user_ID, s);
		return token;
	}

	@Override
	public boolean checkToken(Token token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Token getToken(String authentication) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteToken(int userId) {
		// TODO Auto-generated method stub
		
	}



}
