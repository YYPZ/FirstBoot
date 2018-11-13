package com.ye.FirstBoot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ye.FirstBoot.dataAccess.mybatis.dao.UserDao;
import com.ye.FirstBoot.dataAccess.mybatis.model.User;

public class CustomUserService implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user=userDao.selectByAccount(account);
        if (user == null) {
            throw new UsernameNotFoundException("用户名账号不存在");
        }
        
        logger.info("username:"+user.getUsername()+"; password:"+user.getPassword());
        return user;
    }
}