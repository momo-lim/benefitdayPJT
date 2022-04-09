package com.ssafy.recommend.dto.rating.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReadDto {
    private Long commentId;
    private Long userId;
    private String userEmail;
    private Long serviceId;
    private String contents;
}
