package com.ssafy.api.response.product;

import com.ssafy.db.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 상품 API ([GET] /api/v1/product) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ProductRegistResponse")
public class ProductRegistPostRes {
    @ApiModelProperty(name="상품 User ID")
    String userId;
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

    public static ProductRegistPostRes of(Product product) {
        ProductRegistPostRes res = new ProductRegistPostRes();
        res.setUserId(product.getUser().getUserId());
        res.setProductName(product.getProductName());
        res.setBasePrice(product.getBasePrice());
        res.setCategories(product.getCategories());
        res.setDescription(product.getDescription());
        res.setState(product.getState());
        res.setImageUrl(product.getImageUrl());
        res.setIsSold(product.getIsSold());
        res.setRegistTime(product.getRegistTime());
        res.setReserveTime(product.getReserveTime());
        res.setRestrictTime(product.getRestrictTime());
        return res;
    }

}