package dao.impl;

import dao.TokenDao;

import model.Token;
import sun.misc.BASE64Encoder;
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
	public Token createToken(int userId) {
		String s = UUID.randomUUID().toString(); 
		String tokenStr=s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
		Token token = new Token(userId,tokenStr);
		redisUtil.put(userId, tokenStr);
		return token;
	}

	@Override
	public boolean checkSign(int userId, String uri,String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String token=redisUtil.get(userId);
		MessageDigest md=MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
        String rightSign=base64en.encode(md.digest((uri+"?token="+token).getBytes("utf-8")));
        System.out.println(rightSign);
		System.out.println(sign);
		if(sign.equals(rightSign)){
			return true;
		}
		else return false;
	}

	@Override
	public void deleteToken(int userId) {
		redisUtil.delete(userId);
	}
	
	
	public String getHashString( MessageDigest digest )
	{
		StringBuilder builder = new StringBuilder();
		for ( byte b : digest.digest() )
		{
		builder.append( Integer.toHexString( (b >> 4) & 0xf ) );
		builder.append( Integer.toHexString( b & 0xf ) );
		}
		return builder.toString();
		}
		public void createBeforeEncryptString(StringBuilder sb,String key,String value,String gap) {
		sb.append(key).append("=").append(value).append(gap);
	}



}
