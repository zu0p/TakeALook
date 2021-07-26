package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {

	@ApiModelProperty(name="유저 email", example="your_email")
	String email;

	@ApiModelProperty(name="유저 address", example="your_address")
	String address;

	@ApiModelProperty(name="유저 name", example="your_name")
	String name;

	@ApiModelProperty(name="유저 user_id", example="your_user_id")
	String userId;

	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
}
