package tech.buildrun.btgpactual.orderms.entity;

import java.math.BigDecimal;

public class OrderItem {

    private String product;
    private Integer quantity;
    private BigDecimal price;

    public OrderItem() {
    }

    public OrderItem(String product, Integer quantity, BigDecimal price) {
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
