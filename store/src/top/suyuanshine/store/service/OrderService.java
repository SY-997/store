package top.suyuanshine.store.service;

import top.suyuanshine.store.domian.Order;
import top.suyuanshine.store.domian.PageModel;
import top.suyuanshine.store.domian.User;

import java.sql.SQLException;

/**
 * 订单业务Service
 */
public interface OrderService {


    /**
     *提交订单
     * @param order
     * @throws SQLException
     */
    void saveOrder(Order order) throws SQLException;

    /**
     * 分页查询用户所有订单
     * @param user 用户
     * @param num 当前页
     * @return
     */
    PageModel findMyOrdersWithPage(User user, int num) throws SQLException, Exception;

    /**
     * 根据订单号查询
     * @param oid
     * @return
     */
    Order findOrderByOid(String oid) throws SQLException;

    /**
     * 更新订单信息
     * @param order 订单
     * @throws SQLException
     */
    void updateOrder(Order order) throws SQLException;

    /**
     * 分页查询所有订单
     * @param num 当前页
     * @return
     */
    PageModel findOrdersWithPage(int num) throws SQLException;

    /**
     * 根据状态分页显示所有订单
     * @param num 当前页
     * @param state 订单状态 1：未付款，2:已付款，3：已发货，4：交易完成
     * @return
     * @throws SQLException
     */
    PageModel findOrdersWithPageByState(int num, int state) throws SQLException;
}
