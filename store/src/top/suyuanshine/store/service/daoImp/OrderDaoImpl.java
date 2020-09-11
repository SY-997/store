package top.suyuanshine.store.service.daoImp;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.suyuanshine.store.dao.OrderDao;
import top.suyuanshine.store.domian.Order;
import top.suyuanshine.store.domian.OrderItem;
import top.suyuanshine.store.domian.Product;
import top.suyuanshine.store.domian.User;
import top.suyuanshine.store.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void saveOrder(Connection conn, Order order) throws SQLException {
        QueryRunner runner=new QueryRunner();
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        Object[] param={order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
        runner.update(conn,sql,param);
    }

    @Override
    public void saveOrderItem(Connection conn, OrderItem item) throws SQLException {
        QueryRunner runner=new QueryRunner();
        String sql="insert into orderitem values(?,?,?,?,?)";
        Object[] param={item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
        runner.update(conn,sql,param);
    }

    @Override
    public int gettotalRecerds(User user) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select count(*) from orders where uid=? ";
        Long total=(long) runner.query(sql,new ScalarHandler(),user.getUid());
        return total.intValue();
    }

    @Override
    public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders where uid=? ORDER BY ordertime DESC limit ?,?";
        //取出该用户所有订单
        List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);

        //遍历所有订单
        for (Order order:orderList ){
            sql="SELECT * FROM product p,orderitem o WHERE p.pid=o.pid AND o.oid=? ";
            List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), order.getOid());
            //每个订单的详情查询遍历
            for (Map<String, Object> map:mapList ){
                OrderItem orderItem=new OrderItem();
                Product product=new Product();
                // 1_创建时间类型的转换器
                DateConverter dt = new DateConverter();
                // 2_设置转换的格式
                dt.setPattern("yyyy-MM-dd");
                // 3_注册转换器
                ConvertUtils.register(dt, java.util.Date.class);
                try {
                    //将查询出的数据填充到对象上
                    BeanUtils.populate(orderItem, map);
                    BeanUtils.populate(product, map);
                    //将商品添加到详情订单对象
                    orderItem.setProduct(product);
                    //再将详情添加到总订单上
                    order.getList().add(orderItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return orderList;
    }

    @Override
    public Order findOrderByOid(String oid) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders where oid=?";
        //查询出订单
        Order order = runner.query(sql, new BeanHandler<Order>(Order.class), oid);
        //再查询订单中商品信息
        sql="SELECT * FROM product p,orderitem o WHERE p.pid=o.pid AND o.oid=? ";
        List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), order.getOid());
        //每个订单的详情查询遍历
        for (Map<String, Object> map:mapList ) {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            // 1_创建时间类型的转换器
            DateConverter dt = new DateConverter();
            // 2_设置转换的格式
            dt.setPattern("yyyy-MM-dd");
            // 3_注册转换器
            ConvertUtils.register(dt, java.util.Date.class);
            try {
                //将查询出的数据填充到对象上
                BeanUtils.populate(orderItem, map);
                BeanUtils.populate(product, map);
                //将商品添加到详情订单对象
                orderItem.setProduct(product);
                //再将详情添加到总订单上
                order.getList().add(orderItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="update orders set ordertime=? ,total=? ,state=? ,address=? ,name=? ,telephone=? where oid=?";
        Object[] param={order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
        runner.update(sql,param);
    }

    @Override
    public int gettotalRecerds() throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select count(*) from orders ";
        Long total=(long) runner.query(sql,new ScalarHandler());
        return total.intValue();
    }

    @Override
    public List findOrdersWithPage(int startIndex, int pageSize) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders ORDER BY ordertime DESC limit ?,?";
        List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class),startIndex,pageSize);
        return orderList;
    }

    @Override
    public int gettotalRecerdsByState(int state) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select count(*) from orders where state=? ";
        Long total=(long) runner.query(sql,new ScalarHandler(),state);
        return total.intValue();
    }

    @Override
    public List findOrdersWithPageByState(int state, int startIndex, int pageSize) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders where state=? ORDER BY ordertime DESC limit ?,?";
        List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class),state,startIndex,pageSize);
        return orderList;
    }
}
