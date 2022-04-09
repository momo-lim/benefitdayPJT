package com.ssafy.recommend.dto.boards.faq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQUpdateDto {
    private String answer;
    private String question;
}
