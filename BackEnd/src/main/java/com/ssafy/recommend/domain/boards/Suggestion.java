package com.ssafy.recommend.domain.boards;

import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_id")
    private Long suggestionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @Builder
    public Suggestion(user user,String title,String contents){
        this.user=user;
        this.title=title;
        this.contents=contents;
    }
    public void update(SuggestionUpdateDto updateDto){
        this.title=updateDto.getTitle();
        this.contents=updateDto.getContents();
    }
}
