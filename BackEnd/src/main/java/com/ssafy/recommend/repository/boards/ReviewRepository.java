package com.ssafy.recommend.repository.boards;

import com.ssafy.recommend.domain.boards.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
