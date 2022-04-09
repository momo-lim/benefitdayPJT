package com.ssafy.recommend.service.boards;

import com.ssafy.recommend.domain.boards.BlindSpot;
import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotCreateDto;
import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotReadDto;
import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotUpdateDto;
import com.ssafy.recommend.repository.boards.BlindSpotRepository;
import com.ssafy.recommend.repository.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlindSpotServiceImpl implements BlindSpotService{
    private final BlindSpotRepository blindSpotRepository;
    private final userRepository uRepository;

    @Override
    public Long writePost(BlindSpotCreateDto createDto,String email) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당하는 유저가 없습니다."));
        BlindSpot blindSpot=BlindSpot.builder()
                .user(user).title(createDto.getTitle())
                .contents(createDto.getContents()).build();
        return blindSpotRepository.save(blindSpot).getBlindSpotId();
    }

    @Override
    public Long deletePost(Long blindSpotId,String email) throws Exception{
        BlindSpot blindSpot=blindSpotRepository.findById(blindSpotId).orElseThrow(()->new IllegalArgumentException("해당하는 항목이 없습니다."));
        if(!email.equals(blindSpot.getUser().getEmail())) throw new Exception("작성자만 삭제 가능합니다.");
        blindSpotRepository.deleteById(blindSpotId);
        return blindSpotId;
    }

    @Override
    public BlindSpotReadDto readPost(Long blindSpotId) {
        BlindSpot blindSpot=blindSpotRepository.findById(blindSpotId).orElseThrow(()->
                new IllegalArgumentException("해당하는 항목이 없습니다."));
        return new BlindSpotReadDto(blindSpot);
    }
    @Override
    public List<BlindSpotReadDto> readAllPost(){
        List<BlindSpot> blindSpotList = blindSpotRepository.findAll();
        List<BlindSpotReadDto> ReadDtoList = new ArrayList<>();
        for(BlindSpot blindSpot:blindSpotList){
            ReadDtoList.add(new BlindSpotReadDto(blindSpot));
        }
        return ReadDtoList;
    }

    @Override
    public Long updatePost(Long id,BlindSpotUpdateDto updateDto, String email) throws Exception{
        BlindSpot blindSpot=blindSpotRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당하는 항목이 없습니다."));
        if(!email.equals(blindSpot.getUser().getEmail())) throw new Exception("작성자만 수정 가능합니다.");
        blindSpot.update(updateDto);
        return blindSpotRepository.save(blindSpot).getBlindSpotId();
    }
}
