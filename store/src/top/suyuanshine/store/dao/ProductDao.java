package top.suyuanshine.store.dao;

import top.suyuanshine.store.domian.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    /**
     * 查询当前最新的九件商品
     * @return
     * @throws SQLException
     */
    List<Product> findNews() throws SQLException;

    /**
     * 查询当前热门的九件商品
     * @return
     * @throws SQLException
     */
    List<Product> findHosts() throws SQLException;

    /**
     * 根据id查找商品
     * @param pid
     * @return
     */
    Product findById(String pid) throws SQLException;

    /**
     * 根据分类id查询在架商品数量
     * @param cid
     * @return
     * @throws SQLException
     */
    int findTotalRecordsOnPflag(String cid) throws SQLException;

    /**
     * 查询分页信息，分页查询当前类别在架商品
     * @param cid 分类id
     * @param startIndex 开始数
     * @param pageSize 每页显示数
     * @return
     */
    List findProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws SQLException;

    /**
     * 查询商品总数
     * @param pflag 商品是否下架（0：没有下架，1：下架）
     * @return
     * @throws SQLException
     */
    int findAllTotalRecordsByPflag(int pflag) throws SQLException;

    /**
     * 分页查询所有已上架商品
     * @param pflag 商品是否下架（0：没有下架，1：下架）
     * @param startIndex 页数
     * @param pageSize 每页条数
     * @return
     */
    List findAllProductsAndPageByPflag(int pflag,int startIndex, int pageSize) throws SQLException;

    /**
     * 添加商品
     * @param product
     */
    void saveProduct(Product product) throws SQLException;

    /**
     * 更新商品在架装态
     * @param pid 商品id
     * @param pflag 当前商品状态
     * @throws SQLException
     */
    void updatePflag(String pid, int pflag) throws SQLException;

    /**
     * 更新商品信息
     * @param product
     * @throws SQLException
     */
    void updateProduct(Product product) throws SQLException;

    /**
     * 根据分类id查询全部商品数量
     * @param cid
     * @return
     * @throws SQLException
     */
    int findTotalRecords(String cid) throws SQLException;

    /**
     * 管理员根据商品类别查询所有商品
     * @param cid
     * @param startIndex
     * @param pageSize
     * @return
     * @throws SQLException
     */
    List findAllProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws SQLException;
}
