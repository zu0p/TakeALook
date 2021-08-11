package com.ssafy.api.request.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@ApiModel("TradeSectionReturnReq")
public class TradeSectionReturnReq {
    @ApiModelProperty(name="구매자 ID", example="buyer")
    String userId;

    @ApiModelProperty(name="상품 ID", example="productId")
    Long productId;
}
