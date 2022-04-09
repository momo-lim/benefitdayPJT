package com.ssafy.recommend.dto.boards.blindSpot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ssafy.recommend.domain.user;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlindSpotCreateDto {
    private String title;
    private String contents;
}
