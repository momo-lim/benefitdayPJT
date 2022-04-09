package com.ssafy.recommend.dto.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ServiceUpdateDto {
    private String 서비스Id;
    private String 부서명;
    private String 서비스명;
    private String 서비스목적;
    private String 선정기준;
    private String 소관기관명;
    private String 신청기한;
    private String 신청방법;
    private String 구비서류;
    private String 지원내용;
    private String 지원대상;
    private String 접수기관명;
    private String 지원유형;
    private String 도특별시광역시;
    private String 시군구;
    private Long 조회수;
}
