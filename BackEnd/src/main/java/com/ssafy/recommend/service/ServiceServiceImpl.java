package com.ssafy.recommend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.dto.service.ServiceCreateDto;
import com.ssafy.recommend.dto.service.ServiceReadDto;
import com.ssafy.recommend.dto.service.ServiceUpdateDto;
import com.ssafy.recommend.mapper.ServiceMapper;
import com.ssafy.recommend.model.Log;
import com.ssafy.recommend.model.LogRequest;
import com.ssafy.recommend.model.User;
import com.ssafy.recommend.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{
    private final ServiceRepository serviceRepository;

    @Autowired
	private SqlSession sqlSession;

    @Override
    public Long createService(ServiceCreateDto createDto) {
        System.out.println(createDto.toEntity()==null);
        return serviceRepository.save(createDto.toEntity()).getServiceId();
    }

    @Override
    public Long deleteService(Long id) {
        Service service=serviceRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당하는 지원서비스가 없습니다."));
        serviceRepository.deleteById(id);
        return id;
    }

    @Override
    public ServiceReadDto readService(Long id) {
        Service service=serviceRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당하는 지원서비스가 없습니다."));
        return new ServiceReadDto(service);
    }

    @Override
    public List<ServiceReadDto> readAllService(){
        List<Service> list=serviceRepository.findAll();
        List<ServiceReadDto> readList=new ArrayList<>();
        for(Service service : list) readList.add(new ServiceReadDto(service));
        return readList;
    }

    @Override
    public Long updateService(Long id, ServiceUpdateDto updateDto) {
        Service service=serviceRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당하는 지원서비스가 없습니다."));
        service.update(updateDto);
        return service.getServiceId();
    }

	@Override
	public List<Service> search(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ServiceMapper.class).search(keyword);
	}
	@Override
	public List<ServiceReadDto> views() {
        List<Service> services=serviceRepository.findAll(Sort.by(Sort.Direction.DESC,"조회수"));
		List<ServiceReadDto> readDtos=new ArrayList<>();
        for(Service service:services) {
            readDtos.add(new ServiceReadDto(service));
            if (readDtos.size()>=20) break;
        }
        return readDtos;
	}
    @Override
    public Long addView(Long id){
        Service service=serviceRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당하는 지원서비스가 없습니다."));
        service.addView();
        serviceRepository.save(service);
        return service.get조회수();
    }

	@Override
	public boolean log(Long serviceId, String email) {
		LogRequest lr = new LogRequest();
		lr.setEmail(email);
		lr.setServiceId(serviceId);
		return sqlSession.getMapper(ServiceMapper.class).log(lr);
	}

	@Override
	public List<Log> myloglist(String email) {
		return sqlSession.getMapper(ServiceMapper.class).myloglist(email);
	}

	@Override
	public List<Log> loglist(Long id) {
		return sqlSession.getMapper(ServiceMapper.class).loglist(id);
	}

	@Override
	public List<Log> loglistall() {
		return sqlSession.getMapper(ServiceMapper.class).loglistall();
	}

}
