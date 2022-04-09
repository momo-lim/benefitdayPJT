package com.ssafy.recommend.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @NotNull
    private String 서비스ID;
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
    private String 지원유형;

}
