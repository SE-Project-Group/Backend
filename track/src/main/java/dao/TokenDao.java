package dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import model.Token;

public interface TokenDao {
	/**
     * ����һ��token������ָ���û�
     * @param userId ָ���û���id
     * @return ���ɵ�token
     */
    public Token createToken(int userId);

    /**
     * ���token�Ƿ���Ч
     * @param model token
     * @return �Ƿ���Ч
     */
    public boolean checkSign(int userId, String uri ,String sign)throws NoSuchAlgorithmException,UnsupportedEncodingException;

    /**
     * ���token
     * @param userId ��¼�û���id
     */
    public void deleteToken(int userId);
}
