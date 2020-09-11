package top.suyuanshine.store.service.daoImp;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.suyuanshine.store.dao.UserDao;
import top.suyuanshine.store.domian.User;
import top.suyuanshine.store.service.serviceImp.UserServiceImp;
import top.suyuanshine.store.utils.JDBCUtils;

import java.sql.SQLException;

public class UserDaoImp implements UserDao {

    /**
     * 实现用户注册
     * @param user
     * @throws SQLException
     */
    @Override
    public void userRegist(User user) throws SQLException {
        String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        runner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
    }

    /**
     * 实现检测用户名,存在返回1，不存在返回null
     * @param username
     * @return
     * @throws SQLException
     */
    @Override
    public String checkUsername(String username) throws SQLException {
        String sql="select 1 from user where username=?";
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        Object str = runner.query(sql, new ScalarHandler(), username);
        return str+"";
    }

    /**
     * 实现用户激活查询
     * @param code
     * @throws SQLException
     * @return 返回值 1:激活成功 2:用户已经激活 3:激活码不存在
     */
    @Override
    public String activeUser(String code) throws SQLException {

        //获取该激活码的用户当前状态,1为激活
        String sql="select state from user where code=?";
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        Object state = runner.query(sql, new ScalarHandler(), code);
        return state+"";

    }

    /**
     * 用户激活
     * @param code
     * @throws SQLException
     */
    @Override
    public void updateState(String code) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        //改State为1激活
        runner.update("update user set state=? where code=?",1,code);
    }

    /**
     * 根据用户名和密码查询所有信息
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public User userLogin(User user) throws SQLException {
        String sql="select * from user where username=? and password=?";
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query(sql,new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
    }

   /* public static void main(String[] args) throws SQLException {
       new UserDaoImp().activeUser("991D1E13EE2D4E15A9vDC77F9E9E0B668");

    }*/
}
