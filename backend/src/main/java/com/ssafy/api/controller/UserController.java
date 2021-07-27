package com.ssafy.api.controller;

import com.ssafy.api.request.user.UserUpdatePatchReq;
import com.ssafy.api.response.user.UserDeleteRes;
import com.ssafy.api.response.user.UserRegistPostRes;
import com.ssafy.api.response.user.UserUpdatePatchRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.user.UserRegisterPostReq;
import com.ssafy.api.response.user.UserDetailGetRes;
import com.ssafy.api.service.user.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value = "유저 API", tags = {"User."})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "아이디와 패스워드를 통해 회원가입 한다.")
	public ResponseEntity<?> registerUser(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		User user = userService.createUser(registerInfo);
		return ResponseEntity.status(200).body(UserRegistPostRes.of(user));
	}

	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
		public ResponseEntity<?> getUserInfo(@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		if(!userService.getUserExistMessage(userDetails.getUsername()))
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

		User user = userService.getUserByUserId(userDetails.getUsername());
		return ResponseEntity.status(200).body(UserDetailGetRes.of(user));
	}

	@GetMapping("/{userId}")
	@ApiOperation(value = "회원 존재 유무 조회", notes = "이미 존재하는 회원인지 확인한다.")
	public ResponseEntity<? extends BaseResponseBody> getUserExistMessage(@PathVariable String userId) {
		if(!userService.getUserExistMessage(userId))
			return ResponseEntity.status(200).body(BaseResponseBody.of(409, "Exist"));
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "None"));
	}

	@PatchMapping()
	@ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정한다.")
	public ResponseEntity<?> updateUserInfo(@ApiIgnore Authentication authentication, @RequestBody @ApiParam(value="회원 정보 수정", required = true) UserUpdatePatchReq userUpdatePatchReq) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		if(!userService.checkAuthByUserId(userUpdatePatchReq.getUserId(),userDetails.getUsername()))
			return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Fail"));

		User authUser = userService.getUserByUserId(userDetails.getUsername());
		User user = userService.updateUser(authUser, userUpdatePatchReq);
		return ResponseEntity.status(200).body(UserUpdatePatchRes.of(userUpdatePatchReq));
	}

	@DeleteMapping("/{userId}")
	@ApiOperation(value = "회원 정보 삭제", notes = "회원 정보를 삭제한다.")
	public ResponseEntity<?> deleteUserInfo(@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String authId = userDetails.getUsername();
		if (!userService.getUserExistMessage(authId))
			return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

		userService.deleteUser(authId);
		return ResponseEntity.status(200).body(UserDeleteRes.of(authId));
	}

}
