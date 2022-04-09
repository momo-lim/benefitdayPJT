package com.ssafy.recommend.repository;

import com.ssafy.recommend.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {
}
