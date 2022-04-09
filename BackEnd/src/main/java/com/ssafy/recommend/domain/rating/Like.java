package com.ssafy.recommend.domain.rating;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.domain.user;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private com.ssafy.recommend.domain.user user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @NotNull
    private Service service;
}
