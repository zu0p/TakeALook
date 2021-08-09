package com.ssafy.api.response.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("WishCountGetRes")
public class WishCountGetRes {
    @ApiModelProperty(name="관심 수")
    Long wishCount;

    public static WishCountGetRes of(Long wishCount){
        WishCountGetRes wishCountGetRes = new WishCountGetRes();
        wishCountGetRes.setWishCount(wishCount);
        return wishCountGetRes;
    }
}
