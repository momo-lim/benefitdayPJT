package com.ssafy.recommend.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Log {
    private int logId;
    private String userId;
    private String serviceId;
    private LocalDateTime logDate;
}