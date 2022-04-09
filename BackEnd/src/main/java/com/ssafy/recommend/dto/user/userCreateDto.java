package com.ssafy.recommend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ssafy.recommend.domain.user;

@Getter
@AllArgsConstructor
@Builder
public class userCreateDto {
    private String email;
    private String location;
    private String gender;
    private LocalDate birthday;
    private String incomeRange;
    private String personalChar;
    private String familyChar;
    private LocalDateTime signUpDate;
}
