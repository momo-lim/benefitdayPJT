package com.ssafy.recommend.service.boards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recommend.domain.boards.FAQ;
import com.ssafy.recommend.dto.boards.faq.FAQCreateDto;
import com.ssafy.recommend.dto.boards.faq.FAQReadDto;
import com.ssafy.recommend.dto.boards.faq.FAQUpdateDto;
import com.ssafy.recommend.repository.boards.FAQRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FAQServiceImpl implements FAQService{
    private final FAQRepository faqRepository;
	
    @Override
    public Long writePost(FAQCreateDto createDto) {
        return faqRepository.save(createDto.toEntity()).getFaqId();
    }

    @Override
    public Long deletePost(Long id) {
        FAQ faq=faqRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 FAQ가 없습니다."));
        faqRepository.deleteById(id);
        return id;
    }

    @Override
    public FAQReadDto readPost(Long id) {
        FAQ faq=faqRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 FAQ가 없습니다."));
        return FAQReadDto.builder().answer(faq.getAnswer())
                .question(faq.getQuestion()).build();
    }

    @Override
    public Long updatePost(Long id, FAQUpdateDto updateDto) {
        FAQ faq=faqRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 FAQ가 없습니다."));
        faq.update(updateDto);
        return faqRepository.save(faq).getFaqId();
    }

	@Override
	public List<FAQReadDto>  readAll() {
		List<FAQ> faqList=faqRepository.findAll();
        List<FAQReadDto> readDtoList=new ArrayList<>();
        for(FAQ faq:faqList){
            readDtoList.add(new FAQReadDto(faq));
        }
		return readDtoList;
	}
}
