package com.ssafy.recommend.dto.UserTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserTagCreateDto {
    String tagValue;
}
