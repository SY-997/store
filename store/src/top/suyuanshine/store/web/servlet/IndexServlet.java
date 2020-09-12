package top.suyuanshine.store.web.servlet;

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
import java.util.List;

@WebServlet(name = "IndexServlet",urlPatterns = "/IndexServlet")
public class IndexServlet extends BaseServlet {
    ProductService impl=new ProductServiceImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //查询热门和最新商品
        List<Product> hosts=impl.findHosts();
        List<Product> news=impl.findNews();
        req.setAttribute("hosts",hosts);
        req.setAttribute("news",news);
        return "jsp/index.jsp";
    }

    //有cookie登录
    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "jsp/index.jsp";
    }
    //有cookie注册
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
        return "jsp/index.jsp";
    }
}
