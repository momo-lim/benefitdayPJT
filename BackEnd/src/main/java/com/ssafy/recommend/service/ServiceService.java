package com.ssafy.recommend.service;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.dto.service.ServiceCreateDto;
import com.ssafy.recommend.dto.service.ServiceReadDto;
import com.ssafy.recommend.dto.service.ServiceUpdateDto;
import com.ssafy.recommend.model.Log;
import com.ssafy.recommend.model.User;

import java.util.List;

public interface ServiceService {
    Long createService(ServiceCreateDto createDto);
    Long deleteService(Long id);
    ServiceReadDto readService(Long id);
    List<ServiceReadDto> readAllService();
    Long updateService(Long id, ServiceUpdateDto updateDto);
    Long addView(Long id);
	List<Service> search(String keyword);
    List<ServiceReadDto> views();
	boolean log(Long serviceId, String email);
	List<Log> myloglist(String email);
	List<Log> loglist(Long id);
	List<Log> loglistall();
}
