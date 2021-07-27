package com.ssafy.api.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserDeleteResponse")
public class UserDeleteRes {
    @ApiModelProperty(name="유저 id")
    String userId;

    public static UserDeleteRes of(String userId){
        UserDeleteRes res = new UserDeleteRes();
        res.setUserId(userId);
        return res;
    }
}
