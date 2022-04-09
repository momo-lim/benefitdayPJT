package com.ssafy.recommend.repository.boards;

import com.ssafy.recommend.domain.boards.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry,Long> {
}
