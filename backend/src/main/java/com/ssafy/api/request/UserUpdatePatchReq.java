package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 정보 수정 API ([PATCH] /api/v1/users/userID) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserUpdatePatchReq")
public class UserUpdatePatchReq {
	@ApiModelProperty(name="유저 password", example="1234")
	String password;

	@ApiModelProperty(name="유저 name", example="your_name")
	String name;

	@ApiModelProperty(name="유저 email", example="your_email")
	String email;

	@ApiModelProperty(name="유저 address", example="your_address")
	String address;
}
