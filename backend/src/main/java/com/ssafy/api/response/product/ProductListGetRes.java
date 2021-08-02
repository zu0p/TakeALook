package com.ssafy.api.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@ApiModel("ProductListGetResponse")
public class ProductListGetRes {
    @ApiModelProperty(name="상품 id")
    Long productId;
    @ApiModelProperty(name="상품 판매자")
    String seller;
    @ApiModelProperty(name="상품명")
    String productName;
    @ApiModelProperty(name="가격")
    Integer basePrice;
    @ApiModelProperty(name="상품 분류")
    String categories;
    @ApiModelProperty(name="상품 설명")
    String description;
    @ApiModelProperty(name="상품 상태")
    String state;
    @ApiModelProperty(name="상품 사진")
    String imageUrl;
    @ApiModelProperty(name="판매 여부")
    Boolean isSold;
    @ApiModelProperty(name="상품 등록 시간")
    LocalDate registTime;
    @ApiModelProperty(name="거래 방 예약 시간")
    LocalDate reserveTime;
    @ApiModelProperty(name="거래 방 제한 시간")
    LocalDate restrictTime;

    public ProductListGetRes(Long productId, String seller, String productName, Integer basePrice, String categories,
                             String description, String state, String imageUrl, Boolean isSold, LocalDate registTime,
                             LocalDate reserveTime) {
        this.productId = productId;
        this.seller = seller;
        this.productName = productName;
        this.basePrice = basePrice;
        this.categories = categories;
        this.description = description;
        this.state = state;
        this.imageUrl = imageUrl;
        this.isSold = isSold;
        this.registTime = registTime;
        this.reserveTime = reserveTime;
    }
}
