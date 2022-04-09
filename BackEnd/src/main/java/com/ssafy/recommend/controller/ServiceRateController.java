package com.ssafy.recommend.controller;

import com.ssafy.recommend.dto.rating.comment.CommentCreateDto;
import com.ssafy.recommend.dto.rating.comment.CommentReadDto;
import com.ssafy.recommend.dto.rating.comment.CommentUpdateDto;
import com.ssafy.recommend.dto.rating.like.LikeCreateDto;
import com.ssafy.recommend.dto.rating.like.LikeReadDto;
import com.ssafy.recommend.service.JwtService;
import com.ssafy.recommend.service.rating.CommentService;
import com.ssafy.recommend.service.rating.LikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/rating")
@RequiredArgsConstructor
public class ServiceRateController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final JwtService jwtService;
    private final LikeService likeService;
    private final CommentService commentService;
    @PostMapping("/like")
    public ResponseEntity<Map<String, Object>> saveLike(@RequestHeader String accessToken, @RequestBody LikeCreateDto createDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try {
                String email = jwtService.getEmail(accessToken);
                likeService.createLike(email,createDto);
                resultMap.put("result", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("Like 생성 실패: {}", e);
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
    @GetMapping("/like/{likeId}")
    public ResponseEntity<Map<String, Object>> getLike(@PathVariable Long likeId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            LikeReadDto readDto=likeService.readLike(likeId);
            resultMap.put("like",readDto);
            resultMap.put("result", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("Like 조회 실패: {}", e);
            resultMap.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    @GetMapping("/like/service/{serviceId}")
    public ResponseEntity<Map<String, Object>> getNumOfLikesByServiceId(@PathVariable Long serviceId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            Long count=likeService.countLikeByService(serviceId);
            resultMap.put("count",count);
            resultMap.put("result", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("Like 조회 실패: {}", e);
            resultMap.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    @GetMapping("/like")
    public ResponseEntity<Map<String, Object>> getLikesByUserId(@RequestHeader String accessToken){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try {
                String email = jwtService.getEmail(accessToken);
                List<LikeReadDto> readDtoList=likeService.readAllLikeByUser(email);
                resultMap.put("LikeList",readDtoList);
                resultMap.put("result", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("Like 리스트 조회 실패: {}", e);
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
    @DeleteMapping("/like/{serviceId}")
    public ResponseEntity<Map<String, Object>> deleteLike(@PathVariable Long serviceId,@RequestHeader String accessToken){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
                try {
                    String email = jwtService.getEmail(accessToken);
                    likeService.deleteLike(serviceId,email);
                    resultMap.put("result", SUCCESS);
                    status = HttpStatus.ACCEPTED;
                }
                catch (Exception e) {
                    logger.error("Like 삭제 실패: {}", e);
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

    @ApiOperation(value = "Comment 등록", notes = "createDto를 받아 등록", response = Map.class)
    @PostMapping("/comment")
    public ResponseEntity<Map<String,Object>> writeComment(@RequestHeader String accessToken, @RequestBody CommentCreateDto createDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try {
                String email = jwtService.getEmail(accessToken);
                commentService.createComment(email,createDto);
                resultMap.put("result", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("Comment 생성 실패: {}", e);
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
    @ApiOperation(value = "Comment 조회", notes = "id를 받아 Comment 조회", response = Map.class)
    @GetMapping("/comment/commentId={commentId}")
    public ResponseEntity<Map<String,Object>> getComment(@PathVariable Long commentId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            CommentReadDto readDto=commentService.readComment(commentId);
            resultMap.put("comment",readDto);
            resultMap.put("result", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("Comment 조회 실패: {}", e);
            resultMap.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    @ApiOperation(value = "유저별 Comment 리스트 조회", notes = "유저의 commentList 반환", response = Map.class)
    @GetMapping("/comment")
    public ResponseEntity<Map<String,Object>> getAllCommentByUser(@RequestHeader String accessToken){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try {
                String email = jwtService.getEmail(accessToken);
                List<CommentReadDto> commentList = commentService.readAllCommentByUser(email);
                resultMap.put("CommentList",commentList);
                resultMap.put("result", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("Comment 조회 실패: {}", e);
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
    @ApiOperation(value = "서비스별 Comment 리스트 조회", notes = "유저의 commentList 반환", response = Map.class)
    @GetMapping("/comment/serviceId={serviceId}")
    public ResponseEntity<Map<String,Object>> getAllCommentByService(@PathVariable Long serviceId){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            List<CommentReadDto> commentList = commentService.readAllCommentByService(serviceId);
            resultMap.put("CommentList",commentList);
            resultMap.put("result", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("Comment 조회 실패: {}", e);
            resultMap.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    @ApiOperation(value = "Comment 수정", notes = "id와 updateDto를 받아 comment 수정", response = Map.class)
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Map<String,Object>> updateComment(@PathVariable Long commentId,@RequestHeader String accessToken ,@RequestBody CommentUpdateDto updateDto){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try {
                String email = jwtService.getEmail(accessToken);
                commentService.updateComment(commentId,updateDto,email);
                resultMap.put("result", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("Comment 수정 실패: {}", e);
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
    @ApiOperation(value = "Comment 삭제", notes = "id를 받아 comment 삭제", response = Map.class)
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Map<String,Object>> updateComment(@PathVariable Long commentId, @RequestHeader String accessToken){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)) {
            logger.info("사용 가능한 토큰!");
            try {
                String email = jwtService.getEmail(accessToken);
                commentService.deleteComment(commentId,email);
                resultMap.put("result", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("Comment 삭제 실패: {}", e);
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
