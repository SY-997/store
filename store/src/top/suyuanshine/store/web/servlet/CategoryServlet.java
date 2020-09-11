package top.suyuanshine.store.web.servlet;

import redis.clients.jedis.Jedis;
import top.suyuanshine.store.domian.Category;
import top.suyuanshine.store.service.CategoryService;
import top.suyuanshine.store.service.serviceImp.CategoryServiceImpl;
import top.suyuanshine.store.utils.JedisUtils;
import top.suyuanshine.store.utils.JsonUtil;
import top.suyuanshine.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    /**
     * 查询所有的分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findCategory(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //连接Redis
        Jedis jedis = JedisUtils.getJedis();
        String jsons = jedis.get("allCats");
        //如果Redis没有数据
        if (jsons==null||"".equals(jsons)){
            // 调用业务方法
            CategoryService categoryService = new CategoryServiceImpl();
            List<Category> allCategory = categoryService.findAll();
            jsons = JsonUtil.list2json(allCategory);
            jedis.set("allCats",jsons);
        }
        JedisUtils.closeJedis(jedis);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(jsons);
        return null;
    }

}
