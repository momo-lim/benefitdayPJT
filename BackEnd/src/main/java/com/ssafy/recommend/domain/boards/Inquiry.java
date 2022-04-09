package com.ssafy.recommend.domain.boards;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.user;
import com.ssafy.recommend.dto.boards.inquiry.InquiryUpdateDto;
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
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long inquiryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "title",length = 200)
    private String title;
    @Column(name = "contents",length = 2000)
    private String contents;

    @Builder
    public Inquiry(user user,Service service,String title,String contents){
        this.user=user;
        this.service=service;
        this.title=title;
        this.contents=contents;
    }
    public void update(InquiryUpdateDto updateDto,Service service){
        this.service = service;
        this.title = updateDto.getTitle();
        this.contents = updateDto.getContents();
    }
}
