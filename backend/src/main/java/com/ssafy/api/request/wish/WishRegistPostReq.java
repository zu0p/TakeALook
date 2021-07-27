package com.ssafy.api.request.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("WishRegistPostReq")
public class WishRegistPostReq {
    @ApiModelProperty(name="유저 id", example="2")
    Long userId;
    @ApiModelProperty(name="관심 상품 id", example="1")
    Long productId;
}
