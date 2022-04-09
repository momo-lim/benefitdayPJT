package com.ssafy.recommend.dto.boards.suggestion;

import com.ssafy.recommend.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuggestionCreateDto {
    private String title;
    private String contents;
}
