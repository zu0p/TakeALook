package com.ssafy.api.service.user;

import com.ssafy.api.request.user.UserUpdatePatchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.user.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.user.UserRepository;
import com.ssafy.db.repository.user.UserRepositorySupport;

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
		user.setUserId(userRegisterInfo.getUserId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setName(userRegisterInfo.getName());
		user.setEmail(userRegisterInfo.getEmail());
		user.setAddress(userRegisterInfo.getAddress());
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
	public User selectUserById(Long userId) {
		User user = userRepository.findUserById(userId);
		return user;
	}

	@Override
	public User updateUser(User user, UserUpdatePatchReq userUpdatePatchReq) {
		user.setPassword(passwordEncoder.encode(userUpdatePatchReq.getPassword()));
		user.setName(userUpdatePatchReq.getName());
		user.setEmail(userUpdatePatchReq.getEmail());
		user.setAddress(userUpdatePatchReq.getAddress());
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepositorySupport.findUserByUserId(userId).get();
		userRepository.delete(user);
	}


}
