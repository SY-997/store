package top.suyuanshine.store.service;

import top.suyuanshine.store.domian.PageModel;
import top.suyuanshine.store.domian.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    /**
     * 查找最热门商品
     * @return
     * @throws SQLException
     */
    List<Product> findHosts() throws SQLException;

    /**
     * 查找最新最热门商品
     * @return
     * @throws SQLException
     */
    List<Product> findNews() throws SQLException;

    /**
     * 通过id查找商品
     * @param pid
     * @return
     */
    Product findById(String pid) throws SQLException;

    /**
     * 分页查询当前类别在架商品
     * @param cid 类别
     * @param num 当前页
     * @return
     */
    PageModel findProductsWithCidAndPage(String cid, int num) throws SQLException;

    /**
     * 查询所有商品
     * @param num 查询页数
     * @param pflag 商品是否下架（0：没有下架，1：下架）
     * @return
     * @throws SQLException
     */
    PageModel findAllProductByPflag(int num,int pflag) throws SQLException;

    /**
     * 添加商品
     * @param product
     * @throws SQLException
     */
    void saveProduct(Product product) throws SQLException;

    /**
     * 更新商品在架状态
     * @param pid 商品id
     * @param pflag 当前状态
     */
    void updatePflag(String pid, int pflag) throws SQLException;

    /**
     * 更新商品信息
     * @param product
     * @throws SQLException
     */
    void updateProduct(Product product) throws SQLException;

    /**
     * 管理员分页查找当前类别所有商品
     * @param cid 类别id
     * @param num 分页
     * @return
     */
    PageModel findAllProductsWithCidAndPage(String cid, int num) throws SQLException;
}
