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
public class TradeSectionInfoReq {
    @ApiModelProperty(name="구매자 ID", example="buyer")
    String room;

    public static TradeSectionInfoReq of(String roomName){
        TradeSectionInfoReq tradeSectionInfoReq = new TradeSectionInfoReq();
        tradeSectionInfoReq.setRoom(roomName);
        return tradeSectionInfoReq;
    }
}
