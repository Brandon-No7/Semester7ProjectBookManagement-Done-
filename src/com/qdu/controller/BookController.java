package com.qdu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qdu.bean.Book;
import com.qdu.bean.RentInformation;
import com.qdu.service.BookInformationService;

@Controller
public class BookController {
	
	@Autowired
	private BookInformationService bookInformationService;
	
	@RequestMapping(value = "/search.do")
	public ModelAndView searchBookInformation(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("list");
		List<Book> bookList = bookInformationService.searchBookByName((String)request.getParameter("bookname"));
		HttpSession session = request.getSession();
		System.out.println(bookList);
		mav.addObject("booklist", bookList);
		mav.addObject("username", session.getAttribute("username"));
		return mav;
	}
	
	@RequestMapping(value = "/rent.do")
	public ModelAndView rentBook(HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView("rent");
		HttpSession session = request.getSession();
		List<Book> book = bookInformationService.searchBookById((String)request.getParameter("bookId"));
		if(book.get(0).getCurrentnumber() == 0) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String msg = null;
			msg = "alert( 'The book has been borrowed out!' );location.href='home.do'";
			out.print("<script type='text/javascript'>" + msg + "</script>");
			out.flush();
			out.close();
		}
		mav.addObject("booklist", book);
		mav.addObject("overtime", bookInformationService.userOvertime(new Date(), (String)request.getSession().getAttribute("username")).size());
		mav.addObject("hasrent", bookInformationService.hasRent((String)request.getSession().getAttribute("username")).size());
		mav.addObject("rentonly", bookInformationService.checkRentOnlyByBookId((String)request.getSession().getAttribute("username"),Integer.parseInt((String)request.getParameter("bookId"))).size());
		mav.addObject("username", session.getAttribute("username"));
		return mav;
	}
	
	@RequestMapping(value = "/rentbookInformation.do")
	public ModelAndView submitRentbookInformation(HttpServletRequest request, RentInformation rent) {
		ModelAndView mav = new ModelAndView();
		Date rightnow = new Date();
		Date enddate = addAndSubtractDaysByGetTime(rightnow, Integer.parseInt(request.getParameter("time")));
		rent.setStartdate(rightnow);
		rent.setEnddate(enddate);
		rent.setBookid(Integer.parseInt(request.getParameter("bookid")));
		rent.setBookname(request.getParameter("bookname"));
		rent.setDay(Integer.parseInt(request.getParameter("time")));
		rent.setUsername(request.getParameter("username"));
		rent.setState(0);
		mav.setViewName("home");
		int number = bookInformationService.checkBookNumber(Integer.parseInt(request.getParameter("bookid")));
		int numbernow = number - 1;
		bookInformationService.changeNumber(numbernow, Integer.parseInt(request.getParameter("bookid")));
		bookInformationService.submitRentInformation(rent);
		return mav;
	}
	
	@RequestMapping("/allCategory.do")
	public ModelAndView allCategory() {
		ModelAndView mav = new ModelAndView("categorylist");
		List<String> categorylist = bookInformationService.allcategory();
		mav.addObject("categorylist", categorylist);
		return mav;
	}
	
	@RequestMapping("/bookOfCategory.do")
	public ModelAndView bookOfCategory(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("list");
		System.out.println((String)request.getParameter("categoryname"));
		List<Book> booklist = bookInformationService.searchBookByCategory((String)request.getParameter("categoryname"));
		mav.addObject("booklist", booklist);
		return mav;
	}
	
	//
	@RequestMapping("/userorderbook.do")
	public ModelAndView userOrderBook(HttpServletRequest request) {
		ModelAndView mav = new  ModelAndView("user_order_book");//逻辑视图名
		List<RentInformation> list = bookInformationService.userOrderbook((String)request.getSession().getAttribute("username"));
		mav.addObject("bookorderlist", list);
		return mav;
	}
	
	@RequestMapping("/cancelorder.do")
	public ModelAndView cancelOrder(HttpServletRequest request) {
		ModelAndView mav = new  ModelAndView("home");
		int number = bookInformationService.checkBookNumberByRentid(Integer.parseInt(request.getParameter("rentId")));
		int numbernow = number + 1;
		bookInformationService.changeNumberByRengid(numbernow, Integer.parseInt(request.getParameter("rentId")));
		bookInformationService.cancelOrder((String)request.getParameter("rentId"));
		return mav;
	}
	
	@RequestMapping("/userrentbook.do")
	public ModelAndView userRentBook(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("user_rent_book");
		List<RentInformation> list = bookInformationService.userRentbook((String)request.getSession().getAttribute("username"));
		mav.addObject("bookrentlist", list);
		return mav;
	}
	
	@RequestMapping("/userovertime.do")
	public ModelAndView userOvertime(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("user_book_overtime");
		Date date = new Date();
		List<RentInformation> list = bookInformationService.userOvertime(date, (String)request.getSession().getAttribute("username"));
		mav.addObject("bookrentlist", list);
		return mav;
	}
	
	@RequestMapping("/userreturnback.do")
	public ModelAndView userReturnBack(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("user_return");
		List<RentInformation> list = bookInformationService.userReturnbook((String)request.getSession().getAttribute("username"));
		mav.addObject("bookreturnlist", list);
		return mav;
	}
	
	public static Date addAndSubtractDaysByGetTime(Date dateTime/*待处理的日期*/,int n/*加减天数*/){
	     SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	     System.out.println(df.format(new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L))); 
	     return new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L); 
	}
	
}
