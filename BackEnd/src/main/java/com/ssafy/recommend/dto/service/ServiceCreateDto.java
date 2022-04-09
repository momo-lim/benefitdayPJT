package com.ssafy.recommend.dto.service;

import com.ssafy.recommend.domain.Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor
@Builder
@ToString
public class ServiceCreateDto {
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
    public ServiceCreateDto(Service service){
        this.서비스Id=service.get서비스Id();
        this.부서명=service.get부서명();
        this.서비스명=service.get서비스명();
        this.서비스목적=service.get서비스목적();
        this.선정기준=service.get선정기준();
        this.소관기관명=service.get소관기관명();
        this.신청기한=service.get신청기한();
        this.신청방법=service.get신청방법();
        this.구비서류=service.get구비서류();
        this.지원내용=service.get지원내용();
        this.지원대상=service.get지원대상();
        this.접수기관명=service.get접수기관명();
        this.지원유형=service.get지원유형();
        this.도특별시광역시=service.get도특별시광역시();
        this.시군구=service.get시군구();
    }
    public Service toEntity(){
        return Service.builder().서비스Id(서비스Id).부서명(부서명).서비스명(서비스명)
                .서비스목적(서비스목적).선정기준(선정기준).소관기관명(소관기관명)
                .신청기한(신청기한).신청방법(신청방법).구비서류(구비서류).지원내용(지원내용)
                .지원대상(지원대상).접수기관명(접수기관명).지원유형(지원유형).도특별시광역시(도특별시광역시)
                .시군구(시군구).build();
    }
}
