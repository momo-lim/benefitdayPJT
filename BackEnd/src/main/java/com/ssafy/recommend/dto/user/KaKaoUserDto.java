package com.ssafy.recommend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class KaKaoUserDto {
    private String email;
    private String gender;
    private LocalDate birthday;
    private String nickName;
    private String profileImageUrl;
}
