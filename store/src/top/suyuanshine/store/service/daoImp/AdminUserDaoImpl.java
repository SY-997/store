package top.suyuanshine.store.service.daoImp;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import top.suyuanshine.store.dao.AdminUserDao;
import top.suyuanshine.store.domian.AdminUser;
import top.suyuanshine.store.utils.JDBCUtils;

import java.sql.SQLException;

public class AdminUserDaoImpl implements AdminUserDao {
    @Override
    public AdminUser adminLogin(AdminUser adminUser) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        AdminUser adminUser1 = runner.query("select * from adminuser where name=? and password=?", new BeanHandler<AdminUser>(AdminUser.class), adminUser.getName(), adminUser.getPassword());
        return adminUser1;
    }
}
