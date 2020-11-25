package com.qdu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qdu.bean.User;
import com.qdu.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public ModelAndView userRegister(User user){
		ModelAndView mav = new ModelAndView();
		List<User> commonNameList = userService.checkNameCommon(user);
		if(commonNameList.size() == 1) {
			mav.addObject("errorMessage", "registererrpr");
			return mav;
		}
		userService.userRegister(user);
		mav.setViewName("register_success");
		return mav;
	}
	
	@RequestMapping(value = "/userLogin.do", method = RequestMethod.POST)
	public ModelAndView userLogin(User user, HttpServletRequest request, Model model,HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<User> list = userService.checkUserLogin(user);
		if(list.size() == 1) {
			mav.setViewName("home");
			mav.addObject("username", user.getUsername());
			HttpSession session = request.getSession();
			session.setAttribute("username", user.getUsername());
			return mav;
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String msg = null;
		msg = "alert( 'Username or Password Wrong!' );location.href='login.jsp'";
		out.print("<script type='text/javascript'>" + msg + "</script>");
		out.flush();
		out.close();
		return mav;
	}
	
	@RequestMapping("/changePassword.do")
	public ModelAndView changePassword(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("change_password");
		String password = userService.getUserPassword((String)request.getSession().getAttribute("username"));
		mav.addObject("password", password);
		return mav;
	}
	
	@RequestMapping("/changeNewPassword.do")
	public String changeNewPassword(HttpServletRequest request) {
		userService.changePassword((String)request.getParameter("newPassword"), (String)request.getSession().getAttribute("username"));
		return "home";
	}
}
