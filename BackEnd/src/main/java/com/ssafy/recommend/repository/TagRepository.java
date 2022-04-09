package com.ssafy.recommend.repository;

import com.ssafy.recommend.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,String> {
    Optional<Tag> findByValue(String value);
}
