package com.ssafy.api.response.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TradeResponse")
public class TradeRes {
    @ApiModelProperty(name="관심 상품 id")
    Long productId;

    public static TradeRes of(Long productId){
        TradeRes tradeRes = new TradeRes();
        tradeRes.setProductId(productId);
        return tradeRes;
    }
}
