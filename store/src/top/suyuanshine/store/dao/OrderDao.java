package top.suyuanshine.store.dao;

import top.suyuanshine.store.domian.Order;
import top.suyuanshine.store.domian.OrderItem;
import top.suyuanshine.store.domian.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单Dao
 */
public interface OrderDao {
    /**
     * 保存订单
     * @param conn
     * @param order
     * @throws SQLException
     */
    void saveOrder(Connection conn, Order order) throws SQLException;

    /**
     * 保存订单详情
     * @param conn
     * @param item
     * @throws SQLException
     */
    void saveOrderItem(Connection conn, OrderItem item) throws SQLException;

    /**
     * 获取用户总订单数量
     * @param user 当前用户
     * @return
     */
    int gettotalRecerds(User user) throws SQLException;

    /**
     * 根据分页查询用户订单
     * @param user 用户
     * @param startIndex 起始位置
     * @param pageSize 每页条数
     * @return
     * @throws SQLException
     */
    List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws SQLException, Exception;

    /**
     * 根据订单号查询订单
     * @param oid
     * @return
     */
    Order findOrderByOid(String oid) throws SQLException;

    /**
     * 更新订单信息
     * @param order 订单
     */
    void updateOrder(Order order) throws SQLException;

    /**
     * 查询订单总数
     * @return
     */
    int gettotalRecerds() throws SQLException;

    /**
     * 分页查找所有订单
     * @param startIndex 起始位置
     * @param pageSize 每页数量
     * @return
     */
    List findOrdersWithPage(int startIndex, int pageSize) throws SQLException;

    /**
     * 根据订单状态查询订单总数
     * @param state 订单状态 1：未付款，2:已付款，3：已发货，4：交易完成
     * @return
     * @throws SQLException
     */
    int gettotalRecerdsByState(int state) throws SQLException;

    /**
     * 根据订单状态分页查找所有订单
     * @param state 订单状态 1：未付款，2:已付款，3：已发货，4：交易完成
     * @param startIndex 起始位置
     * @param pageSize 每页数量
     * @return
     */
    List findOrdersWithPageByState(int state, int startIndex, int pageSize) throws SQLException;
}
