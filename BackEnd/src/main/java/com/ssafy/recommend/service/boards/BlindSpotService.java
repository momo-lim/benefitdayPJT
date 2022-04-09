package com.ssafy.recommend.service.boards;

import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotCreateDto;
import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotReadDto;
import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotUpdateDto;

import java.util.List;

public interface BlindSpotService {
    Long writePost(BlindSpotCreateDto createDto,String email);
    Long deletePost(Long blindSpotId,String email) throws Exception;
    BlindSpotReadDto readPost(Long blindSpotId);
    List<BlindSpotReadDto> readAllPost();
    Long updatePost(Long id, BlindSpotUpdateDto updateDto,String email) throws Exception;
}
