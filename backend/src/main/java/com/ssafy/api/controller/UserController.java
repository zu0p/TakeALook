package com.ssafy.api.controller;

import com.ssafy.api.request.UserUpdatePatchReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepositorySupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의. (swagger)
 */
@Slf4j
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService; // 유저 관련 비즈니스 처리 로직
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") // swagger 설명 추가
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {
		User user = userService.createUser(registerInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })

	// @ApiIgnore : swagger 에 표시안함
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User user = userService.getUserByUserId(userDetails.getUsername());
		return ResponseEntity.status(200).body(UserRes.of(user));
	}

	@GetMapping("/{userId}")
	@ApiOperation(value = "회원 존재 유무 조회", notes = "이미 존재하는 회원인지 확인한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> getUserExistMessage(@PathVariable String userId) {
		User user = userService.getUserByUserId(userId);
		if(user!=null) return ResponseEntity.status(200).body(BaseResponseBody.of(409, "Exist"));
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "None"));
	}

	@PatchMapping("/{userId}")
	@ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> updateUserInfo(@ApiIgnore Authentication authentication, @PathVariable String userId,
            @RequestBody @ApiParam(value="회원가입 정보", required = true) UserUpdatePatchReq userUpdatePatchReq) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User authUser = userService.getUserByUserId(userDetails.getUsername());
		if( authUser.getUserId().equals(userId) ){
			User user = userService.updateUser(authUser, userUpdatePatchReq);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else{
			return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Fail"));
		}
	}

	@DeleteMapping("/{userId}")
	@ApiOperation(value = "회원 정보 삭제", notes = "회원 정보를 삭제한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> deleteUserInfo(@ApiIgnore Authentication authentication, @PathVariable String userId) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String authUser = userService.getUserByUserId(userDetails.getUsername()).getUserId();
		if (userId.equals(authUser)){
			userService.deleteUser(userId);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else{
			return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Fail"));
		}
	}

}
