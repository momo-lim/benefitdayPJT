package com.ssafy.recommend.mapper;

import java.util.List;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.model.Log;
import com.ssafy.recommend.model.LogRequest;
import com.ssafy.recommend.model.User;

public interface ServiceMapper {

	List<Service> list(User user);

	List<Service> search(String keyword);

	List<Service> limit(User user);

	List<Service> views();

	boolean log(LogRequest lr);

	List<Log> myloglist(String email);

	List<Log> loglist(Long id);

	List<Log> loglistall();

}
