package com.ssafy.api.request.product;

import io.swagger.annotations.ApiModelProperty;

public class ProductListGetReq {
    @ApiModelProperty(name="관심 상품 id", example="1")
    Long productId;
    @ApiModelProperty(name="상품 분류")
    String categories;
    @ApiModelProperty(name="상품명")
    String productName;
    @ApiModelProperty(name="가격")
    Integer basePrice;
    @ApiModelProperty(name="상품 사진")
    String imageUrl;
    @ApiModelProperty(name="판매 여부")
    Boolean isSold;
}
