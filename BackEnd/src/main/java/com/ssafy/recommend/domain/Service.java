package com.ssafy.recommend.domain;

import com.ssafy.recommend.dto.service.ServiceUpdateDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@DynamicUpdate
@Entity(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "서비스ID",length = 12)
    @NotNull
    private String 서비스Id;
    @Column(name = "부서명",length = 20)
    private String 부서명;
    @Column(name = "서비스명",length = 100)
    private String 서비스명;
    @Column(name = "서비스목적",length = 1000)
    private String 서비스목적;
    @Column(name = "선정기준",length = 4000)
    private String 선정기준;
    @Column(name = "소관기관명",length = 30)
    private String 소관기관명;
    @Column(name = "신청기한",length = 300)
    private String 신청기한;
    @Column(name = "신청방법",length = 2000)
    private String 신청방법;
    @Column(name = "구비서류",length = 500)
    private String 구비서류;
    @Column(name = "지원내용",length = 3000)
    private String 지원내용;
    @Column(name = "지원대상",length = 2000)
    private String 지원대상;
    @Column(name = "접수기관명",length = 50)
    private String 접수기관명;
    @Column(name = "지원유형",length = 100)
    private String 지원유형;
    @Column(name = "도_특별시_광역시",length = 20)
    private String 도특별시광역시;
    @Column(name = "시_군_구",length = 20)
    private String 시군구;
    @Column(name = "조회수")
    @ColumnDefault("0")
    private Long 조회수;

    @OneToMany(mappedBy = "service")
    private List<ServiceTag> serviceTags=new ArrayList<>();

    public void update(ServiceUpdateDto updateDto){
        this.서비스Id=updateDto.get서비스Id();
        this.부서명=updateDto.get부서명();
        this.서비스명=updateDto.get서비스명();
        this.서비스목적=updateDto.get서비스목적();
        this.선정기준=updateDto.get선정기준();
        this.소관기관명=updateDto.get소관기관명();
        this.신청기한=updateDto.get신청기한();
        this.신청방법=updateDto.get신청방법();
        this.구비서류=updateDto.get구비서류();
        this.지원내용=updateDto.get지원내용();
        this.지원대상=updateDto.get지원대상();
        this.접수기관명=updateDto.get접수기관명();
        this.지원유형=updateDto.get지원유형();
        this.도특별시광역시=updateDto.get도특별시광역시();
        this.시군구=updateDto.get시군구();
    }
    public void addView(){
        this.조회수=this.조회수+1;
    }
}
