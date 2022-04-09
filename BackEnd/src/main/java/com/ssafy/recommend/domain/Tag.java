package com.ssafy.recommend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tag")
public class Tag {
    @Id
    @Column(name = "tag_id",length = 6)
    private String tagId;

    @Column(name = "value",length = 100)
    private String value;

    @OneToMany(mappedBy = "tag")
    private List<UserTag> UserTags=new ArrayList<>();

    @OneToMany(mappedBy = "tag")
    private List<ServiceTag> ServiceTags=new ArrayList<>();

}
