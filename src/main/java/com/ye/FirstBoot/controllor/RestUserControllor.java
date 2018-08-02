package com.ye.FirstBoot.controllor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ye.FirstBoot.common.ResposeResult;
import com.ye.FirstBoot.domain.User;
import com.ye.FirstBoot.repository.UserRepository;
import com.ye.FirstBoot.service.UserService;

@RestController
public class RestUserControllor {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	
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

	

}
