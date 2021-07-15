package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserModifyPatchRequest")

public class UserModifyPatchReq {
    @ApiModelProperty(name="유저 Name", example = "김싸피")
    String name;
    @ApiModelProperty(name="유저 Department", example = "개발부")
    String department;
    @ApiModelProperty(name="유저 Position", example = "대리")
    String position;
}
