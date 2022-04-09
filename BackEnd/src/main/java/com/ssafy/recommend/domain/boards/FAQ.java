package com.ssafy.recommend.domain.boards;

import com.ssafy.recommend.dto.boards.faq.FAQUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long faqId;

    @Column(name = "question",length = 500)
    private String question;

    @Column(name = "answer",length = 500)
    private String answer;

    public void update(FAQUpdateDto updateDto){
        this.question=updateDto.getQuestion();
        this.answer=updateDto.getAnswer();
    }
}
