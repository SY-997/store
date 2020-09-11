package top.suyuanshine.store.web.servlet;

import top.suyuanshine.store.domian.*;
import top.suyuanshine.store.service.OrderService;
import top.suyuanshine.store.service.serviceImp.OrderServiceImpl;
import top.suyuanshine.store.utils.PaymentUtil;
import top.suyuanshine.store.utils.UUIDUtils;
import top.suyuanshine.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "OrderServlet",urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet{
    OrderService orderService=new OrderServiceImpl();

    /**
     * 保存订单
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String saveOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //先判断用户是否登录
        User user= (User) request.getSession().getAttribute("loginUser");
        if (null==user){
            request.setAttribute("msg","请登录后再下单!");
            return "/jsp/info.jsp";
        }
        //获取购物车
        Cart cart= (Cart) request.getSession().getAttribute("cart");
        //创建订单
        Order order=new Order();
        order.setOid(UUIDUtils.getCode());
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());
        order.setState(1);
        order.setUser(user);
        //遍历订单的详情
        for (CartItem item:cart.getCartItems()) {
            OrderItem orderItem=new OrderItem();
            orderItem.setItemid(UUIDUtils.getCode());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getNum());
            orderItem.setTotal(item.getSubTotal());

            orderItem.setOrder(order);
            order.getList().add(orderItem);
        }
        //调用业务层,保存订单
        orderService.saveOrder(order);
        //提交完成,清空购物车
        cart.clearCart();
        //传入作用域给jsp显示
        request.setAttribute("order",order);
        return "/jsp/order_info.jsp";
    }

    /**
     * 查询个人总订单
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findMyOrdersWithPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取当前页
        int num=Integer.parseInt(request.getParameter("num"));
        User user= (User) request.getSession().getAttribute("loginUser");
        //返回分页结果
        PageModel pm=orderService.findMyOrdersWithPage(user,num);
        request.setAttribute("page",pm);
        return "jsp/order_list.jsp";
    }

    /**
     * 通过订单id查找订单
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findOrderByOid(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取订单号
        String oid=request.getParameter("oid");
        //创建订单对象
        Order order=new Order();
        order=orderService.findOrderByOid(oid);
        //传入作用域给jsp显示
        request.setAttribute("order",order);
        return "/jsp/order_info.jsp";
    }

    /**
     * 通过易宝支付订单
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String payOrder(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //获取到订单编号，收货人姓名、地址、电话、付款银行
        String oid=request.getParameter("oid");
        String name=request.getParameter("name");
        String address=request.getParameter("address");
        String telephone=request.getParameter("telephone");
        String pd_FrpId=request.getParameter("pd_FrpId");
        //调用，查找对应订单，更新信息
        Order order = orderService.findOrderByOid(oid);
        order.setAddress(address);
        order.setName(name);
        order.setTelephone(telephone);
        orderService.updateOrder(order);
        //向易宝支付发送参数
        // 把付款所需要的参数准备好:
        String p0_Cmd = "Buy";
        //商户编号
        String p1_MerId = "10001126856";
        //订单编号
        String p2_Order = oid;
        //金额
        String p3_Amt = order.getTotal()+"";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        //接受响应参数的Servlet
        String p8_Url = "http://localhost:8080/store/OrderServlet?method=callBack";
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        //公司的秘钥
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

        //调用易宝的加密算法,对所有数据进行加密,返回电子签名
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);
        // 使用重定向到易宝支付
        response.sendRedirect(sb.toString());
        return null;
    }

    /**
     * 默认支付成功
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String payOrder01(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //获取到订单编号，收货人姓名、地址、电话、付款银行
        String oid=request.getParameter("oid");
        String name=request.getParameter("name");
        String address=request.getParameter("address");
        String telephone=request.getParameter("telephone");
        String pd_FrpId=request.getParameter("pd_FrpId");
        //调用，查找对应订单，更新信息
        Order order = orderService.findOrderByOid(oid);
        order.setAddress(address);
        order.setName(name);
        order.setTelephone(telephone);
        order.setState(2);
        orderService.updateOrder(order);
        request.setAttribute("msg", "支付成功！订单号：" + oid + "金额：" + order.getTotal());
        //转发到/jsp/info.jsp
        return "/jsp/info.jsp";
    }

    /**
     * 易宝支付后回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String callBack(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //接收易宝支付返回数据
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");
        // hmac
        String hmac = request.getParameter("hmac");
        // 利用本地密钥和加密算法 加密数据
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

        //验证数据合法性
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);

        if (isValid) {
            // 有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向

                //如果支付成功,更新订单状态
                Order order=orderService.findOrderByOid(r6_Order);
                order.setState(2);
                orderService.updateOrder(order);
                //向request放入提示信息
                request.setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
                //转发到/jsp/info.jsp
                return "/jsp/info.jsp";


            } else if (r9_BType.equals("2")) {
                // 修改订单状态:
                // 服务器点对点，来自于易宝的通知
                System.out.println("收到易宝通知，修改订单状态！");//
                // 回复给易宝success，如果不回复，易宝会一直通知
                response.getWriter().print("success");
            }

        } else {
            throw new RuntimeException("数据被篡改！");
        }
        request.setAttribute("msg", "支付失败，未知错误！！！订单号：" + r6_Order + "金额：" + r3_Amt);
        //转发到/jsp/info.jsp
        return "/jsp/info.jsp";
    }

    /**
     * 收货
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String takeDelivery(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //获取订单编号
        String oid=request.getParameter("oid");
        Order order=orderService.findOrderByOid(oid);
        //获取到订单，修改订单状态
        order.setState(4);
        orderService.updateOrder(order);
        response.sendRedirect("OrderServlet?method=findMyOrdersWithPage&num=1");
        return null;
    }

}
