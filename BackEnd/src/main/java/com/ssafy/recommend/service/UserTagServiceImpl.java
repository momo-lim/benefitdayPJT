package com.ssafy.recommend.service;

import com.ssafy.recommend.domain.Tag;
import com.ssafy.recommend.domain.UserTag;
import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.UserTag.UserTagCreateDto;
import com.ssafy.recommend.dto.tag.TagReadDto;
import com.ssafy.recommend.repository.TagRepository;
import com.ssafy.recommend.repository.UserTagRepository;
import com.ssafy.recommend.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTagServiceImpl implements UserTagService {
    private final userRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final TagRepository tagRepository;
    @Override
    public List<TagReadDto> getTags(String email) {
        user founduser = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당하는 유저가 없습니다."));
        List<UserTag> userTagList=userTagRepository.findAllByuser(founduser);
        List<TagReadDto> tagReadDtoList=new ArrayList<>();
        for(UserTag userTag:userTagList){
            Tag tag=tagRepository.findById(userTag.getTag().getTagId()).orElseThrow(()->new IllegalArgumentException("해당하는 태그가 없습니다."));
            TagReadDto tagReadDto=TagReadDto.builder().tagId(tag.getTagId()).value(tag.getValue()).build();
            tagReadDtoList.add(tagReadDto);
        }
        return tagReadDtoList;
    }
    @Override
    public Long setTag(String email, String value){
        user foundUser = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        Tag tag=tagRepository.findByValue(value).orElseThrow(()->new IllegalArgumentException("해당 태그가 없습니다."));;
        return userTagRepository.save(UserTag.builder().user(foundUser).tag(tag).build()).getUserTagId();
    }
    @Override
    public List<String> setTags(String email, List<UserTagCreateDto> userTagCreateDTOs){
        List<Tag> tags=new ArrayList<>();
        for(UserTagCreateDto createDto:userTagCreateDTOs){
            tags.add(tagRepository.findByValue(createDto.getTagValue()).orElseThrow(()->new IllegalArgumentException("해당하는 태그가 없습니다.")));
        }
        user foundUser = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        List<String> tagIdList=new ArrayList<>();
        for(Tag tag:tags){
            userTagRepository.save(UserTag.builder().user(foundUser).tag(tag).build());
            tagIdList.add(tag.getTagId());
        }
        return tagIdList;
    }
    @Transactional
    @Override
    public void deleteTags(String email){
        user foundUser = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        userTagRepository.deleteAllByuser(foundUser);
    }

}
