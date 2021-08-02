package com.ssafy.api.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 정보 수정 API ([PATCH] /api/v1/users/userID) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserUpdatePatchRequest")
public class UserUpdatePatchReq {
	@ApiModelProperty(name="유저 id", example="ssafy")
	String userId;
	@ApiModelProperty(name="유저 password", example="5678")
	String password;
	@ApiModelProperty(name="유저 name", example="김싸피")
	String name;
	@ApiModelProperty(name="유저 email", example="ssafy@ssafy.com")
	String email;
	@ApiModelProperty(name="유저 address", example="부산")
	String address;
}
