package dao;

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
    public boolean checkToken(Token token);

    /**
     * ���ַ����н���token
     * @param authentication ���ܺ���ַ���
     * @return
     */
    public Token getToken(String authentication);

    /**
     * ���token
     * @param userId ��¼�û���id
     */
    public void deleteToken(int userId);
}
