package com.ssafy.recommend.dto.boards.suggestion;

import com.ssafy.recommend.domain.boards.BlindSpot;
import com.ssafy.recommend.domain.boards.Suggestion;
import com.ssafy.recommend.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionReadDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    public SuggestionReadDto(Suggestion suggestion){
        this.id=suggestion.getSuggestionId();
        this.email=suggestion.getUser().getEmail();
        this.title=suggestion.getTitle();
        this.contents= suggestion.getContents();
    }
}
