package com.ssafy.api.request.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("TradeSectionCreateReq")
public class TradeSectionCreateReq {
    @ApiModelProperty(name="판매자 ID", example="seller")
    String seller;

    @ApiModelProperty(name="상품 ID", example="productId")
    Long productId;
}