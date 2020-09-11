package top.suyuanshine.store.dao;

import top.suyuanshine.store.domian.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 处理商品分类
 */
public interface CategoryDao {

    /**
     * 查询所有分类
     * @return
     * @throws SQLException
     */
    List<Category> findAll() throws SQLException;

    /**
     * 添加商品分类
     * @param category
     */
    void addCategory(Category category) throws SQLException;

    /**
     * 通过cid查询分类
     * @param cid
     * @return
     */
    Category findCategoryByCid(String cid) throws SQLException;

    /**
     * 编辑分类信息
     * @param category
     */
    void editCategory(Category category) throws SQLException;

    /**
     * 删除分类信息
     * @param cid
     */
    void deleteCategoryByCid(String cid) throws SQLException;
}
