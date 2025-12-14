package com.rslakra.appsuite.spring.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Test custom class for Filter testing with complex types.
 *
 * @author Rohtash Lakra
 * @created 1/1/25
 */
public class TestProduct {
    private String productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private LocalDate createdDate;
    private Boolean inStock;

    public TestProduct() {
    }

    public TestProduct(String productId, String productName, BigDecimal price, Integer quantity, LocalDate createdDate, Boolean inStock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.inStock = inStock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "TestProduct{productId='" + productId + "', productName='" + productName + "', price=" + price + ", quantity=" + quantity + ", createdDate=" + createdDate + ", inStock=" + inStock + "}";
    }
}

