package com.ssafy.recommend.repository.boards;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.recommend.domain.boards.FAQ;

public interface FAQRepository extends JpaRepository<FAQ,Long> {
}
