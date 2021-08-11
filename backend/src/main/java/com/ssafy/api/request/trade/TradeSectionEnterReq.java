package com.ssafy.api.request.trade;

import com.ssafy.api.response.trade.TradeSectionCreateRes;
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
public class TradeSectionEnterReq {
    @ApiModelProperty(name="구매자 ID", example="buyer")
    String userId;
    @ApiModelProperty(name="상품 ID", example="productId")
    Long productId;

    public static TradeSectionEnterReq of(String user, Long productId){
        TradeSectionEnterReq tradeSectionEnterReq = new TradeSectionEnterReq();
        tradeSectionEnterReq.setUserId(user);
        tradeSectionEnterReq.setProductId(productId);
        return tradeSectionEnterReq;
    }
}
