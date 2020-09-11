package top.suyuanshine.store.dao;

import top.suyuanshine.store.domian.AdminUser;

import java.sql.SQLException;

/**
 * 管理员事务
 */
public interface AdminUserDao {
    /**
     * 登录
     * @param adminUser
     * @return
     * @throws SQLException
     */
    AdminUser adminLogin(AdminUser adminUser) throws SQLException;
}
