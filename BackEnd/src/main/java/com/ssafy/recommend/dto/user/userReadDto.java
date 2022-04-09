package com.ssafy.recommend.dto.user;

import com.ssafy.recommend.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class userReadDto {
    private Long userId;
    private String email;
    private String location;
    private String gender;
    private LocalDate birthday;
    private String incomeRange;
    private String personalChar;
    private String familyChar;
    private String signUpDate;
    public userReadDto(user user){
        this.userId=user.getUserId();
        this.email=user.getEmail();
        this.location=user.getLocation();
        this.gender=user.getGender();
        this.birthday=user.getBirthday();
        this.incomeRange=user.getIncomeRange();
        this.personalChar=user.getPersonalChar();
        this.familyChar=user.getFamilyChar();
        this.signUpDate=user.getSignUpDate().toString();
    }
}
