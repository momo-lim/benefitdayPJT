package com.ssafy.recommend.service.rating;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.rating.Comment;
import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.rating.comment.CommentCreateDto;
import com.ssafy.recommend.dto.rating.comment.CommentReadDto;
import com.ssafy.recommend.dto.rating.comment.CommentUpdateDto;
import com.ssafy.recommend.repository.ServiceRepository;
import com.ssafy.recommend.repository.rating.CommentRepository;
import com.ssafy.recommend.repository.userRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class CommentServiceImpl implements CommentService{
    private final userRepository uRepository;
    private final ServiceRepository serviceRepository;
    private final CommentRepository commentRepository;
    @Override
    public Long createComment(String email, CommentCreateDto createDto) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저를 찾을수 없습니다."));
        Service service=serviceRepository.findById(createDto.getServiceId()).orElseThrow(()->new IllegalArgumentException("해당 서비스를 찾을수 없습니다."));
        return commentRepository.save(Comment.builder().user(user).service(service).contents(createDto.getContents()).build()).getCommentId();
    }
    @Override
    public CommentReadDto readComment(Long commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("해당하는 댓글이 없습니다."));
        CommentReadDto readDto=CommentReadDto.builder().commentId(comment.getCommentId()).userId(comment.getUser().getUserId())
                .userEmail(comment.getUser().getEmail()).serviceId(comment.getService().getServiceId()).contents(comment.getContents()).build();
        return readDto;
    }

    @Override
    public List<CommentReadDto> readAllCommentByUser(String email) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저를 찾을수 없습니다."));
        List<Comment> commentList=commentRepository.findAllByuser(user);
        List<CommentReadDto> readDtoList=new ArrayList<>();
        for(Comment comment:commentList){
            CommentReadDto readDto=CommentReadDto.builder().commentId(comment.getCommentId())
                    .userId(user.getUserId()).userEmail(user.getEmail()).serviceId(comment.getService().getServiceId()).contents(comment.getContents()).build();
            readDtoList.add(readDto);
        }
        return readDtoList;
    }

    @Override
    public List<CommentReadDto> readAllCommentByService(Long serviceId) {
        Service service=serviceRepository.findById(serviceId).orElseThrow(()->new IllegalArgumentException("해당 서비스를 찾을수 없습니다."));
        List<Comment> commentList=commentRepository.findAllByService(service);
        List<CommentReadDto> readDtoList=new ArrayList<>();
        for(Comment comment:commentList){
            CommentReadDto readDto=CommentReadDto.builder().commentId(comment.getCommentId()).userId(comment.getUser().getUserId())
                    .userEmail(comment.getUser().getEmail()).serviceId(comment.getService().getServiceId()).contents(comment.getContents()).build();
            readDtoList.add(readDto);
        }
        return readDtoList;
    }

    @Override
    public Long updateComment(Long commentId,CommentUpdateDto updateDto,String email) throws Exception {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("해당하는 댓글이 없습니다."));
        if(!comment.getUser().getEmail().equals(email)) throw new Exception("작성자만 수정 가능합니다.");
        comment.update(updateDto.getContents());
        return commentRepository.save(comment).getCommentId();
    }

    @Override
    public void deleteComment(Long commentId,String email) throws Exception{
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("해당하는 댓글이 없습니다."));
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저를 찾을수 없습니다."));
        if(!user.getEmail().equals(email)) throw new Exception("작성자만 삭제 가능합니다.");
        commentRepository.deleteById(commentId);
    }
}
