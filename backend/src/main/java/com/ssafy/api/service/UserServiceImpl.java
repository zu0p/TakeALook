package com.ssafy.api.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.api.request.UserUpdatePatchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.util.NoSuchElementException;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getId());
		user.setDepartment(userRegisterInfo.getDepartment());
		user.setName(userRegisterInfo.getName());
		user.setPosition(userRegisterInfo.getPosition());
		user.setUserId(userRegisterInfo.getUser_id());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		System.out.println(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		try{
			User user = userRepositorySupport.findUserByUserId(userId).get();
			return user;
		}catch (NoSuchElementException e){
			return null;
		}
	}

	@Override
	public User updateUser(String userId, UserUpdatePatchReq userUpdatePatchReq) {
		User user = userRepositorySupport.findUserByUserId(userId).get();
		user.setPosition(userUpdatePatchReq.getPosition());
		user.setDepartment(userUpdatePatchReq.getDepartment());
		user.setName(userUpdatePatchReq.getName());
		user.setUserId(user.getUserId());
		user.setPassword(user.getPassword());
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepositorySupport.findUserByUserId(userId).get();
		userRepository.delete(user);
	}


}
