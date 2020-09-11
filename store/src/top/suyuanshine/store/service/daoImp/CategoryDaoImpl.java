package top.suyuanshine.store.service.daoImp;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.suyuanshine.store.dao.CategoryDao;
import top.suyuanshine.store.domian.Category;
import top.suyuanshine.store.utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findAll() throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("select * from category",new BeanListHandler<Category>(Category.class));
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        runner.update("insert into category value(?,?)",category.getCid(),category.getCname());

    }

    @Override
    public Category findCategoryByCid(String cid) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("select * from category where cid=?",new BeanHandler<Category>(Category.class),cid);
    }

    @Override
    public void editCategory(Category category) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        runner.update("update category set cname=? where cid=?",category.getCname(),category.getCid());
    }

    @Override
    public void deleteCategoryByCid(String cid) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        //删除该分类下所有商品
        String sql="DELETE FROM product WHERE cid=?";
        runner.update(sql,cid);
        //删除该分类
        sql="DELETE FROM category WHERE cid=?";
        runner.update(sql,cid);
    }
}
