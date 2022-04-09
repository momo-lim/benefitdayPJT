package com.ssafy.recommend.domain.rating;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private user user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @NotNull
    private Service service;

    @Column(name = "contents", length = 2000)
    @NotNull
    private String contents;

    public void update(String contents){
        this.contents=contents;
    }
}
