package com.ssafy.recommend.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class TagReadDto {
    private String tagId;
    private String value;
}
