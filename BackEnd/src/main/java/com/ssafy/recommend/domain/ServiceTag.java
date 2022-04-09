package com.ssafy.recommend.domain;

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
public class ServiceTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_tag_id")
    private Long serviceTagId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
