package top.suyuanshine.store.service.serviceImp;

import redis.clients.jedis.Jedis;
import top.suyuanshine.store.dao.CategoryDao;
import top.suyuanshine.store.domian.Category;
import top.suyuanshine.store.service.CategoryService;
import top.suyuanshine.store.service.daoImp.CategoryDaoImpl;
import top.suyuanshine.store.utils.BeanFactory;
import top.suyuanshine.store.utils.JedisUtils;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao=(CategoryDao) BeanFactory.createObject("CategoryDao");

    /**
     * 实现查询所有
     * @return
     * @throws SQLException
     */
    @Override
    public List<Category> findAll() throws SQLException {

        return  dao.findAll();
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        dao.addCategory(category);
        //增加后删除Redis缓存,客户端访问时从新获取
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeJedis(jedis);
    }

    @Override
    public Category findCategoryByCid(String cid) throws SQLException {

        return dao.findCategoryByCid(cid);
    }

    @Override
    public void editCategory(Category category) throws SQLException {

        dao.editCategory(category);
        //更改后删除Redis缓存,客户端访问时从新获取
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeJedis(jedis);
    }

    @Override
    public void deleteCategoryByCid(String cid) throws SQLException {
        dao.deleteCategoryByCid(cid);
        //更改后删除Redis缓存,客户端访问时从新获取
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeJedis(jedis);
    }
}
