package com.ssafy.recommend.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.recommend.model.SetInfoRequest;
import com.ssafy.recommend.model.User;

@Mapper
public interface UserMapper {

	public User login(User user) throws SQLException;

	public boolean signup(User user);

	public boolean setInfo(SetInfoRequest sir);

	public User getInfo(String email);
	
}