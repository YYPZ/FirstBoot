package com.ye.FirstBoot.controllor;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
	public ModelAndView getUserInfo(long id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		 Optional<User> userOptional= userRepository.findById(id);
		 ModelAndView mav = new ModelAndView("index");
		 mav.addObject("user", userOptional.get());
	    System.out.println(request.getParameter("id"));
	    request.setAttribute("id", "attr");
	    System.out.println(request.getAttribute("id"));
	    request.setAttribute("XSS_TEST", request.getParameter("XSS_TEST"));
	    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request2 = requestAttributes.getRequest();
	    HttpServletResponse response2 = requestAttributes.getResponse();
	    //从session里面获取对应的值
	    String myValue = (String) requestAttributes.getAttribute("my_value",RequestAttributes.SCOPE_SESSION);
	    System.out.println(myValue);
	    requestAttributes.setAttribute("my_value", "FirstBoot", RequestAttributes.SCOPE_SESSION);
	     myValue = (String) requestAttributes.getAttribute("my_value",RequestAttributes.SCOPE_SESSION);
	     System.out.println(myValue);
		return mav;
	}

}
