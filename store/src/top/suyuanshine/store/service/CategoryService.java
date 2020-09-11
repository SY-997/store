package top.suyuanshine.store.service;

import top.suyuanshine.store.domian.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 处理商品分类
 */
public interface CategoryService {

    /**
     * 查询所有分类
     * @return
     * @throws SQLException
     */
    List<Category> findAll() throws SQLException;

    /**
     * 添加分类
     * @param category
     * @throws SQLException
     */
    void addCategory(Category category) throws SQLException;

    /**
     * 通过cid查询分类
     * @param cid
     * @return
     * @throws SQLException
     */
    Category findCategoryByCid(String cid) throws SQLException;

    /**
     * 更改分类信息
     * @param category
     */
    void editCategory(Category category) throws SQLException;

    /**
     * 删除分类
     * @param cid
     */
    void deleteCategoryByCid(String cid) throws SQLException;
}
