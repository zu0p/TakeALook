package com.ssafy.api.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProductSearchPostReq")
public class ProductSearchPostReq {
    @ApiModelProperty(name="페이지 목록 수", example = "10")
    int size;
    @ApiModelProperty(name="페이지", example = "1")
    int page;
    @ApiModelProperty(name="카테고리", example = "1")
    String categories;
    @ApiModelProperty(name="키워드", example = "1")
    String keyword;
}
