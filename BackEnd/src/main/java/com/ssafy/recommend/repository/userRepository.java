package com.ssafy.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.recommend.domain.user;

import java.util.Optional;

public interface userRepository extends JpaRepository<user,Long> {
    Optional<user> findByEmail(String email);
}
