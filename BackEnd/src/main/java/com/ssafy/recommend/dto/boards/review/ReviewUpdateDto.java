package com.ssafy.recommend.dto.boards.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewUpdateDto {
    private String title;
    private String contents;
}
