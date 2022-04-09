package com.ssafy.recommend.service.boards;


import java.util.ArrayList;
import java.util.List;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.domain.boards.Inquiry;
import com.ssafy.recommend.dto.boards.inquiry.InquiryCreateDto;
import com.ssafy.recommend.dto.boards.inquiry.InquiryReadDto;
import com.ssafy.recommend.dto.boards.inquiry.InquiryUpdateDto;
import com.ssafy.recommend.repository.ServiceRepository;
import com.ssafy.recommend.repository.userRepository;
import com.ssafy.recommend.repository.boards.InquiryRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class InquiryServiceImpl implements InquiryService{
    private final InquiryRepository inquiryRepository;
    private final userRepository uRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public Long writePost(String email,InquiryCreateDto createDto) {
        user user=uRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("해당하는 유저가 없습니다."));
        Service service=serviceRepository.findById(createDto.getServiceId()).orElseThrow(()-> new IllegalArgumentException("해당하는 서비스가 없습니다."));
        Inquiry inquiry=Inquiry.builder().user(user)
                .service(service).title(createDto.getTitle())
                .contents(createDto.getContents()).build();
        return inquiryRepository.save(inquiry).getInquiryId();
    }

    @Override
    public Long deletePost(Long id, String email) throws Exception{
        Inquiry inquiry=inquiryRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당하는 항목이 없습니다."));
        if(!email.equals(inquiry.getUser().getEmail())) throw new Exception("작성자만 삭제할 수 있습니다.");
        inquiryRepository.deleteById(id);
        return id;
    }

    @Override
    public InquiryReadDto readPost(Long id) {
        Inquiry inquiry=inquiryRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당하는 항목이 없습니다."));
        return new InquiryReadDto(inquiry);
    }
    @Override
    public List<InquiryReadDto> readAll(){
        List<Inquiry> inquiryList=inquiryRepository.findAll();
        List<InquiryReadDto> readDtoList=new ArrayList<>();
        for(Inquiry inquiry:inquiryList){
            readDtoList.add(new InquiryReadDto(inquiry));
        }
        return readDtoList;
    }
    @Override
    public Long updatePost(Long id, InquiryUpdateDto updateDto,String email) throws Exception {
        Inquiry inquiry=inquiryRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당하는 항목이 없습니다."));
        Service service=serviceRepository.findById(updateDto.getServiceId()).orElseThrow(()->new IllegalArgumentException("해당하는 서비스가 없습니다."));
        if(!email.equals(inquiry.getUser().getEmail())) throw new Exception("작성자만 수정할 수 있습니다.");
		inquiry.update(updateDto,service);
		return inquiryRepository.save(inquiry).getInquiryId();
	}
        
}
