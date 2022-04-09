// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.ssafy.recommend.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class User {
    @JsonIgnore
    private int userId;
    private String nickname;
    private String profileImageUrl;
    private String email;
    private String location;
    private String gender;
    private LocalDate birthday;
    private String incomeRange;
    private String personalChar;
    private String familyChar;
    private String type;
}