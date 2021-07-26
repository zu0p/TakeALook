package com.ssafy.api.service;

import com.ssafy.api.request.user.UserRegisterPostReq;
import com.ssafy.api.request.user.UserUpdatePatchReq;
import com.ssafy.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */

// 비즈니스 로직은 따로 분리해서 관리. AOP?
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);
	User updateUser(User user, UserUpdatePatchReq userUpdatePatchReq);
	void deleteUser(String userId);
}