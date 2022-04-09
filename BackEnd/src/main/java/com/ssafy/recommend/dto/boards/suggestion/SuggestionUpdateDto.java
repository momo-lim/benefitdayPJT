package com.ssafy.recommend.dto.boards.suggestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionUpdateDto {
    private String title;
    private String contents;
}
