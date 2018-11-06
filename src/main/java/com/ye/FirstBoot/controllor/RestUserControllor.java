package com.ye.FirstBoot.controllor;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ye.FirstBoot.common.ResposeResult;
import com.ye.FirstBoot.dataAccess.jpa.domain.User;
import com.ye.FirstBoot.dataAccess.jpa.repository.UserRepository;
import com.ye.FirstBoot.dataAccess.mybatis.dao.UserDao;
import com.ye.FirstBoot.service.UserService;

import redis.clients.jedis.JedisCluster;

@RestController
public class RestUserControllor {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;

	
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Autowired
	JedisCluster jedisCluster;
	
	@Autowired
	UserDao userDao;
	
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
	
    @RequestMapping(path="getInfoFromJedisCluster",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult  getInfoFromRedis(String name) {
		return ResposeResult.ok(jedisCluster.get(name))  ;
	}
	

	@RequestMapping(path="getInfoFromRedis",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult  getInfoFromRedisCluster(String name) {
		return ResposeResult.ok(redisTemplate.opsForValue().get("name"))  ;
	}
	
	@RequestMapping(path="getUserByMybatis",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult  getUserByMybatis(Long id) {
		PageHelper.startPage(1, 5);
		List<com.ye.FirstBoot.dataAccess.mybatis.model.User> userList=userDao.selectAllUsers();
		PageInfo result = new PageInfo(userList);
		return ResposeResult.ok(result)  ;
	}
	
	@RequestMapping(path="getUserByJPA",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult getUserByJPA() throws Exception {
		
		Sort sort = new Sort(Sort.Direction.DESC, "id"); // 设置根据id倒序排列
		Pageable pageable = PageRequest.of(0, 3, sort); // 根据start、size、sort创建分页对象
		Page<User> page = userRepository.findAll(pageable); // 根据这个分页对象获取分页对象

		System.out.println(page.getNumber()); // 当前页start
		System.out.println(page.getNumberOfElements()); // 当前页start
		System.out.println(page.getSize()); // 每页数量size
		System.out.println(page.getTotalElements()); // 总数量
		System.out.println(page.getTotalPages()); // 总页数

		
		return  ResposeResult.ok(page)  ;
	}
	
	
	@RequestMapping(path="denyAll",method= {RequestMethod.POST, RequestMethod.GET})
	public ResposeResult denyAll() throws Exception {	
		return  ResposeResult.ok("Sorry you had been denied!")  ;
	}

}
