package top.suyuanshine.store.service.serviceImp;

import top.suyuanshine.store.dao.UserDao;
import top.suyuanshine.store.domian.User;
import top.suyuanshine.store.service.UserService;
import top.suyuanshine.store.utils.BeanFactory;

import java.sql.SQLException;

public class UserServiceImp implements UserService {
     UserDao dao=(UserDao) BeanFactory.createObject("UserDao");

    /**
     * 实现注册
     * @param user
     * @throws SQLException
     */
    @Override
    public void userRegist(User user) throws SQLException {
        dao.userRegist(user);
    }

    /**
     * 检测用户名,存在返回1，不存在返回null
     * @param username
     * @return
     */
    @Override
    public String checkUsername(String username) throws SQLException {
        return  dao.checkUsername(username);
    }

    /**
     * 实现激活
     * @param code
     * @throws SQLException
     * @return 返回值 1:激活成功 2:用户已经激活 3:激活码不存在
     */
    @Override
    public String activeUser(String code) throws SQLException {
        String state=dao.activeUser(code);
        if(state!=null){
            //如果不等于1,则开始激活
            if (!state.equals(1)){
                dao.updateState(code);
                return "1";
            }else{
                return "2";
            }
        }
        else{
            return "3";
        }
    }

    /**
     * 实现登录查询
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public User userLogin(User user) throws SQLException {
        User use=dao.userLogin(user);
        if (use==null){
            //密码错误
            throw new RuntimeException("输入密码错误");
        }else if (use.getState()==0){
            //用户未激活
            throw new RuntimeException("您的用户未激活,请在您的邮箱激活!");
        }else{
            return use;
        }
    }
}
