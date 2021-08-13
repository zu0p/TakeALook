package com.ssafy.api.request.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("TradeSectionInfoReq")
public class TradeSectionUpdatePriceReq {
    @ApiModelProperty(name="max price", example="curPrice")
    Integer curPrice;
    @ApiModelProperty(name="방 번호", example="room")
    String room;

    public static TradeSectionUpdatePriceReq of(Integer curPrice, String room){
        TradeSectionUpdatePriceReq tradeSectionUpdatePriceReq = new TradeSectionUpdatePriceReq();
        tradeSectionUpdatePriceReq.setCurPrice(curPrice);
        tradeSectionUpdatePriceReq.setRoom(room);
        return tradeSectionUpdatePriceReq;
    }
}
