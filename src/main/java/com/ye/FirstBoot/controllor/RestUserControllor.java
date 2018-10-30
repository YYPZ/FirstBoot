package com.ye.FirstBoot.controllor;

import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ye.FirstBoot.common.ResposeResult;
import com.ye.FirstBoot.domain.User;
import com.ye.FirstBoot.repository.UserRepository;
import com.ye.FirstBoot.service.UserService;

@RestController
public class RestUserControllor {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
/*	@Autowired
	RedisUtil redisUtil;*/
	
	@Qualifier("lettruceRedisTemplate")
	@Autowired
	RedisTemplate<String, String> lettruceRedisTemplate;

/*	@Autowired
	JedisCluster jedisCluster;*/
	
	@RequestMapping(path="saveUserInfoRest",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult saveUserInfo(String name) {
		User user =new User();
		user.setAccount("002");
		user.setIphone("0000008");
		return userService.registerUser(user);
		
	}
	
	@RequestMapping(path="getUserInfoRest",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult getUserInfo(long id) {
		String msg ="OK";
		User user = null;
		Optional<User> optionalUser=userRepository.findById(id);
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		}else {
			msg="none";
		}
		
		return ResposeResult.build(200,msg,user )  ;
	}
	
	@RequestMapping(path="pushMsg",method= {RequestMethod.POST, RequestMethod.GET})
	public HashMap<String,String> pushMsg(String name) {
		String msg ="OK";
		logger.info(name);
		HashMap<String,String>retMap = new HashMap<String,String>();
	     retMap.put("respCode","0000");
         retMap.put("respMsg","SUCCESS");
         logger.info("发送消息成功");
         return retMap;
		//return ResposeResult.ok(name)  ;
	}
	
	
	@RequestMapping(path="updateEmail",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult  updateEmail(long id) {
		userService.editUserEmail(id, "677@qq.com");
		return ResposeResult.ok("修改成功")  ;
	}
	
/*	@RequestMapping(path="getInfoFromRedis",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult  getInfoFromRedis(String name) {
		return ResposeResult.ok(redisUtil.get(name))  ;
	}*/
	
/*	@RequestMapping(path="getInfoFromRedisCluster",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult  getInfoFromRedisCluster(String name) {
		return ResposeResult.ok(jedisCluster.get(name))  ;
	}
*/
	@RequestMapping(path="getInfoFromLetturRedisCluster",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult  getInfoFromRedisCluster(String name) {
		return ResposeResult.ok(lettruceRedisTemplate.opsForValue().get("name"))  ;
	}

}
