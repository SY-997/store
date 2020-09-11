package top.suyuanshine.store.service.serviceImp;

import top.suyuanshine.store.dao.ProductDao;
import top.suyuanshine.store.domian.PageModel;
import top.suyuanshine.store.domian.Product;
import top.suyuanshine.store.service.ProductService;
import top.suyuanshine.store.service.daoImp.ProductDaoImpl;
import top.suyuanshine.store.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * 实现接口ProductService
 */
public class ProductServiceImpl implements ProductService {
    ProductDao dao=(ProductDao) BeanFactory.createObject("ProductDao");
    @Override
    public List<Product> findHosts() throws SQLException {
        return dao.findHosts();
    }

    @Override
    public List<Product> findNews() throws SQLException {
        return dao.findNews();
    }

    @Override
    public Product findById(String pid) throws SQLException {

        return dao.findById(pid);
    }

    @Override
    public PageModel findProductsWithCidAndPage(String cid, int num) throws SQLException {
        int totalRecords=dao.findTotalRecordsOnPflag(cid);
        PageModel pm=new PageModel(num,totalRecords,12);
        List list=dao.findProductsWithCidAndPage(cid,pm.getStartIndex(),pm.getPageSize());
        pm.setRecords(list);
        pm.setUrl("ProductServlet?method=findProductsWithCidAndPage&cid="+cid);
        return pm;
    }

    @Override
    public PageModel findAllProductByPflag(int num,int pflag) throws SQLException {
        int totalRecords=dao.findAllTotalRecordsByPflag(pflag);
        PageModel pm=new PageModel(num,totalRecords,8);
        List list=dao.findAllProductsAndPageByPflag(pflag,pm.getStartIndex(),pm.getPageSize());
        pm.setRecords(list);
        pm.setUrl("AdminProductServlet?method=findAllProduct&pflag="+pflag);
        return pm;
    }

    @Override
    public void saveProduct(Product product) throws SQLException {
        dao.saveProduct(product);
    }

    @Override
    public void updatePflag(String pid, int pflag) throws SQLException {
        //传入pflag为0，则表示商品需要下架改为1,传入pflag为1则相反
        if (pflag==0){
            pflag=1;
        }else {
            pflag=0;
        }
        dao.updatePflag(pid,pflag);
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        dao.updateProduct(product);
    }

    @Override
    public PageModel findAllProductsWithCidAndPage(String cid, int num) throws SQLException {
        int totalRecords=dao.findTotalRecords(cid);
        PageModel pm=new PageModel(num,totalRecords,9);
        List list=dao.findAllProductsWithCidAndPage(cid,pm.getStartIndex(),pm.getPageSize());
        pm.setRecords(list);
        pm.setUrl("AdminProductServlet?method=findAllProductBycid&cid="+cid);
        return pm;
    }
}
