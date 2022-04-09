package com.ssafy.recommend.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.recommend.dto.tag.TagReadDto;
import com.ssafy.recommend.dto.user.KaKaoUserDto;
import com.ssafy.recommend.dto.user.userReadDto;
import com.ssafy.recommend.dto.user.userUpdateDto;
import com.ssafy.recommend.service.UserTagService;
import com.ssafy.recommend.util.RunPython;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.recommend.service.JwtService;
import com.ssafy.recommend.service.OAuthService;
import com.ssafy.recommend.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api("사용자 컨트롤러  API V1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private final JwtService jwtService;
	private final OAuthService oauthService;
	private final UserService userService;
	private final UserTagService userTagService;
	
	@ApiOperation(value = "로그인 & 회원가입", notes = "카카오 인가코드를 받아 jwt 와 firstLogin 을 반환합니다. ", response = Map.class)
	@GetMapping("/login/{code}")
	public ResponseEntity<Map<String, Object>> login(@PathVariable("code") @ApiParam(value = "카카오 인가코드", required = true) String code) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String token = null;
		KaKaoUserDto kakaoUser = null;
		try {
			// 인가코드로 카카오액세스토큰 받아오기
			token = oauthService.getKakaoAccessToken(code);
			// 카카오액세스토큰으로 유저정보 받아오기
			kakaoUser = oauthService.createKakaoUser(token);
			
			if(kakaoUser != null) {
				userReadDto readDto = userService.login(kakaoUser.getEmail());
				// 이메일로 jwt토큰 생성
				token = jwtService.create("email", readDto.getEmail(), "accessToken");// key, data, subject

				resultMap.put("isFirstLogin", false);
				resultMap.put("accessToken", token);
				resultMap.put("email",kakaoUser.getEmail());
				resultMap.put("nickname", kakaoUser.getNickName());
				resultMap.put("profileImageUrl", kakaoUser.getProfileImageUrl());
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
			else {
				logger.error("로그인 실패 : {}", "카카오톡 인증에 실패하였습니다.");
				resultMap.put("result", FAIL);
				resultMap.put("error", "카카오톡 인증에 실패하였습니다.");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}catch (IllegalArgumentException e){
			logger.info("로그인 실패. 회원가입 필요 : {}",e);
			userService.signup(kakaoUser);
			userReadDto readDto = userService.login(kakaoUser.getEmail());
			// 이메일로 jwt토큰 생성
			token = jwtService.create("email", readDto.getEmail(), "accessToken");
			resultMap.put("isFirstLogin", true);
			resultMap.put("accessToken", token);
			resultMap.put("email",kakaoUser.getEmail());
			resultMap.put("nickname", kakaoUser.getNickName());
			resultMap.put("profileImageUrl", kakaoUser.getProfileImageUrl());
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<>(resultMap, status);
	}

	@ApiOperation(value = "맞춤필터설정", notes = "맞춤필터설정 성공 여부를 반환", response = Map.class)
	@PostMapping("/setInfo")
	public ResponseEntity<Map<String, Object>> setInfo(@RequestHeader String accessToken,
			@RequestBody @ApiParam(value = "맞춤필터설정 정보 : location, incomeRange, personalChar, familyChar") userUpdateDto updateDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			String email=jwtService.getEmail(accessToken);
			try {
				userService.setInfo(email,updateDto);
				userTagService.deleteTags(email);
				userReadDto readDto= userService.getInfo(email);
				String gender=readDto.getGender();
				LocalDate birthday=readDto.getBirthday();
				int age=LocalDate.now().getYear()-birthday.getYear();
				String income = readDto.getIncomeRange();
				String personal = readDto.getPersonalChar();
				String family = readDto.getFamilyChar();
				if("male".equals(gender)) userTagService.setTag(email,"남성");
				else userTagService.setTag(email,"여성");
				String ageRange="";
				if(age>=0&&age<=5)ageRange="영유아(0~5)";
				else if(age>=6&&age<=12) ageRange="아동(6~12)";
				else if(age>=13&&age<=18) ageRange="청소년(13~18)";
				else if(age>=19&&age<=29) ageRange="청년(19~29)";
				else if(age>=30&&age<=49) ageRange="중년(30~49)";
				else if(age>=50&&age<=64) ageRange="장년(50~64)";
				else ageRange="노년기(65~)";
				userTagService.setTag(email,ageRange);
				if(income.charAt(income.length()-1)=='%') income = income.substring(0, income.length()-1);
				userTagService.setTag(email, income);
				if(!"해당사항없음".equals(personal)) userTagService.setTag(email,personal);
				if(!"해당사항없음".equals(family)) userTagService.setTag(email,family);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("맞춤필터설정 실패 : {}", e);
				resultMap.put("result", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("result", FAIL);
			resultMap.put("error", "토큰 기간이 만료되었습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	@PostMapping("/recommendList")
	public ResponseEntity<Map<String, Object>> makeRecommendList(@RequestHeader String accessToken) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			String email=jwtService.getEmail(accessToken);
			try {
				userReadDto readDto= userService.getInfo(email);
				List<TagReadDto> tagList=userTagService.getTags(email);
				StringBuilder tags=new StringBuilder("");
				for(TagReadDto tag:tagList) tags.append(tag.getTagId()+" ");
				RunPython.makeUserJSON(readDto.getUserId(),tags.toString(),readDto.getLocation());
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("추천 리스트 생성 실패 : {}", e);
				resultMap.put("result", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("result", FAIL);
			resultMap.put("error", "토큰 기간이 만료되었습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<>(resultMap, status);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteUser(@RequestHeader String accessToken) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			String email=jwtService.getEmail(accessToken);
			try {
				userReadDto readDto= userService.getInfo(email);
				userService.deleteUser(email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("추천 리스트 생성 실패 : {}", e);
				resultMap.put("result", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("result", FAIL);
			resultMap.put("error", "토큰 기간이 만료되었습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<>(resultMap, status);
	}


	
}
