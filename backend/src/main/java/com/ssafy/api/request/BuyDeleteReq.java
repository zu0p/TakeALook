package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@ApiModel("BuyDeleteRequest")
public class BuyDeleteReq {
    @ApiModelProperty(name="구매상품 ID", example="1")
    Long id;

    @ApiModelProperty(name="구매자 ID", example="ssafy_web")
    String buyer;

    @ApiModelProperty(name="판매자 ID", example="ssafy_web")
    String seller;
}
