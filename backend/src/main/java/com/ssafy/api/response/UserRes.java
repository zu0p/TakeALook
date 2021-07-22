package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
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
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="유저 id")
	String userId;

	@ApiModelProperty(name="유저 name", example="싸피")
	String name;

	@ApiModelProperty(name="유저 email", example="ssafy@ssafy.com")
	String email;

	@ApiModelProperty(name="유저 address", example="서울")
	String address;

	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setUserId(user.getUserId());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setAddress(user.getAddress());
		return res;
	}
}
