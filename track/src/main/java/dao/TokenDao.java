package dao;

import model.Token;

public interface TokenDao {
	/**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    public Token createToken(int user_ID);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(Token token);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    public Token getToken(String authentication);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    public void deleteToken(int userId);
}
