package com.ssafy.recommend.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.ssafy.recommend.domain.boards.BlindSpot;
import com.ssafy.recommend.domain.boards.Inquiry;
import com.ssafy.recommend.domain.boards.Review;
import com.ssafy.recommend.domain.boards.Suggestion;
import com.ssafy.recommend.domain.rating.Comment;
import com.ssafy.recommend.domain.rating.Like;
import com.ssafy.recommend.dto.user.userUpdateDto;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="email", length = 30)
    @NotNull
    private String email;

    @Column(name = "location",length = 100)
    private String location;

    @Column(name="gender",length = 10)
    private String gender;

    @Column(name="birthday")
    private LocalDate birthday;

    @Column(name="income_range",length = 20)
    private String incomeRange;

    @Column(name = "personal_char",length = 50)
    private String personalChar;

    @Column(name="family_char",length = 50)
    private String familyChar;

    @Column(name = "sign_up_date")
    private LocalDateTime signUpDate;

    @OneToMany(mappedBy = "user")
    private List<UserTag> userTags=new ArrayList<>();

    public void update(userUpdateDto updateDto){
        this.location=updateDto.getLocation();
        this.incomeRange=updateDto.getIncomeRange();
        this.personalChar=updateDto.getPersonalChar();
        this.familyChar=updateDto.getFamilyChar();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        this.birthday=LocalDate.parse(updateDto.getBirthday(),formatter);
    }
}
