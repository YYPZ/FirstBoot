package com.ye.FirstBoot.controllor;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ye.FirstBoot.dataAccess.jpa.domain.User;
import com.ye.FirstBoot.dataAccess.jpa.repository.UserRepository;
import com.ye.FirstBoot.service.UserService;

@Controller
public class UserControllor {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@RequestMapping(path = "getUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getUserInfo(long id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Optional<User> userOptional = userRepository.findById(id);
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("user", userOptional.get());
		logger.info(request.getParameter("id"));
		request.setAttribute("id", "attr");
		System.out.println(request.getAttribute("id"));
		request.setAttribute("XSS_TEST", request.getParameter("XSS_TEST"));
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request2 = requestAttributes.getRequest();
		HttpServletResponse response2 = requestAttributes.getResponse();
		// 从session里面获取对应的值
		String myValue = (String) requestAttributes.getAttribute("my_value", RequestAttributes.SCOPE_SESSION);
		System.out.println(myValue);
		requestAttributes.setAttribute("my_value", "FirstBoot", RequestAttributes.SCOPE_SESSION);
		myValue = (String) requestAttributes.getAttribute("my_value", RequestAttributes.SCOPE_SESSION);
		logger.info(myValue);
		return mav;
	}

}
