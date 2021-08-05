package com.ssafy.api.response.wish;

import com.ssafy.db.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ApiModel("WishListGetResponse")
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
    @ApiModelProperty(name="상품 이미지")
    String imageUrl;
    @ApiModelProperty(name="상품 판매 여부")
    Boolean isSold;
    @ApiModelProperty(name="거래 방 예약 시간")
    Date reserveTime;

    public WishListGetRes(Long productId, String seller, String productName, Integer basePrice, String categories, String imageUrl, Boolean isSold, Date reserveTime) {
        this.productId = productId;
        this.seller = seller;
        this.productName = productName;
        this.basePrice = basePrice;
        this.categories = categories;
        this.imageUrl = imageUrl;
        this.isSold = isSold;
        this.reserveTime=reserveTime;
    }
}
