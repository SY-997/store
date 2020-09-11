package top.suyuanshine.store.domian;

/**
 * 订单详情
 */
public class OrderItem {
    private String itemid;//id
    private int quantity;//商品数量
    private double total; //小计

    private Product product;//用于获取商品id
    private Order order;//用于获取所属的订单编号

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
