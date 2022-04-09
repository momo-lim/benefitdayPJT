package com.ssafy.recommend.service.rating;

import com.ssafy.recommend.dto.rating.like.LikeCreateDto;
import com.ssafy.recommend.dto.rating.like.LikeReadDto;

import java.util.List;

public interface LikeService {
    Long createLike(String email,LikeCreateDto createDto);
    void deleteLike(Long likeId,String email) throws Exception;
    LikeReadDto readLike(Long likeId);
    Long countLikeByService(Long serviceId);
    List<LikeReadDto> readAllLikeByUser(String email);
}
