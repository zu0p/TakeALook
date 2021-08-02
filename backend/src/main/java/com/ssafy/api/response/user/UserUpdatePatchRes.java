package com.ssafy.api.response.user;

import com.ssafy.api.request.user.UserUpdatePatchReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserUpdatePatchResponse")
public class UserUpdatePatchRes {
    @ApiModelProperty(name="유저 name", example="김싸피")
    String name;
    @ApiModelProperty(name="유저 email", example="ssafy@ssafy.com")
    String email;
    @ApiModelProperty(name="유저 address", example="부산")
    String address;

    public static UserUpdatePatchRes of(UserUpdatePatchReq userUpdatePatchReq){
        UserUpdatePatchRes res = new UserUpdatePatchRes();
        res.setName(userUpdatePatchReq.getName());
        res.setEmail(userUpdatePatchReq.getEmail());
        res.setAddress(userUpdatePatchReq.getAddress());
        return res;
    }
}
