package com.ssafy.recommend.service.rating;

import com.ssafy.recommend.dto.rating.comment.CommentCreateDto;
import com.ssafy.recommend.dto.rating.comment.CommentReadDto;
import com.ssafy.recommend.dto.rating.comment.CommentUpdateDto;

import java.util.List;

public interface CommentService {
    Long createComment(String email, CommentCreateDto createDto);
    void deleteComment(Long commentId,String email) throws Exception;
    CommentReadDto readComment(Long commentId);
    List<CommentReadDto> readAllCommentByUser(String email);
    List<CommentReadDto> readAllCommentByService(Long serviceId);
    Long updateComment(Long commentId,CommentUpdateDto updateDto,String email) throws Exception;
}
