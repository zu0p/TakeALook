package com.ssafy.api.response.trade;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TradeSectionCreateRes")
public class TradeSectionCreateRes {
    @ApiModelProperty(name="room info")
    String room;

    public static TradeSectionCreateRes of(String sellerId, Long productId){
        TradeSectionCreateRes tradeSectionCreateRes = new TradeSectionCreateRes();
        tradeSectionCreateRes.setRoom("RoomCreatedBy"+sellerId+productId);
        return tradeSectionCreateRes;
    }
}