package com.ssafy.recommend.dto.rating.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateDto {
    private Long serviceId;
    private String contents;
}
