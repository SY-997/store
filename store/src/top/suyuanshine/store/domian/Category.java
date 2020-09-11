package top.suyuanshine.store.domian;

/**
 * 商品分类信息
 */
public class Category {
    private String cid; //分类id
    private String cname; //分类名

    public Category() {
    }

    public Category(String cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
