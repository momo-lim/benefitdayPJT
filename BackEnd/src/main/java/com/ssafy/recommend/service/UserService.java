package com.ssafy.recommend.service;

import com.ssafy.recommend.dto.user.KaKaoUserDto;
import com.ssafy.recommend.dto.user.userReadDto;
import com.ssafy.recommend.dto.user.userUpdateDto;

public interface UserService {

	userReadDto login(String email) throws IllegalArgumentException;

	Long signup(KaKaoUserDto kaKaoUserDto);

	Long setInfo(String email, userUpdateDto updateDto);

	userReadDto getInfo(String email);

	void deleteUser(String email);
}