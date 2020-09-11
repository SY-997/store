package top.suyuanshine.store.service;

import top.suyuanshine.store.domian.User;

import java.sql.SQLException;

public interface UserService {

    /**
     * 用户注册
     * @param user
     */
    void userRegist(User user) throws SQLException;

    /**
     * 检测用户名
     * @param username
     * @return
     * @throws SQLException
     */
    String checkUsername(String username) throws SQLException;

    /**
     * 激活用户
     * @param code
     * @throws SQLException
     * @return 返回值 1:激活成功 2:用户已经激活 3:激活码不存在
     */
    String activeUser(String code) throws SQLException;


    /**
     * 登录查询
     * @param user
     * @return
     * @throws SQLException
     */
    User userLogin(User user) throws SQLException;
}
