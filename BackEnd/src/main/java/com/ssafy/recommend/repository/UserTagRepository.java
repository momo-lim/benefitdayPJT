package com.ssafy.recommend.repository;

import com.ssafy.recommend.domain.Tag;
import com.ssafy.recommend.domain.UserTag;
import com.ssafy.recommend.domain.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTagRepository extends JpaRepository<UserTag,Long> {
    List<UserTag> findAllByuser(user user);
    void deleteAllByuser(user user);
}
