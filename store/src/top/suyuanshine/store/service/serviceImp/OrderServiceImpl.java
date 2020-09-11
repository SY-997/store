package top.suyuanshine.store.service.serviceImp;

import top.suyuanshine.store.dao.OrderDao;
import top.suyuanshine.store.domian.Order;
import top.suyuanshine.store.domian.OrderItem;
import top.suyuanshine.store.domian.PageModel;
import top.suyuanshine.store.domian.User;
import top.suyuanshine.store.service.OrderService;
import top.suyuanshine.store.service.daoImp.OrderDaoImpl;
import top.suyuanshine.store.utils.BeanFactory;
import top.suyuanshine.store.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao=(OrderDao) BeanFactory.createObject("OrderDao");

    @Override
    public void saveOrder(Order order) throws SQLException {
        Connection conn=null;
        try {
            conn=JDBCUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);

            orderDao.saveOrder(conn,order);
            for (OrderItem item:order.getList() ){
                orderDao.saveOrderItem(conn,item);
            }
            //成功,提交事务
            conn.commit();
        } catch (SQLException e) {
            //失败,回滚事务
            conn.rollback();
            e.printStackTrace();
        } finally {
            //关闭释放conn
            JDBCUtils.closeConn(conn);

        }
    }

    @Override
    public PageModel findMyOrdersWithPage(User user, int num) throws Exception {
        //根据用户查询所有订单数量
        int totalRecerds=orderDao.gettotalRecerds(user);
        PageModel pm=new PageModel(num,totalRecerds,3);
        List list=orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
        pm.setRecords(list);
        pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
        return pm;
    }

    @Override
    public Order findOrderByOid(String oid) throws SQLException {
        Order order=orderDao.findOrderByOid(oid);
        return order;
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        orderDao.updateOrder(order);
    }

    @Override
    public PageModel findOrdersWithPage(int num) throws SQLException {
        //查询所有订单数量
        int totalRecerds=orderDao.gettotalRecerds();
        PageModel pm=new PageModel(num,totalRecerds,14);
        List list=orderDao.findOrdersWithPage(pm.getStartIndex(),pm.getPageSize());
        pm.setRecords(list);
        pm.setUrl("AdminOrderServlet?method=findAllOrder");
        return pm;
    }

    @Override
    public PageModel findOrdersWithPageByState(int num, int state) throws SQLException {
        //查询所有订单数量
        int totalRecerds=orderDao.gettotalRecerdsByState(state);
        PageModel pm=new PageModel(num,totalRecerds,14);
        List list=orderDao.findOrdersWithPageByState(state,pm.getStartIndex(),pm.getPageSize());
        pm.setRecords(list);
        pm.setUrl("AdminOrderServlet?method=findAllOrder");
        return pm;
    }
}
