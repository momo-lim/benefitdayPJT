package com.ssafy.recommend.dto.UserTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserTagReadDto {
    private Long id;
    private Long userId;
    private Long tagId;
    private String tagValue;

}
