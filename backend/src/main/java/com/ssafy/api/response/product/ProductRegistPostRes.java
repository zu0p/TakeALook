package com.ssafy.api.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProductRegistPostRes")
public class ProductRegistPostRes {
    @ApiModelProperty(name="상품 판매자")
    Long productId;

    public static ProductRegistPostRes of(Long productId){
        ProductRegistPostRes res = new ProductRegistPostRes();
        res.setProductId(productId);

        return res;
    }
}
