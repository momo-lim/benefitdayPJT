package com.ssafy.recommend.dto.boards.faq;

import com.ssafy.recommend.domain.boards.FAQ;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FAQCreateDto {
    private String answer;
    private String question;
    public FAQ toEntity(){
        return FAQ.builder().answer(answer).question(question).build();
    }
}
