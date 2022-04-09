package com.ssafy.recommend.dto.rating.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeReadDto {
    private Long likeId;
    private Long userId;
    private Long serviceId;
    private String serviceName;
}
