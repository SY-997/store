package top.suyuanshine.store.web.filter;

import top.suyuanshine.store.domian.User;
import top.suyuanshine.store.service.daoImp.UserDaoImp;
import top.suyuanshine.store.utils.CookUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

/**
 * 自动登录
 */
@WebFilter(filterName = "UserLoginFilter",urlPatterns = "/*")
public class UserLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        Object session = request.getSession().getAttribute("loginUser");
        String url = request.getQueryString();;
        //session失效时
        if (session==null){
            //获取客户端cookie
            Cookie cookie = CookUtils.getCookieByName("user", request.getCookies());
            //Cookie为空表示第一次登录
            if (cookie!=null){
               String value = cookie.getValue();
                //解码用URLDecoder.decode
                value = URLDecoder.decode(value,"UTF-8");
                //分割
                String[] strs = value.split("-&\\$-");
                //取出用户名和密码封装
                User user=new User();
                user.setUsername(strs[0]);
                user.setPassword(strs[1]);
                try {
                    //进行登录,并存入session
                    User user1=new UserDaoImp().userLogin(user);
                    //如果有cookie，并且查询用户不为空访问登录注册,则直接跳转首页
                    if ("method=loginUI-method=registUI".contains(url+"")&&user1!=null){
                        request.getSession().setAttribute("loginUser",user1);
                        request.getRequestDispatcher("/IndexServlet").forward(request,response);

                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else{
            //如果有session访问登录注册,则直接跳转首页
            if ("method=loginUI-method=registUI".contains(url+"")){
                request.getRequestDispatcher("/IndexServlet").forward(request,response);
                return;
            }
            chain.doFilter(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
