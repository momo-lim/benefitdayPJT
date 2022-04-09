package com.ssafy.recommend.dto.boards.faq;

import com.ssafy.recommend.domain.boards.FAQ;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQReadDto {
    private Long id;
    private String answer;
    private String question;
    public FAQReadDto(FAQ faq){
        this.id= faq.getFaqId();
        this.answer=faq.getAnswer();
        this.question=faq.getQuestion();
    }
}
