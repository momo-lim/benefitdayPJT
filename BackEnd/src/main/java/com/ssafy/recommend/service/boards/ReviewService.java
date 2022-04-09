package com.ssafy.recommend.service.boards;

import com.ssafy.recommend.dto.boards.review.ReviewCreateDto;
import com.ssafy.recommend.dto.boards.review.ReviewReadDto;
import com.ssafy.recommend.dto.boards.review.ReviewUpdateDto;

import java.util.List;

public interface ReviewService {
    Long writePost(ReviewCreateDto createDto, String email);
    Long deletePost(Long id, String email) throws Exception;
    ReviewReadDto readPost(Long id);
    List<ReviewReadDto> readAllPost();
    Long updatePost(Long id, ReviewUpdateDto updateDto, String email) throws Exception;
}
