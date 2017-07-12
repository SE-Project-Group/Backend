package dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import model.Token;

public interface TokenDao {
	/**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    public Token createToken(int userId);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkSign(int userId, String uri ,String sign)throws NoSuchAlgorithmException,UnsupportedEncodingException;

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    public void deleteToken(int userId);
}
