package com.ssafy.recommend.service.boards;

import com.ssafy.recommend.dto.boards.suggestion.SuggestionCreateDto;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionReadDto;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionUpdateDto;

import java.util.List;

public interface SuggestionService {
    Long writePost(SuggestionCreateDto createDto,String email);
    Long deletePost(Long id,String email) throws Exception;
    SuggestionReadDto readPost(Long id);
    List<SuggestionReadDto> readAllPost();
    Long updatePost(Long id, SuggestionUpdateDto updateDto,String email) throws Exception;
}
