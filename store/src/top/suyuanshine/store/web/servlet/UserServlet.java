package top.suyuanshine.store.web.servlet;

import top.suyuanshine.store.domian.User;
import top.suyuanshine.store.service.serviceImp.UserServiceImp;
import top.suyuanshine.store.utils.*;
import top.suyuanshine.store.web.base.BaseServlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * 处理用户登录注册的一些请求
 */
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {
    //实现业务方法对象
   private UserServiceImp serviceImp = new UserServiceImp();

    /**
     * 获取验证码图片
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
   public void codeimg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String filePath=(String) request.getSession().getAttribute("filePath");
       //不为空表示不是第一次获取验证码
       if (filePath!=null){
           //删除上次的验证码图片
           DeleteFileUtil.deleteFile(filePath);
       }
       String realPath=getServletContext().getRealPath("/img/code/");
       long fileName=System.currentTimeMillis();
       OutputStream out = new FileOutputStream(realPath+"/"+fileName+".jpg");
       Map<String,Object> map = CodeUtils.generateCodeAndPic();
       ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", out);
       out.close();
       //验证码值和全路径存入session
       request.getSession().setAttribute("code",map.get("code"));
       request.getSession().setAttribute("filePath",realPath+"/"+fileName+".jpg");
       request.setAttribute("codeImg",fileName+".jpg");
       response.setContentType("text/html; charset=utf-8");
       response.getWriter().print(request.getContextPath()+"/img/code/"+fileName+".jpg");
   }

    //registUI注册界面
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
       codeimg(request,response);
        return "/jsp/register.jsp";
    }

    //loginUI登录界面
    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        codeimg(request,response);
        return "/jsp/login.jsp";
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String userRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取用户输入的验证码
        String code01=request.getParameter("code").toUpperCase();
        //获取存在session中验证码
        String code02= request.getSession().getAttribute("code").toString();
        if (!code01.equals(code02)){
            request.setAttribute("msg","验证码错误！");
            codeimg(request,response);
           // response.sendRedirect(request.getContextPath()+"/UserServlet?method=registUI");
            return "/jsp/register.jsp";
        }
        User user = new User();//获取用户对象
        try {
            //接收客户端信息并存储
            MyBeanUtils.populate(user,request.getParameterMap());
            //给用户其他属性赋值
            user.setUid(UUIDUtils.getId());
            user.setState(0);
            user.setCode(UUIDUtils.getCode());
            //获取传入的密码，进行MD5加密
            String pass=user.getPassword();
            pass=MD5Utils.md5(pass);
            user.setPassword(pass);
            //调用注册方法
            serviceImp.userRegist(user);
            //发送邮件到用户邮箱
            MailUtils.sendMail(user.getEmail(),user.getCode());
            request.setAttribute("msg","您好！账户 "+user.getUsername()+" 注册成功！请到您的邮箱激活！");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","您好！账户 "+user.getUsername()+" 注册失败，请重新注册！");
        }
        String filePath=(String) request.getSession().getAttribute("filePath");
        //注册完成，删除验证码图片
        DeleteFileUtil.deleteFile(filePath);
        return "/jsp/info.jsp";
    }


    /**
     * 检测用户名是否重复
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkUsername(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        try {
            //调用方法，存在返回1,不存在返回null
            String flag=serviceImp.checkUsername(username);
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().println(flag);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *激活用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     * @return
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取激活码
            String code = request.getParameter("code");
            String flag=serviceImp.activeUser(code);
            if ("1".equals(flag)){
                //激活成功,返回登录界面
                request.setAttribute("msg","您的账户激活成功,请登录!");
                return "/jsp/login.jsp";
            }else if("2".equals(flag)){
                //激活错误返回提示页面
                request.setAttribute("msg","您的账户已经激活,无需重复激活!!!");
                return "/jsp/info.jsp";
            }else {
                request.setAttribute("msg","您的激活码无效!!!");
                return "/jsp/info.jsp";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String  userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //获取用户输入的验证码
        String code01=request.getParameter("code").toUpperCase();
        //获取存在session中验证码
        String code02= request.getSession().getAttribute("code").toString();
        if (!code01.equals(code02)){
            request.setAttribute("msg","验证码错误！");
            codeimg(request,response);
            return "/jsp/login.jsp";
        }
        //获得账号和密码并封装
        User user=new User();
        MyBeanUtils.populate(user,request.getParameterMap());
        //获取传入的密码进行加密
        String pass=MD5Utils.md5(user.getPassword());
        user.setPassword(pass);
        User user1=null;
        try {
            user1=serviceImp.userLogin(user);
            //登录成功,存入session中
            request.getSession().setAttribute("loginUser",user1);
            //判断用户是否勾选自动登录和记住用户名
            String auto=request.getParameter("autologin");
            String keepname=request.getParameter("keepname");
            //勾选了自动登录(两者同时勾选则只用自动登录)
            if ("true".equals(auto)){
                //设置cookie
                String value = URLEncoder.encode(user.getUsername()+"-&$-"+user.getPassword(), "UTF-8");
                Cookie cookie=new Cookie("user",value);
                cookie.setMaxAge(60*60*60*24*7);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }else{
                //勾选了记住用户名
                if ("true".equals(keepname)){
                    //设置cookie保存用户名
                    String value = URLEncoder.encode(user.getUsername(), "UTF-8");
                    Cookie cookie=new Cookie("name",value);
                    cookie.setMaxAge(60*60*60*24*7);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }else{
                    //取消勾选记住用户名,清除cookie
                    Cookie cookie=new Cookie("name","");
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }
            }
            String filePath=(String) request.getSession().getAttribute("filePath");
            //登录成功，删除验证码图片
            DeleteFileUtil.deleteFile(filePath);
            response.sendRedirect("index.jsp");
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            //用户登录失败
            String msg=e.getMessage();
            request.setAttribute("msg",msg);
            codeimg(request,response);
            return "/jsp/login.jsp";
        }
    }

    /**
     * 用户退出
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String  outLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //清除session和Cookie
        request.getSession().invalidate();
        Cookie cookie=new Cookie("user","");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        response.sendRedirect("index.jsp");
        return null;
    }
}
