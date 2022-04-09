package com.ssafy.recommend.service.boards;

import com.ssafy.recommend.domain.boards.Suggestion;
import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionCreateDto;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionReadDto;
import com.ssafy.recommend.dto.boards.suggestion.SuggestionUpdateDto;
import com.ssafy.recommend.repository.boards.SuggestionRepository;
import com.ssafy.recommend.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService{
    private final SuggestionRepository suggestionRepository;
    private final userRepository uRepository;
    @Override
    public Long writePost(SuggestionCreateDto createDto,String email) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당하는 유저가 없습니다."));
        Suggestion suggestion=Suggestion.builder()
                .user(user).title(createDto.getTitle())
                .contents(createDto.getContents()).build();
        return suggestionRepository.save(suggestion).getSuggestionId();
    }
    @Override
    public Long deletePost(Long id, String email) throws Exception{
        Suggestion suggestion=suggestionRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 제안 게시물이 없습니다."));
        if(!email.equals(suggestion.getUser().getEmail())) throw new Exception("작성자만 삭제 가능합니다.");
        suggestionRepository.deleteById(id);
        return id;
    }

    @Override
    public SuggestionReadDto readPost(Long id) {
        Suggestion suggestion=suggestionRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 제안 게시물이 없습니다."));
        return new SuggestionReadDto(suggestion);
    }
    @Override
    public List<SuggestionReadDto> readAllPost(){
        List<Suggestion> suggestionList=suggestionRepository.findAll();
        List<SuggestionReadDto> readDtoList=new ArrayList<>();
        for(Suggestion suggestion:suggestionList){
            readDtoList.add(new SuggestionReadDto(suggestion));
        }
        return readDtoList;
    }
    @Override
    public Long updatePost(Long id, SuggestionUpdateDto updateDto, String email) throws Exception {
        Suggestion suggestion=suggestionRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 제안 게시물이 없습니다."));
        if(!email.equals(suggestion.getUser().getEmail())) throw new Exception("작성자만 수정 가능합니다.");
        suggestion.update(updateDto);
        return suggestionRepository.save(suggestion).getSuggestionId();
    }
}
