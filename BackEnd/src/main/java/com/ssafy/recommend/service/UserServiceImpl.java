package com.ssafy.recommend.service;

import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.user.KaKaoUserDto;
import com.ssafy.recommend.dto.user.userReadDto;
import com.ssafy.recommend.dto.user.userUpdateDto;
import com.ssafy.recommend.repository.UserTagRepository;
import com.ssafy.recommend.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final userRepository userRepository;

	@Override
	public userReadDto login(String email) {
		user user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
		return new userReadDto(user);
	}

	@Override
	public Long signup(KaKaoUserDto userDto) {
		user user= com.ssafy.recommend.domain.user.builder().email(userDto.getEmail()).gender(userDto.getGender())
				.birthday(userDto.getBirthday()).signUpDate(LocalDateTime.now()).build();
		System.out.println(user.getSignUpDate());
		return userRepository.save(user).getUserId();
	}

	@Override
	public Long setInfo(String email, userUpdateDto updateDto) {
		user user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
		user.update(updateDto);
		return userRepository.save(user).getUserId();
	}

	@Override
	public userReadDto getInfo(String email) {
		user user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
		return new userReadDto(user);
	}

	@Override
	public void deleteUser(String email){
		user user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
		userRepository.deleteById(user.getUserId());
	}
}
