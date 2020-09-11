package top.suyuanshine.store.web.servlet;


import top.suyuanshine.store.domian.PageModel;
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

@WebServlet(name = "ProductServlet",urlPatterns = "/ProductServlet")
public class ProductServlet extends BaseServlet {
    ProductService impl=new ProductServiceImpl();

    /**
     * 根据商品id获取商品信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findById(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String pid=request.getParameter("pid");
        Product info=impl.findById(pid);
        request.setAttribute("info",info);
        return "jsp/product_info.jsp";
    }

    /**
     * 分页分类查询
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findProductsWithCidAndPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获得cid和num
        String cid=request.getParameter("cid");
        int num=Integer.parseInt(request.getParameter("num"));
        PageModel pm=impl.findProductsWithCidAndPage( cid,num);
        request.setAttribute("page",pm);

        return "jsp/product_list.jsp";
    }

}
