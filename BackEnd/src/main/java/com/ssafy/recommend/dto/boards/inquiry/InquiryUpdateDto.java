package com.ssafy.recommend.dto.boards.inquiry;

import com.ssafy.recommend.domain.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InquiryUpdateDto {
    private Long serviceId;
    private String title;
    private String contents;
}
