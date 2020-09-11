package top.suyuanshine.store.web.listener;

import top.suyuanshine.store.utils.DeleteFileUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener("SessionListener")
public class SessionListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    /**
     * 监听session销毁
     * @param se
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String filePath=session.getAttribute("filePath").toString();
        //当session销毁时，删除其保存的验证码图片
        DeleteFileUtil.deleteFile(filePath);
        System.out.println("销毁"+filePath);
    }





}
