package top.suyuanshine.store.web.filter;

import top.suyuanshine.store.domian.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检测用户是否登录
 */
@WebFilter(filterName = "PrivileedgeFilter",urlPatterns = {"/jsp/cart.jsp","/OrderServlet","/CartServlet","/jsp/order_info.jsp","/jsp/order_list.jsp"})
public class PrivileedgeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        //获取登录信息
        User user= (User) request.getSession().getAttribute("loginUser");
        if (null!=user){
            //有登录信息
            chain.doFilter(req, resp);
        }else{
            //没有登录，跳转提示页面
            request.setAttribute("msg","检测到还未登录！请先<a href=\""+request.getContextPath()+"/UserServlet?method=loginUI\">登录</a>");
            request.getRequestDispatcher("/jsp/info.jsp").forward(request,response);
            return;
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
