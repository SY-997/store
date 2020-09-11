package top.suyuanshine.store.web.servlet;

import top.suyuanshine.store.domian.AdminUser;
import top.suyuanshine.store.service.AdminUserService;
import top.suyuanshine.store.service.serviceImp.AdminUserServiceImpl;
import top.suyuanshine.store.utils.MyBeanUtils;
import top.suyuanshine.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理员用户处理
 */
@WebServlet(name = "AdminUserServlet",urlPatterns = "/AdminUserServlet")
public class AdminUserServlet extends BaseServlet {

    /**
     * 管理员登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String adminUserLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        AdminUser adminUser= new AdminUser();
        MyBeanUtils.populate(adminUser,request.getParameterMap());
        AdminUserService adminUserService=new AdminUserServiceImpl();
        AdminUser adminUser1=adminUserService.adminLogin(adminUser);
        //用户存在
        if (adminUser1!=null){
            request.getSession().setAttribute("adminUser",adminUser1);
            return "admin/home.jsp";
        }
        request.setAttribute("msg","用户名或密码错误");

        return "admin.jsp";
    }
}
