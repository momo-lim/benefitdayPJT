package com.ssafy.recommend.dto.boards.inquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryCreateDto {
    private Long serviceId;
    private String title;
    private String contents;
}
