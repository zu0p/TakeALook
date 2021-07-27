package com.ssafy.api.response.wish;

import com.ssafy.db.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("WishListGetRes")
public class WishListGetRes {
    @ApiModelProperty(name="상품 id")
    Long productId;
    @ApiModelProperty(name="판매자 id")
    String seller;
    @ApiModelProperty(name="상품 이름")
    String productName;
    @ApiModelProperty(name="상품 기준가")
    Integer basePrice;
    @ApiModelProperty(name="상품 카테고리")
    String categories;
    @ApiModelProperty(name="상품 설명")
    String description;
    @ApiModelProperty(name="상품 상태")
    String state;
    @ApiModelProperty(name="상품 이미지")
    String imageUrl;
    @ApiModelProperty(name="상품 판매 여부")
    Boolean isSold;

    public WishListGetRes(Long productId, String seller, String productName, Integer basePrice, String categories, String description, String state, String imageUrl, Boolean isSold) {
        this.productId = productId;
        this.seller = seller;
        this.productName = productName;
        this.basePrice = basePrice;
        this.categories = categories;
        this.description = description;
        this.state = state;
        this.imageUrl = imageUrl;
        this.isSold = isSold;
    }
}
