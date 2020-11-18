package com.qdu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qdu.bean.Admin;
import com.qdu.bean.User;
import com.qdu.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView userRegister(User user) {
		ModelAndView mav = new ModelAndView();
		List<User> commonNameList = userService.checkNameCommon(user);
		if(user.getUsername().length()<4 || user.getUsername().length()>16) {
			mav.setViewName("error");
			mav.addObject("errorMessage", "�û���Ӧ����4-16λ�ַ�!");
			return mav;
		}
		else if(commonNameList.size() == 1) {
			mav.setViewName("error");
			mav.addObject("errorMessage", "�û����ظ���!");
			return mav;
		}
		else if(!user.getPassword().matches("[0-9A-Za-z]{6,16}")) {
			mav.setViewName("error");
			mav.addObject("errorMessage", "����ֻ�ܰ�����ĸ�����֣��ҳ�����6-16λ��");
			return mav;
		}
		else if(user.getGender() == -1) {
			mav.setViewName("error");
			mav.addObject("errorMessage", "�Ա���Ϊ�գ�");
			return mav;
		}
		else if(!user.getEmail().matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$")) {
			mav.setViewName("error");
			mav.addObject("errorMessage", "����д��ȷ�����䣡");
			return mav;
		}
		else if(user.getPhone().matches("^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0-35689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[13589]\\d)\\d{7}$")) {
			mav.setViewName("error");
			mav.addObject("errorMessage", "����д��ȷ���ֻ��ţ�");
			return mav;
		}
		userService.userRegister(user);
		mav.setViewName("login");
		return mav;
	}
	
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public ModelAndView userLogin(User user) {
		ModelAndView mav = new ModelAndView();
		List<User> list = userService.checkUserLogin(user);
		if(list.size() == 1) {
			mav.setViewName("success");
			return mav;
		}
		mav.setViewName("error");
		mav.addObject("errorMessage", "�û��������������");
		return mav;
	}
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public ModelAndView adminLogin(Admin admin) {
		ModelAndView mav = new ModelAndView();
		List<Admin> list = userService.checkAdminLogin(admin);
		if(list.size() == 1) {
			mav.setViewName("success");
			return mav;
		}
		mav.setViewName("error");
		mav.addObject("errorMessage", "�û��������������");
		return mav;
	}
}
