package top.suyuanshine.store.web.servlet;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import top.suyuanshine.store.domian.Category;
import top.suyuanshine.store.domian.PageModel;
import top.suyuanshine.store.domian.Product;
import top.suyuanshine.store.service.CategoryService;
import top.suyuanshine.store.service.ProductService;
import top.suyuanshine.store.service.serviceImp.CategoryServiceImpl;
import top.suyuanshine.store.service.serviceImp.ProductServiceImpl;
import top.suyuanshine.store.utils.UUIDUtils;
import top.suyuanshine.store.utils.UploadUtils;
import top.suyuanshine.store.web.base.BaseServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 管理员商品管理
 */
@WebServlet(name = "AdminProductServlet",urlPatterns = "/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
    ProductService productService=new ProductServiceImpl();
    /**
     * 查询出所有商品
     * @param request
     * @param response
     * @return
     */
    public String findAllProduct(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //查询页数
        int num=Integer.parseInt(request.getParameter("num"));
        //商品是否下架（0：没有下架，1：下架）
        int pflag=Integer.parseInt(request.getParameter("pflag"));
        PageModel page =productService.findAllProductByPflag(num,pflag);
        request.setAttribute("page",page);
        if (pflag==0){
            return "/admin/product/list.jsp";
        }else {
            return "/admin/product/pushDown_list.jsp";
        }

    }

    /**
     * 根据分类查询所有商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findAllProductBycid(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //查询页数
        int num=Integer.parseInt(request.getParameter("num"));
        //分类id
       String cid=request.getParameter("cid");
       CategoryService categoryService=new CategoryServiceImpl();
        Category category=categoryService.findCategoryByCid(cid);
        PageModel page =productService.findAllProductsWithCidAndPage(cid,num);
        request.setAttribute("category",category);
        request.setAttribute("page",page);
        return "/admin/product/cateProductList.jsp";
    }

    /**
     * 下架或者上架商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String pushDownOrPushProduct(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //根据传来的pflag判断是否上架或者下架 //传入pflag为0，则表示商品需要下架改为1,传入pflag为1则相反
        int pflag=Integer.parseInt(request.getParameter("pflag"));
        //需要操作的商品id
        String pid=request.getParameter("pid");
        productService.updatePflag(pid,pflag);
        if (pflag==0){
            response.sendRedirect("AdminProductServlet?method=findAllProduct&pflag=1&num=1");
            return null;
        }else {
            response.sendRedirect("AdminProductServlet?method=findAllProduct&pflag=0&num=1");
            return null;
        }
    }


    /**
     * 添加商品界面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取分类id
        String cid=request.getParameter("cid");
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list=new ArrayList<Category>();
        if (cid==null){
            //查询出所有分类返回给页面
            list = categoryService.findAll();
        }else{
            Category category = categoryService.findCategoryByCid(cid);
            list.add(category);
        }
        request.setAttribute("list",list);
        return "/admin/product/add.jsp";
    }


    /**
     * 编辑商品界面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String editProductUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String pid=request.getParameter("pid");
        //根据id查询出商品
        Product product = productService.findById(pid);
        //查询出所有分类返回给页面
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.findAll();
        request.setAttribute("list",list);
        request.setAttribute("product",product);
        return "/admin/product/edit.jsp";
    }

    /**
     * 添加上传商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Product product=new Product();
        //存放提交的内容
        Map<String,String> map=new HashMap<String,String>();
        //用工具执行request.getInputStream(),对上传数据拆分封装
        DiskFileItemFactory fac=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(fac);
        List<FileItem> list = upload.parseRequest(request);
        //遍历提交的数据
        for (FileItem item:list) {
            //如果是普通项,则把name作为键名，内容作为值存放到map
            if (item.isFormField()){
                map.put(item.getFieldName(),item.getString("utf-8"));
            }else{ //上传项
                //获取上传的文件名
                String fileName=item.getName();
                //改变文件名
                 fileName= UploadUtils.getUUIDName(fileName);
                //通过FileItem获取到输入流对象,通过输入流可以获取到图片二进制数据
                InputStream is=item.getInputStream();
                //获取到项目下文件上传的真实路径--products/3
                String realPath=getServletContext().getRealPath("/products/3/");
                //通过文件名生成一个随机八层子目录----作用-1.防止文件名重复--2.防止统一层目录下文件过多或者目录过多造成系统性能损耗
                String dir=UploadUtils.getDir(fileName);
                String path=realPath+dir;
                //创建目录
                File newDir=new File(path);
                if (!newDir.exists()){
                    //不存在则创建
                    newDir.mkdirs();
                }
                //在服务端创建一个空文件(后缀必须和上传到服务端的文件名后缀一致)
                File newFile=new File(newDir,fileName);
                if (!newFile.exists()){
                    //不存在则创建
                    newFile.createNewFile();
                }
                //建立和空文件对应的输出流
                OutputStream os=new FileOutputStream(newFile);
                //将输入流中的数据刷到输出流中
                IOUtils.copy(is,os);
                //释放资源
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);
                //将图片路径存入map
                map.put("pimage","/products/3/"+dir+"/"+fileName);

            }
        }
        //利用BeanUtils将MAP中的数据填充到Product对象上
        BeanUtils.populate(product, map);
        product.setPid(UUIDUtils.getId());
        //设置上传时间
        product.setPdate(new Date());
        product.setPflag(0);
        //调用service，存入数据库
        productService.saveProduct(product);
        //返回商品列表
        response.sendRedirect("AdminProductServlet?method=findAllProduct&pflag=0&num=1");
        return null;
    }

    /**
     * 更新商品信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Product product=new Product();
        //存放提交的内容
        Map<String,String> map=new HashMap<String,String>();
        //用工具执行request.getInputStream(),对上传数据拆分封装
        DiskFileItemFactory fac=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(fac);
        List<FileItem> list = upload.parseRequest(request);
        //遍历提交的数据
        for (FileItem item:list) {
            //如果是普通项,则把name作为键名，内容作为值存放到map
            if (item.isFormField()){
                map.put(item.getFieldName(),item.getString("utf-8"));
            }else{ //上传项
                //获取上传的文件名
                String fileName=item.getName();
                //文件名不为空，即用户上传了文件
                if (fileName!=null&&fileName.length()!=0) {
                    //改变文件名
                    fileName = UploadUtils.getUUIDName(fileName);
                    //通过FileItem获取到输入流对象,通过输入流可以获取到图片二进制数据
                    InputStream is = item.getInputStream();
                    //获取到项目下文件上传的真实路径--products/3
                    String realPath = getServletContext().getRealPath("/products/3/");
                    //通过文件名生成一个随机八层子目录----作用-1.防止文件名重复--2.防止统一层目录下文件过多或者目录过多造成系统性能损耗
                    String dir = UploadUtils.getDir(fileName);
                    String path = realPath + dir;
                    //创建目录
                    File newDir = new File(path);
                    if (!newDir.exists()) {
                        //不存在则创建
                        newDir.mkdirs();
                    }
                    //在服务端创建一个空文件(后缀必须和上传到服务端的文件名后缀一致)
                    File newFile = new File(newDir, fileName);
                    if (!newFile.exists()) {
                        //不存在则创建
                        newFile.createNewFile();
                    }
                    //建立和空文件对应的输出流
                    OutputStream os = new FileOutputStream(newFile);
                    //将输入流中的数据刷到输出流中
                    IOUtils.copy(is, os);
                    //释放资源
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(os);
                    //将图片路径存入map
                    map.put("pimage", "/products/3/" + dir + "/" + fileName);
                }

            }
        }
        //利用BeanUtils将MAP中的数据填充到Product对象上
        BeanUtils.populate(product, map);
        //调用service，存入数据库
        productService.updateProduct(product);
        //返回商品列表
        response.sendRedirect("AdminProductServlet?method=findAllProduct&pflag="+product.getPflag()+"&num=1");
        return null;
    }

}
