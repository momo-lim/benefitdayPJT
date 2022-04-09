package com.ssafy.recommend.controller;

import com.ssafy.recommend.domain.Tag;
import com.ssafy.recommend.dto.UserTag.UserTagCreateDto;
import com.ssafy.recommend.dto.tag.TagReadDto;
import com.ssafy.recommend.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tag")
public class UserTagController {
    public static final Logger logger= LoggerFactory.getLogger(ServiceController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final JwtService jwtService;
    private final UserTagService userTagService;

    @PostMapping("/tags")
    public ResponseEntity<Map<String,Object>> setTagList(@RequestHeader String accessToken, @RequestBody List<UserTagCreateDto> createDtos){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try{
                String email = jwtService.getEmail(accessToken);
                List<String> insertTags = userTagService.setTags(email,createDtos);
                resultMap.put("insertTags",insertTags);
                resultMap.put("message",SUCCESS);
                status = HttpStatus.ACCEPTED;
            }catch (Exception e){
                logger.error("조회 실패: {}",e);
                resultMap.put("message", e.getMessage());
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
    @GetMapping("/tags")
    public ResponseEntity<Map<String,Object>> getTagList(@RequestHeader String accessToken){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try{
                String email = jwtService.getEmail(accessToken);
                List<TagReadDto> tagList = userTagService.getTags(email);
                resultMap.put("tagList",tagList);
                resultMap.put("message",SUCCESS);
                status = HttpStatus.ACCEPTED;
            }catch (Exception e){
                logger.error("조회 실패: {}",e);
                resultMap.put("message", e.getMessage());
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
    @DeleteMapping("/tags")
    public ResponseEntity<Map<String,Object>> deleteTagList(@RequestHeader String accessToken){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if(jwtService.isUsable(accessToken)){
            logger.info("사용 가능한 토큰!!!");
            try{
                String email = jwtService.getEmail(accessToken);
                userTagService.deleteTags(email);
                resultMap.put("message",SUCCESS);
                status = HttpStatus.ACCEPTED;
            }catch (Exception e){
                logger.error("조회 실패: {}",e);
                resultMap.put("message", e.getMessage());
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
