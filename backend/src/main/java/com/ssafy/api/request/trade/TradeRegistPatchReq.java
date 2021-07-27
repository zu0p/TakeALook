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
@ApiModel("BuyRegistPatchReq")
public class TradeRegistPatchReq {
    @ApiModelProperty(name="구매자 ID", example="ssafy_web")
    String buyer;
    @ApiModelProperty(name="가격", example="100000")
    Integer price;
    @ApiModelProperty(name="상품 ID", example="1")
    Long product;
    @ApiModelProperty(name="판매자 ID", example="ssafy_web")
    String seller;
    @ApiModelProperty(name="거래한 시간", example="?")
    LocalDate trade_date;
}
