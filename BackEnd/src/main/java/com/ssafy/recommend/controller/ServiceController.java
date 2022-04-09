package com.ssafy.recommend.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.recommend.domain.Service;
import com.ssafy.recommend.dto.service.ServiceCreateDto;
import com.ssafy.recommend.dto.service.ServiceReadDto;
import com.ssafy.recommend.dto.tag.TagReadDto;
import com.ssafy.recommend.model.Log;
import com.ssafy.recommend.service.JwtService;
import com.ssafy.recommend.service.ServiceService;
import com.ssafy.recommend.service.UserService;
import com.ssafy.recommend.service.UserTagService;
import com.ssafy.recommend.util.RunPython;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/service")
@Api("서비스 컨트롤러")
public class ServiceController {

    public static final Logger logger= LoggerFactory.getLogger(ServiceController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
	@Autowired
	private UserTagService userTagService;

	@ApiOperation(value = "추천 리스트 20개 조회", notes = "액세스 토큰을 이용하여, 이용자에게 추천된 리스트 중 유사도가 높은 20개 조회", response = Map.class)
	@GetMapping("/recommend")
	public ResponseEntity<Map<String, Object>> getRecommendList(@RequestHeader String accessToken, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)){
			logger.info("사용 가능한 토큰!!!");
			try{
				String email = jwtService.getEmail(accessToken);
				List<TagReadDto> tagList=userTagService.getTags(email);
				StringBuilder tags=new StringBuilder("");
				for(TagReadDto tag:tagList) tags.append(tag.getTagId()+" ");
				Long userId=userService.getInfo(email).getUserId();
				String userLocation=userService.getInfo(email).getLocation();
				if(userLocation==null) userLocation="";
				Path path= Paths.get("/home/ssafy/userJSON/"+userId+".json");
				if(!path.toFile().isFile()) RunPython.makeUserJSON(userId, tags.toString(), userLocation);
				BufferedReader br=new BufferedReader(new FileReader(path.toFile()));
				StringBuilder sb=new StringBuilder();
				String line="";
				while((line=br.readLine())!=null) sb.append(line);
				JSONParser parser=new JSONParser();
				JSONArray jsonArray=(JSONArray) parser.parse(sb.toString());
				List<ServiceReadDto> serviceList=new ArrayList<>();
				for(int i=0;i<jsonArray.size();i++) {
					if(serviceList.size()>=20) break;
					JSONObject jsonObject=(JSONObject) jsonArray.get(i);
					Long id =(Long)jsonObject.get("service_id");
					serviceList.add(serviceService.readService(id));
				}
				br.close();
				resultMap.put("serviceList",serviceList);
				resultMap.put("message",SUCCESS);
				status = HttpStatus.ACCEPTED;
			}catch (Exception e){
				logger.error("조회 실패: {}",e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<>(resultMap, status);
	}

	@ApiOperation(value = "지원유형별 추천 리스트 20개 조회", notes = "액세스 토큰을 이용하여, 이용자에게 추천된 리스트 중 지원 유형별로 유사도가 상위권 20개 각각각조회", response = Map.class)
	@GetMapping("/type/{type}")
	public ResponseEntity<Map<String, Object>> getRecommendListEachType(@PathVariable String type,@RequestHeader String accessToken, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		if(jwtService.isUsable(accessToken)){
			logger.info("사용 가능한 토큰!!!");
			try{
				String email = jwtService.getEmail(accessToken);
				List<TagReadDto> tagList=userTagService.getTags(email);
				StringBuilder tags=new StringBuilder("");
				for(TagReadDto tag:tagList) tags.append(tag.getTagId()+" ");
				Long userId=userService.getInfo(email).getUserId();
				String userLocation=userService.getInfo(email).getLocation();
				if(userLocation==null) userLocation="";
				Path path= Paths.get("/home/ssafy/userJSON/"+userId+".json");
				if(!path.toFile().isFile()) RunPython.makeUserJSON(userId,tags.toString(),userLocation);
				BufferedReader br=new BufferedReader(new FileReader(path.toFile()));
				StringBuilder sb=new StringBuilder();
				String line="";
				while((line=br.readLine())!=null) sb.append(line);
				JSONParser parser=new JSONParser();
				JSONArray jsonArray=(JSONArray) parser.parse(sb.toString());
				List<ServiceReadDto> serviceList=new ArrayList<>();
				for(int i=0;i<jsonArray.size();i++) {
					if(serviceList.size()>=20) break;
					JSONObject jsonObject=(JSONObject) jsonArray.get(i);
					Long id =(Long)jsonObject.get("service_id");
					ServiceReadDto serviceReadDto=serviceService.readService(id);
					String temp=serviceReadDto.get지원유형();
					ArrayList<String> list=new ArrayList<>();
					Arrays.stream(temp.split("\\|\\|")).forEach(str->{list.add(str);});
					if(type.equals("현금")) {
						if (list.contains("현금") || list.contains("현금(감면)") || list.contains("현금(보험)") || list.contains("현금(융자)") || list.contains("현금(장학금)"))
							serviceList.add(serviceReadDto);
					}
					else if (type.equals("현물")) {
						if (list.contains("현물") || list.contains("이용권"))
							serviceList.add(serviceReadDto);
					}
					else if (type.equals("서비스")) {
						if (list.contains("서비스(돌봄)") || list.contains("서비스(일자리)") || list.contains("기타(교육)") || list.contains("기타(상담)") || list.contains("봉사/기부") || list.contains("민원"))
							serviceList.add(serviceReadDto);
					}
					else if (type.equals("법률지원")) {
						if (list.contains("상담/법률지원"))
							serviceList.add(serviceReadDto);
					}
					else if (type.equals("의료지원")) {
						if (list.contains("서비스(의료)") || list.contains("의료지원"))
							serviceList.add(serviceReadDto);
					}
					else if (type.equals("문화여가생활")) {
						if (list.contains("문화/여가지원"))
							serviceList.add(serviceReadDto);
					}
				}
				br.close();
				resultMap.put("serviceList",serviceList);
				resultMap.put("message",SUCCESS);
				status = HttpStatus.ACCEPTED;
			}catch (Exception e){
				logger.error("조회 실패: {}",e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
		else{
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<>(resultMap, status);
	}

    @ApiOperation(value = "서비스 리스트 검색", notes = "서비스 리스트 검색", response = Map.class)
	@GetMapping("/search/{keyword}")
	public ResponseEntity<Map<String, Object>> search(
			@PathVariable("keyword") @ApiParam(value = "검색 키워드", required = true) String keyword) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<Service> serviceList = serviceService.search(keyword);
			resultMap.put("serviceList", serviceList);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("피드 리스트 조회 실패: {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
    
    @ApiOperation(value = "서비스 리스트 조회수 top20 조회", notes = "모든 서비스 중 조회수 top20 조회", response = Map.class)
    @GetMapping("/views")
   	public ResponseEntity<Map<String, Object>> views() {
   		Map<String, Object> resultMap = new HashMap<>();
   		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			List<ServiceReadDto> serviceList = serviceService.views();
			resultMap.put("serviceList", serviceList);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("피드 리스트 조회 실패: {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
   		
   		return new ResponseEntity<Map<String, Object>>(resultMap, status);
   	}
    
    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> createService(@RequestBody ServiceCreateDto createDto){
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        try{
            Long serviceId=serviceService.createService(createDto);
            resultMap.put("result",SUCCESS);
            resultMap.put("serviceId",serviceId);
            status=HttpStatus.ACCEPTED;
        }
        catch (Exception e){
            resultMap.put("result",FAIL);
            resultMap.put("error",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,status);
    }
    
    @ApiOperation(value = "서비스 상세 조회", notes = "서비스 상세 조회", response = Map.class)
    @GetMapping("/read/{id}")
    public ResponseEntity<Map<String,Object>> readService(@RequestHeader String accessToken,
    		@PathVariable("id")Long id){
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        try {
        	if(jwtService.isUsable(accessToken)) {
        		String email = jwtService.getEmail(accessToken);
	            ServiceReadDto readDto = serviceService.readService(id);
	            serviceService.addView(id);
	            serviceService.log(id,email);
	            resultMap.put("result",SUCCESS);
	            resultMap.put("service",readDto);
	            status=HttpStatus.ACCEPTED;
        	} else {
        		
        	}
        }
        catch (IllegalArgumentException e){
            resultMap.put("result",FAIL);
            resultMap.put("error",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,status);
    }
    
    @GetMapping("/read")
    public ResponseEntity<Map<String,Object>> readAllService(){
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        try {
            List<ServiceReadDto> list=serviceService.readAllService();
            resultMap.put("result",SUCCESS);
            resultMap.put("serviceList",list);
            status=HttpStatus.ACCEPTED;
        }
        catch (IllegalArgumentException e){
            resultMap.put("result",FAIL);
            resultMap.put("error",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,status);
    }
    
    @GetMapping("/addview/{id}")
    public ResponseEntity<Map<String,Object>> addViewOne(@PathVariable("id")Long id){
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        try {
        	Long views=serviceService.addView(id);
            resultMap.put("result",SUCCESS);
            resultMap.put("serviceList",views);
            status=HttpStatus.ACCEPTED;
        }
        catch (IllegalArgumentException e){
            resultMap.put("result",FAIL);
            resultMap.put("error",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,status);
    }
    
    @ApiOperation(value = "내 로그 리스트 조회", notes = "액세스토큰으로 해당하는 유저의 로그리스트 반환", response = Map.class)
    @GetMapping("/loglist")
    public ResponseEntity<Map<String,Object>> myloglist(@RequestHeader String accessToken){
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        try {
        	String email = jwtService.getEmail(accessToken);
        	List<Log> loglist = serviceService.myloglist(email);
            resultMap.put("result",SUCCESS);
            resultMap.put("logList",loglist);
            status=HttpStatus.ACCEPTED;
        }
        catch (IllegalArgumentException e){
            resultMap.put("result",FAIL);
            resultMap.put("error",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,status);
    }
    
    @ApiOperation(value = "유저 로그 리스트 조회", notes = "유저아이디로 해당하는 유저의 로그리스트 반환", response = Map.class)
    @GetMapping("/loglist/{id}")
    public ResponseEntity<Map<String,Object>> myloglist(@PathVariable("id")Long id){
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        try {
        	List<Log> loglist = serviceService.loglist(id);
            resultMap.put("result",SUCCESS);
            resultMap.put("logList",loglist);
            status=HttpStatus.ACCEPTED;
        }
        catch (IllegalArgumentException e){
            resultMap.put("result",FAIL);
            resultMap.put("error",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,status);
    }
    
    @ApiOperation(value = "모든 로그 리스트 조회", notes = "모든 로그리스트 시간 순으로 반환", response = Map.class)
    @GetMapping("/loglistall")
    public ResponseEntity<Map<String,Object>> loglistall(){
        Map<String,Object> resultMap=new HashMap<>();
        HttpStatus status=null;
        try {
        	List<Log> loglist = serviceService.loglistall();
            resultMap.put("result",SUCCESS);
            resultMap.put("logList",loglist);
            status=HttpStatus.ACCEPTED;
        }
        catch (IllegalArgumentException e){
            resultMap.put("result",FAIL);
            resultMap.put("error",e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        return new ResponseEntity<Map<String, Object>>(resultMap,status);
    }
    
}
