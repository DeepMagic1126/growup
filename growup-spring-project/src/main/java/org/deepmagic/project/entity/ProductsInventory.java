package org.deepmagic.project.entity;

import java.math.BigDecimal;

/**
 * ProductsInventory
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/7 22:09
 */
public class ProductsInventory {

    private Long productId;

    private String productName;

    private Long quantity;

    private BigDecimal unitPrice;

    private String location;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
