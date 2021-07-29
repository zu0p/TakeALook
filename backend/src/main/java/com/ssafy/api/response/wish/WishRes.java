package com.ssafy.api.response.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("WishResonse")
public class WishRes {
    @ApiModelProperty(name="관심 상품 id")
    Long productId;

    public static WishRes of(Long productId){
        WishRes wishRegistPostRes = new WishRes();
        wishRegistPostRes.setProductId(productId);
        return wishRegistPostRes;
    }
}
