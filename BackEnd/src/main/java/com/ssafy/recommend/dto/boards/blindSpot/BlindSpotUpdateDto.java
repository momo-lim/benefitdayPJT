package com.ssafy.recommend.dto.boards.blindSpot;

import com.ssafy.recommend.domain.boards.BlindSpot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BlindSpotUpdateDto {
    private String title;
    private String contents;
}
