package top.suyuanshine.store.service;

import top.suyuanshine.store.domian.AdminUser;

import java.sql.SQLException;

/**
 * 处理管理员事务
 */
public interface AdminUserService {

    /**
     * 登录
     * @param adminUser
     * @return
     * @throws SQLException
     */
    AdminUser adminLogin(AdminUser adminUser) throws SQLException;
}
