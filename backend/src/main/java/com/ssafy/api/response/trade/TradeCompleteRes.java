package com.ssafy.api.response.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel("TradeCompleteRes")
public class TradeCompleteRes {
    @ApiModelProperty(name="상품 id")
    Long productId;
    @ApiModelProperty(name="상품 이름")
    String productName;
    @ApiModelProperty(name="상품 거래일")
    Date tradeDate;
    @ApiModelProperty(name="상대 id")
    String receiver;
    @ApiModelProperty(name="가격")
    Integer price;
    @ApiModelProperty(name="채팅방 번호")
    String roomId;

    public TradeCompleteRes(Long productId, String productName, Date tradeDate, String receiver, Integer price, String roomId) {
        this.productId = productId;
        this.productName = productName;
        this.tradeDate = tradeDate;
        this.receiver = receiver;
        this.price = price;
        this.roomId = roomId;
    }
}
