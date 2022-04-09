package com.ssafy.recommend.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SetInfoRequest {
	@JsonIgnore
	private String email;
	private String location;
    private String birthday;
    private String incomeRange;
    private String personalChar;
    private String familyChar;
    
}
