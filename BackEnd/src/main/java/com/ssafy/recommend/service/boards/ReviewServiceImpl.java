package com.ssafy.recommend.service.boards;

import com.ssafy.recommend.domain.boards.Review;

import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.boards.review.ReviewCreateDto;
import com.ssafy.recommend.dto.boards.review.ReviewReadDto;
import com.ssafy.recommend.dto.boards.review.ReviewUpdateDto;
import com.ssafy.recommend.repository.boards.ReviewRepository;
import com.ssafy.recommend.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final userRepository uRepository;

    @Override
    public Long writePost(ReviewCreateDto createDto,String email) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당하는 유저가 없습니다."));
        Review review=Review.builder().user(user)
                .title(createDto.getTitle()).contents(createDto.getContents())
                .build();
        return reviewRepository.save(review).getReviewId();
    }

    @Override
    public Long deletePost(Long id, String email) throws Exception{
        Review review=reviewRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 리뷰글이 없습니다."));
        if(!email.equals(review.getUser().getEmail())) throw new Exception("작성자만 삭제 가능합니다.");
        reviewRepository.deleteById(id);
        return id;
    }

    @Override
    public ReviewReadDto readPost(Long id) {
        Review review=reviewRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 리뷰글이 없습니다."));
        return new ReviewReadDto(review);
    }
    @Override
    public List<ReviewReadDto> readAllPost() {
        List<Review> reviewList=reviewRepository.findAll();
        List<ReviewReadDto> readDtoList=new ArrayList<>();
        for(Review review:reviewList){
            readDtoList.add(new ReviewReadDto(review));
        }
        return readDtoList;
    }

    @Override
    public Long updatePost(Long id, ReviewUpdateDto updateDto, String email) throws Exception{
        Review review=reviewRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 리뷰글이 없습니다."));
        if(!email.equals(review.getUser().getEmail())) throw new Exception("작성자만 수정 가능합니다.");
        review.update(updateDto);
        return reviewRepository.save(review).getReviewId();
    }
}
