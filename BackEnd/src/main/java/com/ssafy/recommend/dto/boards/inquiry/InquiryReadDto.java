package com.ssafy.recommend.dto.boards.inquiry;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.boards.Inquiry;
import com.ssafy.recommend.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InquiryReadDto {
    private Long id;
    private String email;
    private Long serviceId;
    private String title;
    private String contents;
    public InquiryReadDto(Inquiry inquiry){
        this.id=inquiry.getInquiryId();
        this.email=inquiry.getUser().getEmail();
        this.serviceId=inquiry.getService().getServiceId();
        this.title=inquiry.getTitle();
        this.contents= inquiry.getContents();
    }
}
