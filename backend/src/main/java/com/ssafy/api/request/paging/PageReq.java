package com.ssafy.api.request.paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PageRequest")
public class PageReq {
    @ApiModelProperty(name="페이지 목록 수", example = "10")
    int size;
    @ApiModelProperty(name="페이지", example = "1")
    int page;
}
