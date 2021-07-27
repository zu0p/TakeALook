package com.ssafy.api.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@ApiModel("ProductUpdatePatchRequest")
public class ProductUpdatePatchReq {
    @ApiModelProperty(name="판매자 userId", example = "userId")
    String userId;

    @ApiModelProperty(name="판매 상품명 productName", example = "your_product_name")
    String productName;

    @ApiModelProperty(name="판매 가격 basePrice", example = "your_base_price")
    Integer basePrice;

    @ApiModelProperty(name="상품 분류 categories", example = "your_categories")
    String categories;

    @ApiModelProperty(name="상품 설명 description", example = "your_description")
    String description;

    @ApiModelProperty(name="상품 상태 state", example = "your_state")
    String state;

    @ApiModelProperty(name="상품 사진 imageUrl", example = "your_product_image")
    String imageUrl;

    @ApiModelProperty(name="상품 판매 여부 isSold", example = "isSold??")
    Boolean isSold;

    @ApiModelProperty(name="상품 등록 시간 registTime", example = "regist time")
    LocalDate registTime;

    @ApiModelProperty(name="거래 방 예약 시간 reserveTime", example = "reserve time")
    LocalDate reserveTime;

    @ApiModelProperty(name="거래 방 제한 시간 restrictTime", example = "restrict time")
    LocalDate restrictTime;
}
