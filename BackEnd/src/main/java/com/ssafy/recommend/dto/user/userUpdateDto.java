package com.ssafy.recommend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class userUpdateDto {
    private String location;
    private String birthday;
    private String incomeRange;
    private String personalChar;
    private String familyChar;
}
