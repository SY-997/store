package top.suyuanshine.store.web.servlet;

import top.suyuanshine.store.domian.Cart;
import top.suyuanshine.store.domian.CartItem;
import top.suyuanshine.store.domian.Product;
import top.suyuanshine.store.service.ProductService;
import top.suyuanshine.store.service.serviceImp.ProductServiceImpl;
import top.suyuanshine.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理购物车
 */
@WebServlet(name = "CartServlet",urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {

    /**
     * 添加商品到购物车
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addCartgItemToCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //商品id
        String pid = request.getParameter("pid");
        //商品购买数量
        int count = Integer.parseInt(request.getParameter("count"));
        //从session获取购物车
        Cart cart=(Cart)request.getSession().getAttribute("cart");

        if(null==cart){
            //如果获取不到,创建购物车对象,放在session中
            cart=new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        //如果获取到,使用即可
        //获取到商品id,数量
        //通过商品id查询都商品对象
        ProductService ProductService=new ProductServiceImpl();
        Product product=ProductService.findById(pid);
        //获取到待购买的购物项
        CartItem cartItem=new CartItem();
        cartItem.setNum(count);
        cartItem.setProduct(product);
        //调用购物车上的方法
        cart.addCartItemToCar(cartItem);
        //重定向到/jsp/cart.jsp
        response.sendRedirect("jsp/cart.jsp");
        return null;
    }

    /**
     * 购物车修改商品数量
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String changeCartgItemToCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pid=request.getParameter("pid");
        int num=Integer.parseInt(request.getParameter("num"));
        //从session获取购物车
        Cart cart=(Cart)request.getSession().getAttribute("cart");
        cart.changeProductNum(pid,num);
        //当前商品小计
        Double total=cart.getCartItem(pid).getSubTotal();
        //当前购物车小计
        Double totals= cart.getTotal();
        String json="{\"flag\":\"true\",\"total\":\""+total+"\",\"totals\":\""+totals+"\"}";
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().print(json);
        return null;
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String clearCart(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Cart cart=(Cart)request.getSession().getAttribute("cart");
        cart.clearCart();
        return "jsp/cart.jsp";
    }

    /**
     * 删除某一件商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String removeCartItem(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pid = request.getParameter("pid");
        Cart cart=(Cart)request.getSession().getAttribute("cart");
        cart.removeCartItem(pid);
        response.sendRedirect("jsp/cart.jsp");
        return null;
    }
}
