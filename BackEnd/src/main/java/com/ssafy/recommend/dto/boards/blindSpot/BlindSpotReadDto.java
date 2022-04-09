package com.ssafy.recommend.dto.boards.blindSpot;

import com.ssafy.recommend.domain.boards.BlindSpot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.ssafy.recommend.domain.user;

@Getter
@Builder
@AllArgsConstructor
public class BlindSpotReadDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    public BlindSpotReadDto(BlindSpot blindSpot){
        this.id=blindSpot.getBlindSpotId();
        this.email=blindSpot.getUser().getEmail();
        this.title=blindSpot.getTitle();
        this.contents= blindSpot.getContents();
    }
}
