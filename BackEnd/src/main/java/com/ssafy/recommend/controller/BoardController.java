package com.ssafy.recommend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotCreateDto;
import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotReadDto;
import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotUpdateDto;
import com.ssafy.recommend.dto.boards.faq.FAQCreateDto;
import com.ssafy.recommend.dto.boards.faq.FAQReadDto;
import com.ssafy.recommend.dto.boards.faq.FAQUpdateDto;
import com.ssafy.recommend.dto.boards.inquiry.InquiryCreateDto;
import com.ssafy.recommend.dto.boards.inquiry.InquiryReadDto;
import com.ssafy.recommend.dto.boards.inquiry.InquiryUpdateDto;
import com.ssafy.recommend.dto.boards.review.ReviewCreateDto;
import com.ssafy.recommend.dto.boards.review.ReviewReadDto;
import com.ssafy.recommend.dto.boards.review.ReviewUpdateDto;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionCreateDto;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionReadDto;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionUpdateDto;
import com.ssafy.recommend.service.JwtService;
import com.ssafy.recommend.service.boards.BlindSpotService;
import com.ssafy.recommend.service.boards.FAQService;
import com.ssafy.recommend.service.boards.InquiryService;
import com.ssafy.recommend.service.boards.ReviewService;
import com.ssafy.recommend.service.boards.SuggestionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
@Api("보드 컨트롤러")
public class BoardController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
    private static final String FAIL = "fail";


    private final JwtService jwtService;
	private final FAQService faqService;
	private final InquiryService inquiryService;
	private final ReviewService reviewService;
	private final SuggestionService suggestionService;
	private final BlindSpotService blindSpotService;

	@ApiOperation(value = "FAQ 쓰기", notes = "FAQ쓰기 성공 여부를 반환", response = Map.class)
	@PostMapping("/faq/write")
	public ResponseEntity<Map<String, Object>> writeFAQ(
			@RequestBody FAQCreateDto fcd) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if (faqService.writePost(fcd) != null) {
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} else
			throw new Exception("FAQ쓰기에 실패했습니다.");

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "FAQ 조회", notes = "FAQ 리스트를 반환", response = Map.class)
	@GetMapping("/faq/list")
	public ResponseEntity<Map<String, Object>> getAllFAQ() {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			List<FAQReadDto> faqlist = faqService.readAll();
			resultMap.put("faqList", faqlist);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("FAQ 리스트 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "FAQ 수정", notes = "수정 성공 여부를 반환", response = Map.class)
	@PutMapping("/faq/update/{id}")
	public ResponseEntity<Map<String, Object>> updateFAQ(@PathVariable("id") Long id,
			@RequestBody FAQUpdateDto fud) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		if(faqService.updatePost(id, fud) != null) {
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} else {
			resultMap.put("result", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "FAQ 삭제", notes = "삭제 성공 여부를 반환", response = Map.class)
	@DeleteMapping("/faq/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteFAQ(
			@PathVariable("id") Long id){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if(faqService.deletePost(id) != null) {
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
			else
				throw new Exception("FAQ삭제 실패");
		} catch (Exception e) {
			logger.error("FAQ 삭제 실패 : {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}


	@ApiOperation(value = "Inquiry 작성", notes = "Inquiry 작성 성공 여부를 반환", response = Map.class)
	@PostMapping("/inquiry")
	public ResponseEntity<Map<String,Object>> writeInquiry(@RequestHeader String accessToken, @RequestBody InquiryCreateDto inquiryCreateDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email= jwtService.getEmail(accessToken);
				inquiryService.writePost(email,inquiryCreateDto);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("inquiry 작성 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Inquiry 조회", notes = "id 값으로 Inquiry 조회", response = Map.class)
	@GetMapping("/inquiry/{id}")
	public ResponseEntity<Map<String,Object>> getInquiry(@PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			InquiryReadDto readDto = inquiryService.readPost(id);
			resultMap.put("inquiry", readDto);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("inquiry 리스트 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "Inquiry 전체 목록 조회", notes = "전체 리스트를 반환", response = Map.class)
	@GetMapping("/inquiry")
	public ResponseEntity<Map<String,Object>> getAllInquiry(){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			List<InquiryReadDto> readDtoList = inquiryService.readAll();
			resultMap.put("inquiryList", readDtoList);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("inquiry 리스트 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "Inquiry 수정", notes = "id와 수정 내용을 받아 user를 제외한 inquiry 수정", response = Map.class)
	@PutMapping("/inquiry/{inquiryId}")
	public ResponseEntity<Map<String,Object>> updateInquiry(@PathVariable Long inquiryId, @RequestHeader String accessToken, @RequestBody InquiryUpdateDto updateDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email= jwtService.getEmail(accessToken);
				inquiryService.updatePost(inquiryId, updateDto, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("inquiry 변경 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Inquiry 삭제", notes = "id를 받아 inquiry 삭제", response = Map.class)
	@DeleteMapping("/inquiry/{inquiryId}")
	public ResponseEntity<Map<String,Object>> deleteInquiry(@PathVariable Long inquiryId, @RequestHeader String accessToken){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email= jwtService.getEmail(accessToken);
				inquiryService.deletePost(inquiryId, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("inquiry 삭제 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "Review 등록", notes = "createDto를 받아 등록", response = Map.class)
	@PostMapping("/review")
	public ResponseEntity<Map<String,Object>> writeReview(@RequestHeader String accessToken,@RequestBody ReviewCreateDto createDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email= jwtService.getEmail(accessToken);
				reviewService.writePost(createDto,email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("Review 생성 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Review 조회", notes = "id를 받아 Review 조회", response = Map.class)
	@GetMapping("/review/{reviewId}")
	public ResponseEntity<Map<String,Object>> getReview(@PathVariable Long reviewId){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			ReviewReadDto readDto=reviewService.readPost(reviewId);
			resultMap.put("review",readDto);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("Review 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Review 리스트 조회", notes = "reviewList 반환", response = Map.class)
	@GetMapping("/review")
	public ResponseEntity<Map<String,Object>> getAllReview(){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			List<ReviewReadDto> list = reviewService.readAllPost();
			resultMap.put("reviewList",list);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("Review 리스트 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Review 수정", notes = "id와 updateDto를 받아 review 수정", response = Map.class)
	@PutMapping("/review/{reviewId}")
	public ResponseEntity<Map<String,Object>> updateReview(@PathVariable Long reviewId,@RequestHeader String accessToken, @RequestBody ReviewUpdateDto updateDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				reviewService.updatePost(reviewId, updateDto, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("Review 수정 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Review 삭제", notes = "id를 받아 review 삭제", response = Map.class)
	@DeleteMapping("/review/{reviewId}")
	public ResponseEntity<Map<String,Object>> updateReview(@PathVariable Long reviewId,@RequestHeader String accessToken){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				reviewService.deletePost(reviewId,email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("Review 삭제 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "Suggestion 등록", notes = "SuggestionCreateDto를 받아 등록", response = Map.class)
	@PostMapping("/suggestion")
	public ResponseEntity<Map<String,Object>> writeSuggestion(@RequestHeader String accessToken,@RequestBody SuggestionCreateDto createDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				suggestionService.writePost(createDto,email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("Suggestion 등록 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Suggestion 조회", notes = "id를 받아 Suggestion 조회", response = Map.class)
	@GetMapping("/suggestion/{suggestionId}")
	public ResponseEntity<Map<String,Object>> getSuggestion(@PathVariable Long suggestionId){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			SuggestionReadDto readDto = suggestionService.readPost(suggestionId);
			resultMap.put("suggestion",readDto);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("Suggestion 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Suggestion 리스트 조회", notes = "전체 Suggestion 조회", response = Map.class)
	@GetMapping("/suggestion")
	public ResponseEntity<Map<String,Object>> getAllSuggestion(){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			List<SuggestionReadDto> readDtoList = suggestionService.readAllPost();
			resultMap.put("suggestionList",readDtoList);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("Suggestion 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Suggestion 수정", notes = "id와 SuggestionUpdateDto를 받아 Suggestion 수정", response = Map.class)
	@PutMapping("/suggestion/{suggestionId}")
	public ResponseEntity<Map<String,Object>> updateSuggestion(@PathVariable Long suggestionId, @RequestHeader String accessToken, @RequestBody SuggestionUpdateDto updateDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				suggestionService.updatePost(suggestionId, updateDto, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("Suggestion 수정 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "Suggestion 삭제", notes = "id를 받아 Suggestion 삭제", response = Map.class)
	@DeleteMapping("/suggestion/{suggestionId}")
	public ResponseEntity<Map<String,Object>> deleteSuggestion(@PathVariable Long suggestionId, @RequestHeader String accessToken){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				suggestionService.deletePost(suggestionId, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("Suggestion 삭제 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "BlindSpot 등록", notes = "BlindSpotCreateDto를 받아 BlindSpot 등록", response = Map.class)
	@PostMapping("/blindspot")
	public ResponseEntity<Map<String,Object>> writeBlindSpot(@RequestHeader String accessToken, @RequestBody BlindSpotCreateDto createDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				blindSpotService.writePost(createDto, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("BlindSpot 등록 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "BlindSpot 조회", notes = "id를 받아 BlindSpot 조회", response = Map.class)
	@GetMapping("/blindspot/{blindspotId}")
	public ResponseEntity<Map<String,Object>> getBlindSpot(@PathVariable Long blindspotId){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			BlindSpotReadDto readDto = blindSpotService.readPost(blindspotId);
			resultMap.put("BlindSpot", readDto);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("BlindSpot 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "BlindSpot 리스트 조회", notes = "전체 BlindSpot 조회", response = Map.class)
	@GetMapping("/blindspot")
	public ResponseEntity<Map<String,Object>> getAllBlindSpot(){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			List<BlindSpotReadDto> readDtoList= blindSpotService.readAllPost();
			resultMap.put("BlindSpotList", readDtoList);
			resultMap.put("result", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("BlindSpot 조회 실패: {}", e);
			resultMap.put("error", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "BlindSpot 수정", notes = "id와 BlindSpotUpdateDto를 받아 BlindSpot 수정", response = Map.class)
	@PutMapping("/blindspot/{blindspotId}")
	public ResponseEntity<Map<String,Object>> updateBlindSpot(@PathVariable Long blindspotId, @RequestHeader String accessToken, @RequestBody BlindSpotUpdateDto updateDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				blindSpotService.updatePost(blindspotId, updateDto, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("BlindSpot 수정 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	@ApiOperation(value = "BlindSpot 삭제", notes = "id를 받아 BlindSpot 삭제", response = Map.class)
	@DeleteMapping("/blindspot/{blindspotId}")
	public ResponseEntity<Map<String,Object>> deleteBlindSpot(@PathVariable Long blindspotId, @RequestHeader String accessToken){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)) {
			try {
				String email = jwtService.getEmail(accessToken);
				blindSpotService.deletePost(blindspotId, email);
				resultMap.put("result", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("BlindSpot 삭제 실패: {}", e);
				resultMap.put("error", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
