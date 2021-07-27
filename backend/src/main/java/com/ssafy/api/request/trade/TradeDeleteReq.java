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
    @ApiModelProperty(name="구매자 ID", example="ssafy1")
    String buyer;
    @ApiModelProperty(name="판매자 ID", example="ssafy2")
    String seller;
}
