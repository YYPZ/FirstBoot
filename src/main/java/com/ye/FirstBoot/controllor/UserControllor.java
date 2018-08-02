package com.ye.FirstBoot.controllor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ye.FirstBoot.domain.User;
import com.ye.FirstBoot.repository.UserRepository;
import com.ye.FirstBoot.service.UserService;

@Controller
public class UserControllor {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	

	@RequestMapping(path="getUserInfo",method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView getUserInfo(long id) {
		 Optional<User> userOptional= userRepository.findById(id);
		 ModelAndView mav = new ModelAndView("index");
		 mav.addObject("user", userOptional.get());
		
		 mav.addObject("test","sdfghsg");
		return mav;
	}


}
