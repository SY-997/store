package top.suyuanshine.store.web.filter;

import top.suyuanshine.store.utils.CookUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 记住用户名
 */
@WebFilter(filterName = "NameKeepFilter",urlPatterns = "/UserServlet")
public class NameKeepFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        //判断有没有保存用户名的cookie
        Cookie cookie=CookUtils.getCookieByName("name",request.getCookies());
        if (cookie!=null){
            String value = cookie.getValue();
            //解码用URLDecoder.decode
            value = URLDecoder.decode(value,"UTF-8");
            //取出用户名,存到作用域
            request.setAttribute("name",value);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
