package top.suyuanshine.store.service.daoImp;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.suyuanshine.store.dao.ProductDao;
import top.suyuanshine.store.domian.Product;
import top.suyuanshine.store.utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * 实现接口ProductDao
 */
public class ProductDaoImpl implements ProductDao {

    @Override
    public List<Product> findNews() throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0 ,9",new BeanListHandler<Product>(Product.class));
    }

    @Override
    public List<Product> findHosts() throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0 ,9 ",new BeanListHandler<Product>(Product.class));
    }

    @Override
    public Product findById(String pid) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("SELECT * FROM product WHERE pid=? ",new BeanHandler<Product>(Product.class),pid);
    }

    @Override
    public int findTotalRecordsOnPflag(String cid) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        Long num =(long)runner.query("select count(*) from product where pflag=0 and cid=?",new ScalarHandler(),cid);
        return num.intValue();
    }

    @Override
    public List findProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("select * from product where pflag=0 and cid=? limit ?,?",new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);
    }

    @Override
    public int findAllTotalRecordsByPflag(int pflag) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        Long num =(long)runner.query("select count(*) from product where pflag=?",new ScalarHandler(),pflag);
        return num.intValue();
    }

    @Override
    public List findAllProductsAndPageByPflag(int pflag,int startIndex, int pageSize) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("select * from product WHERE pflag=?  ORDER BY pdate DESC LIMIT ?,?",new BeanListHandler<Product>(Product.class),pflag,startIndex,pageSize);
    }

    @Override
    public void saveProduct(Product product) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        Object[] param={product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
        runner.update("insert into product values(?,?,?,?,?,?,?,?,?,?)",param);
    }

    @Override
    public void updatePflag(String pid, int pflag) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        runner.update("update product set pflag=? where pid=?",pflag,pid);

    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="update product set pname=? ,market_price=? ,shop_price = ?, pimage = ?,  is_hot = ?, pdesc =? ,pflag = ?, cid = ? WHERE pid = ?";
        Object[] param={product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid(),product.getPid()};
        runner.update(sql,param);
    }

    @Override
    public int findTotalRecords(String cid) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        Long num =(long)runner.query("select count(*) from product where  cid=?",new ScalarHandler(),cid);
        return num.intValue();
    }

    @Override
    public List findAllProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws SQLException {
        QueryRunner runner=new QueryRunner(JDBCUtils.getDataSource());
        return runner.query("select * from product where  cid=? ORDER BY pdate DESC limit ?,?",new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);

    }
}
