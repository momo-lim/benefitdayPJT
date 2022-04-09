package com.ssafy.recommend.service.boards;


import java.util.List;

import com.ssafy.recommend.domain.boards.FAQ;
import com.ssafy.recommend.domain.boards.Inquiry;
import com.ssafy.recommend.dto.boards.inquiry.InquiryCreateDto;
import com.ssafy.recommend.dto.boards.inquiry.InquiryReadDto;
import com.ssafy.recommend.dto.boards.inquiry.InquiryUpdateDto;

import java.util.List;

public interface InquiryService {
    Long writePost(String email,InquiryCreateDto createDto);
    Long deletePost(Long id, String email) throws Exception;
    InquiryReadDto readPost(Long id);
    List<InquiryReadDto> readAll();
    Long updatePost(Long id, InquiryUpdateDto updateDto, String email) throws Exception;
}
