package com.ye.FirstBoot.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ye.FirstBoot.common.ResposeResult;
import com.ye.FirstBoot.domain.User;
import com.ye.FirstBoot.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
    public ResposeResult registerUser(User user) {
    	// 检查用户名是否注册，一般在前端验证的时候处理，因为注册不存在高并发的情况，这里再加一层查询是不影响性能的
    	/*if (null != userRepository.findByAccount(user.getAccount())) {
    		return ItdragonResult.build(400, "");
    	}*/
    	userRepository.save(user);
    	// 注册成功后选择发送邮件激活。现在一般都是短信验证码
    	return ResposeResult.build(200, "OK");
    }
    
    public ResposeResult editUserEmail(String email) {
    	// 通过Session 获取用户信息, 这里假装从Session中获取了用户的id，后面讲解SOA面向服务架构中的单点登录系统时，修改此处代码 FIXME
    	long id = 3L;
    	// 添加一些验证，比如短信验证
    	userRepository.updateUserEmail(id, email);
    	return ResposeResult.ok();
    }
    
}
