package com.ssafy.recommend.service;

import com.ssafy.recommend.dto.UserTag.UserTagCreateDto;
import com.ssafy.recommend.dto.tag.TagReadDto;

import java.util.List;

public interface UserTagService {
    List<TagReadDto> getTags(String email);

    Long setTag(String email, String value);

    List<String> setTags(String email, List<UserTagCreateDto> userTagCreateDTOs);

    void deleteTags(String email);
}
