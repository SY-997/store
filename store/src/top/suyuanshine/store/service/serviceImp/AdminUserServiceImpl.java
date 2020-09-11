package top.suyuanshine.store.service.serviceImp;

import top.suyuanshine.store.dao.AdminUserDao;
import top.suyuanshine.store.dao.CategoryDao;
import top.suyuanshine.store.domian.AdminUser;
import top.suyuanshine.store.service.AdminUserService;
import top.suyuanshine.store.utils.BeanFactory;

import java.sql.SQLException;

public class AdminUserServiceImpl implements AdminUserService {
    AdminUserDao dao=(AdminUserDao) BeanFactory.createObject("AdminUserDao");
    @Override
    public AdminUser adminLogin(AdminUser adminUser) throws SQLException {
        return dao.adminLogin(adminUser);
    }
}
