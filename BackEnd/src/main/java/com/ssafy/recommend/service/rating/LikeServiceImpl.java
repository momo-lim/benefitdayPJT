package com.ssafy.recommend.service.rating;

import com.ssafy.recommend.domain.rating.Like;
import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.dto.rating.like.LikeCreateDto;
import com.ssafy.recommend.dto.rating.like.LikeReadDto;
import com.ssafy.recommend.repository.ServiceRepository;
import com.ssafy.recommend.repository.rating.LikeRepository;
import com.ssafy.recommend.repository.userRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class LikeServiceImpl implements LikeService{
    private final userRepository uRepository;
    private final ServiceRepository serviceRepository;
    private final LikeRepository likeRepository;
    @Override
    public Long createLike(String email,LikeCreateDto createDto) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        Service service=serviceRepository.findById(createDto.getServiceId()).orElseThrow(()->new IllegalArgumentException("해당하는 서비스가 없습니다."));
        Like like=Like.builder().user(user).service(service).build();
        return likeRepository.save(like).getLikeId();
    }

    @Override
    public void deleteLike(Long serviceId,String email) throws Exception{
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        Service service=serviceRepository.findById(serviceId).orElseThrow(()->new IllegalArgumentException("해당하는 서비스가 없습니다."));
        Like like=likeRepository.findFirstByServiceAndUser(service,user).orElseThrow(()->new IllegalArgumentException("해당하는 좋아요가 없습니다."));
        likeRepository.delete(like);
    }

    @Override
    public LikeReadDto readLike(Long likeId) {
        Like like=likeRepository.findById(likeId).orElseThrow(()->new IllegalArgumentException("해당하는 좋아요가 없습니다."));
        user user=uRepository.findById(like.getUser().getUserId()).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        Service service=serviceRepository.findById(like.getService().getServiceId()).orElseThrow(()->new IllegalArgumentException("해당하는 서비스가 없습니다."));
        return LikeReadDto.builder().likeId(like.getLikeId()).userId(user.getUserId()).serviceId(service.getServiceId()).serviceName(service.get서비스명()).build();
    }
    @Override
    public Long countLikeByService(Long serviceId){
        Service service=serviceRepository.findById(serviceId).orElseThrow(()->new IllegalArgumentException("해당하는 서비스가 없습니다."));
        Long count=new Integer((likeRepository.findAllByService(service)).size()).longValue();
        return count;
    }
    @Override
    public List<LikeReadDto> readAllLikeByUser(String email) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        List<Like> likeList=likeRepository.findAllByuser(user);
        List<LikeReadDto> readDtoList=new ArrayList<>();
        for(Like like:likeList){
            Service service=serviceRepository.findById(like.getService().getServiceId()).orElseThrow(()->new IllegalArgumentException("해당하는 서비스가 없습니다."));
            LikeReadDto readDto=LikeReadDto.builder().likeId(like.getLikeId()).userId(user.getUserId()).serviceId(service.getServiceId()).serviceName(service.get서비스명()).build();
            readDtoList.add(readDto);
        }
        return readDtoList;
    }
}
