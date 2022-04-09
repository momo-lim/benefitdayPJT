package com.ssafy.recommend.repository.rating;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.rating.Comment;
import com.ssafy.recommend.domain.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByuser(user user);
    List<Comment> findAllByService(Service service);
}
