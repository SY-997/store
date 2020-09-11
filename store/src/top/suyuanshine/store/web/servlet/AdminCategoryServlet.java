package top.suyuanshine.store.web.servlet;

import top.suyuanshine.store.domian.Category;
import top.suyuanshine.store.service.CategoryService;
import top.suyuanshine.store.service.serviceImp.CategoryServiceImpl;
import top.suyuanshine.store.utils.MyBeanUtils;
import top.suyuanshine.store.utils.UUIDUtils;
import top.suyuanshine.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 管理员分类管理
 */
@WebServlet(name = "AdminCategoryServlet",urlPatterns = "/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    CategoryService categoryService=new CategoryServiceImpl();


    /**
     * 添加分类的界面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addCategoryUI(HttpServletRequest request,HttpServletResponse response) throws Exception{
        return "/admin/category/add.jsp";
    }
    /**
     * 编辑分类的界面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String editCategoryUI(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取该分类的cid,并查询
        String cid=request.getParameter("cid");
        Category category=categoryService.findCategoryByCid(cid);
        request.setAttribute("category",category);
        return "/admin/category/edit.jsp";
    }

    /**
     * 获取全部分类信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findAllCats(HttpServletRequest request,HttpServletResponse response) throws Exception{

        List<Category> list = categoryService.findAll();
        //存入分类信息
        request.setAttribute("allCats",list);
        //转发
        return "/admin/category/list.jsp";
    }

    /**
     * 返回给分类查找商品使用
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findAllCats02(HttpServletRequest request,HttpServletResponse response) throws Exception{

        List<Category> list = categoryService.findAll();
        //存入分类信息
        request.setAttribute("allCats",list);
        //转发
        return "/admin/product/cateList.jsp";
    }



    /**
     * 添加分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addCategory(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //获取添加的分类名
        String cname=request.getParameter("cname");
        Category category=new Category();
        category.setCname(cname);
        category.setCid(UUIDUtils.getId());
        categoryService.addCategory(category);
        response.sendRedirect("AdminCategoryServlet?method=findAllCats");
        return null;
    }

    /**
     * 重命名分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String editCategory(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Category category=new Category();
        //获取需要更改的信息
        MyBeanUtils.populate(category,request.getParameterMap());
        categoryService.editCategory(category);
        response.sendRedirect("AdminCategoryServlet?method=findAllCats");
        return null;
    }

    /**
     * 删除分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String deleteCategoryByCid(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //获取需要 删除的分类id
        String cid=request.getParameter("cid");
        categoryService.deleteCategoryByCid(cid);
        response.sendRedirect("AdminCategoryServlet?method=findAllCats");
        return null;
    }


}
