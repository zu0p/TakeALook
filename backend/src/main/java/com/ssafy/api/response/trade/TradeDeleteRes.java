package com.ssafy.api.response.trade;

import com.ssafy.api.response.wish.WishRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeDeleteRes {
    @ApiModelProperty(name="관심 상품 id")
    Long productId;

    public static TradeDeleteRes of(Long productId){
        TradeDeleteRes tradeDeleteRes = new TradeDeleteRes();
        tradeDeleteRes.setProductId(productId);
        return tradeDeleteRes;
    }
}
