package com.ssafy.recommend.domain.boards;

import com.ssafy.recommend.dto.boards.review.ReviewUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import com.ssafy.recommend.domain.user;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    @Column(name = "title",length = 200)
    private String title;

    @Column(name = "contents",length = 2000)
    private String contents;

    @Builder
    public Review(user user, String title,String contents){
        this.user=user;
        this.title=title;
        this.contents=contents;
    }
    public void update(ReviewUpdateDto updateDto){
        this.title=updateDto.getTitle();
        this.contents=updateDto.getContents();
    }
}
