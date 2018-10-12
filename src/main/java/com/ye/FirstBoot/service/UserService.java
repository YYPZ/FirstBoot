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
    
    public void editUserEmail(long id,String email) {    
    	userRepository.updateUserEmail(id, email);
    }
    
}
