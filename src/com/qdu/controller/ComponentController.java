package com.qdu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ComponentController {

	@RequestMapping("/home.do")
	public String homepage() {
		return "home";
	}
	
	@RequestMapping("/backtouserlist.do")
	public String backToUserList() {
		return "UserList";
	}
	
	@RequestMapping("/backtoborrowingrecords.do")
	public String backToBorrowingRecords() {
		return "BorrowingRecords";
	}
}
