package com.ssafy.recommend.dto.boards.review;

import com.ssafy.recommend.domain.boards.BlindSpot;
import com.ssafy.recommend.domain.boards.Review;
import com.ssafy.recommend.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewReadDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    public ReviewReadDto(Review review){
        this.id=review.getReviewId();
        this.email=review.getUser().getEmail();
        this.title=review.getTitle();
        this.contents= review.getContents();
    }
}
