package com.ssafy.api.response.trade;

import com.ssafy.api.request.trade.TradeSectionInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("TradeSectionInfoRes")
public class TradeSectionInfoRes {
    @ApiModelProperty(name="제품 Id", example="productId")
    Long productId;
    @ApiModelProperty(name="판매자 Id", example="sellerId")
    String sellerId;
    @ApiModelProperty(name="기본 가격", example="basePrice")
    Integer basePrice;
    @ApiModelProperty(name="호가 단위", example="priceGap")
    Integer priceGap;

    public static TradeSectionInfoRes of(Long productId, String sellerId, Integer basePrice, Integer priceGap){
        TradeSectionInfoRes tradeSectionInfoRes = new TradeSectionInfoRes();
        tradeSectionInfoRes.setProductId(productId);
        tradeSectionInfoRes.setSellerId(sellerId);
        tradeSectionInfoRes.setBasePrice(basePrice);
        tradeSectionInfoRes.setPriceGap(priceGap);
        return tradeSectionInfoRes;
    }
}
