package com.ssafy.recommend.dto.boards.review;

import com.ssafy.recommend.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateDto {
    private String title;
    private String contents;
}
