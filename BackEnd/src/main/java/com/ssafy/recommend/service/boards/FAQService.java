package com.ssafy.recommend.service.boards;


import java.util.List;

import com.ssafy.recommend.domain.boards.FAQ;
import com.ssafy.recommend.dto.boards.faq.FAQCreateDto;
import com.ssafy.recommend.dto.boards.faq.FAQReadDto;
import com.ssafy.recommend.dto.boards.faq.FAQUpdateDto;

public interface FAQService {
    Long writePost(FAQCreateDto createDto);
    Long deletePost(Long id);
    FAQReadDto readPost(Long id);
    List<FAQReadDto> readAll();
    Long updatePost(Long id, FAQUpdateDto updateDto);
}
