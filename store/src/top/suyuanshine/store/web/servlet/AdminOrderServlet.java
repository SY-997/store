package top.suyuanshine.store.web.servlet;

import top.suyuanshine.store.domian.Order;
import top.suyuanshine.store.domian.PageModel;
import top.suyuanshine.store.service.OrderService;
import top.suyuanshine.store.service.serviceImp.OrderServiceImpl;
import top.suyuanshine.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理员订单管理
 */
@WebServlet(name = "AdminOrderServlet",urlPatterns = "/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    OrderService orderService=new OrderServiceImpl();

    /**
     * 分页显示所有订单
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public String findAllOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取当前页
        int num=Integer.parseInt(request.getParameter("num"));
        PageModel page=orderService.findOrdersWithPage(num);
        request.setAttribute("page",page);
        return "admin/order/list.jsp";
    }

    /**
     * 根据状态分页显示所有订单
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public String findAllOrderByState(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取当前页
        int num=Integer.parseInt(request.getParameter("num"));
        //订单状态 1：未付款，2:已付款，3：已发货，4：交易完成
        int state=Integer.parseInt(request.getParameter("state"));
        PageModel page=orderService.findOrdersWithPageByState(num,state);
        request.setAttribute("page",page);
        return "admin/order/list.jsp";
    }

    /**
     * 根据订单id查询订单详情
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findOrderItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取查询的订单id
        String oid=request.getParameter("oid");
        Order order = orderService.findOrderByOid(oid);
        request.setAttribute("order",order);
        return "admin/order/orderItem.jsp";
    }

    /**
     * 发货
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String sendOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取查询的订单id
        String oid=request.getParameter("oid");
        Order order = orderService.findOrderByOid(oid);
        order.setState(3);
        orderService.updateOrder(order);
        response.sendRedirect("AdminOrderServlet?method=findAllOrderByState&state=3&num=1");
        return null;
    }

}
