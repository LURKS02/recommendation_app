package com.example.recommendationapp.cls;

import java.io.Serializable;

public class UserProduct implements Serializable {
    private Integer userId;
    private Integer productId;

    public UserProduct(Integer userId, Integer productId){
        super();
        this.userId = userId;
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "UserProduct{" +
                "userId=" + userId +
                ", productId=" + productId +
                '}';
    }
}
