package com.ssafy.api.response.user;

import com.ssafy.api.request.user.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRegistResponse")
public class UserRegistPostRes {
    @ApiModelProperty(name="유저 id", example="ssafy")
    String userId;
    @ApiModelProperty(name="유저 name", example="김싸피")
    String name;
    @ApiModelProperty(name="유저 email", example="ssafy@ssafy.com")
    String email;
    @ApiModelProperty(name="유저 address", example="서울")
    String address;

    public static UserRegistPostRes of(User user){
        UserRegistPostRes res = new UserRegistPostRes();
        res.setUserId(user.getUserId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setAddress(user.getAddress());
        return res;
    }
}
