package com.ssafy.api.request.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("BuyDeleteRequest")
public class TradeDeleteReq {
    @ApiModelProperty(name="구매상품 ID", example="1")
    Long productId;
}
