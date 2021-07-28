package com.ssafy.api.response.trade;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

public class TradeListGetRes {
    @ApiModelProperty(name="상품 id")
    Long productId;
    @ApiModelProperty(name="상품 이름")
    String productName;
    @ApiModelProperty(name="상품 거래일")
    LocalDate tradeDate;
    @ApiModelProperty(name="판매자 id")
    String seller;
    @ApiModelProperty(name="구매자 id")
    String buyer;
    @ApiModelProperty(name="가격")
    Integer price;
    @ApiModelProperty(name="상품 카테고리")
    String categories;
    @ApiModelProperty(name="상품 이미지")
    String imageUrl;
    @ApiModelProperty(name="상품 판매 여부")
    Boolean isSold;

    public TradeListGetRes(Long productId, String productName, LocalDate tradeDate, String seller,
                           String buyer, Integer price, String categories, String imageUrl, Boolean isSold) {
        this.productId = productId;
        this.productName = productName;
        this.tradeDate = tradeDate;
        this.seller = seller;
        this.buyer = buyer;
        this.price = price;
        this.categories = categories;
        this.imageUrl = imageUrl;
        this.isSold = isSold;
    }
}
