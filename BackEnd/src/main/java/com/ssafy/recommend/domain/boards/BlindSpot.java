package com.ssafy.recommend.domain.boards;

import com.ssafy.recommend.dto.boards.blindSpot.BlindSpotUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import com.ssafy.recommend.domain.user;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "blind_spot")
public class BlindSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blind_spot_id")
    private Long blindSpotId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    @Column(name = "title",length = 200)
    private String title;

    @Column(name = "contents",length = 2000)
    private String contents;

    @Builder
    public BlindSpot(user user,String title,String contents){
        this.user=user;
        this.title=title;
        this.contents=contents;
    }
    public void update(BlindSpotUpdateDto updateDto){
        this.title= updateDto.getTitle();
        this.contents=updateDto.getContents();
    }
}
