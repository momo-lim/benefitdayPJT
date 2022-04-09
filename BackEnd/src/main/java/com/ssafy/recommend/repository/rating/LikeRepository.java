package com.ssafy.recommend.repository.rating;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.rating.Like;
import com.ssafy.recommend.domain.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    List<Like> findAllByuser(user user);
    List<Like> findAllByService(Service service);
    Optional<Like> findFirstByServiceAndUser(Service service,user user);
}
