package dao;

import java.security.NoSuchAlgorithmException;

import model.Token;

public interface TokenDao {
	/**
     * ����һ��token������ָ���û�
     * @param userId ָ���û���id
     * @return ���ɵ�token
     */
    public Token createToken(int user_ID);

    /**
     * ���token�Ƿ���Ч
     * @param model token
     * @return �Ƿ���Ч
     */
    public boolean checkSign(int user_ID, String uri ,String sign)throws NoSuchAlgorithmException;

    /**
     * ���token
     * @param userId ��¼�û���id
     */
    public void deleteToken(int user_ID);
}
