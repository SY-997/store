package top.suyuanshine.store.web.filter;

import top.suyuanshine.store.domian.AdminUser;
import top.suyuanshine.store.domian.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理员界面拦截
 */
@WebFilter(filterName = "AdminFilter",urlPatterns = {"/admin","/AdminCategoryServlet","/AdminOrderServlet","/AdminProductServlet"})
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        //获取登录信息
        AdminUser adminUser= (AdminUser) request.getSession().getAttribute("adminUser");
        if (null!=adminUser){
            //有登录信息
            chain.doFilter(req, resp);
        }else{
            //没有登录，跳转提示页面
            request.setAttribute("msg","检测到还未登录！请先<a href=\""+request.getContextPath()+"/admin.jsp\">登录</a>");
            request.getRequestDispatcher("/jsp/info.jsp").forward(request,response);
            return;
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
