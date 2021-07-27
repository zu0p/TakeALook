package com.ssafy.api.response.user;

import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserGetResponse")
public class UserDetailGetRes {
	@ApiModelProperty(name="유저 id")
	String userId;
	@ApiModelProperty(name="유저 name")
	String name;
	@ApiModelProperty(name="유저 email")
	String email;
	@ApiModelProperty(name="유저 address")
	String address;

	public static UserDetailGetRes of(User user) {
		UserDetailGetRes res = new UserDetailGetRes();
		res.setUserId(user.getUserId());
		res.setEmail(user.getEmail());
		res.setAddress(user.getAddress());
		res.setName(user.getName());
		return res;
	}
}
