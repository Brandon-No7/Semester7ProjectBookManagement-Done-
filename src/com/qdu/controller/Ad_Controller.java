package com.qdu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qdu.bean.Admin;
import com.qdu.bean.Book;
import com.qdu.bean.Borrow;
import com.qdu.bean.User;
import com.qdu.service.AdService;

@Controller
public class Ad_Controller {
	@Autowired
	private AdService adService;

	@RequestMapping(value = "/adminLogin.do", method = RequestMethod.POST)
	public ModelAndView adminLogin(Admin admin, Model model,HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<Admin> list = adService.checkAdminLogin(admin);
		if(list.size() == 1) {
			mav.setViewName("BookList");
			model.addAttribute("book_list", adService.getAllBooks());
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
	@RequestMapping(value = "/backtobooklist.do", method = RequestMethod.GET)
	public ModelAndView BackToBookList(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("BookList");
		model.addAttribute("book_list", adService.getAllBooks());
		return mav;
	}
	
	
	@RequestMapping(value = "/delete_book.do/{bookid}.do", method = RequestMethod.GET)
	public ModelAndView deleteBooks(@PathVariable int bookid, Model model, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<Book> list = adService.selectBooks(bookid);
		if(list.size() == 1)
		{
			adService.deleteBooks(bookid);
			adService.deleteRentInformationOfUser(bookid);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String msg = null;
			msg = "alert( 'Delete success!' );location.href='/BookManagementSystem/backtobooklist.do'";
			out.print("<script type='text/javascript'>" + msg + "</script>");
			out.flush();
			out.close();
			mav.setViewName("BookList");
			model.addAttribute("book_list", adService.getAllBooks());
			return mav;
		}
		else
		{
			PrintWriter out = response.getWriter();
			String msg = null;
			msg = "alert( 'Delete failed!' );location.href='/BookManagementSystem/backtobooklist.do'";
			out.print("<script type='text/javascript'>" + msg + "</script>");
			out.flush();
			out.close();
			mav.setViewName("BookList");
			model.addAttribute("book_list", adService.getAllBooks());
			return mav;
		}
	}
	
	@RequestMapping(value = "/go_to_userinfo.do", method = RequestMethod.GET)
	public ModelAndView goToUserinfo(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("UserList");
		model.addAttribute("user_list", adService.getAllUsers());
		return mav;
	}
	
	
	@RequestMapping(value = "/delete_user.do/{id}.do", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable int id, Model model, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<User> list = adService.selectUser(id);
		if(list.size() == 1)
		{
			adService.deleteUser(id);
			PrintWriter out = response.getWriter();
			String msg = null;
			msg = "alert( 'Delete success!' );location.href='/BookManagementSystem/go_to_userinfo.do'";
			out.print("<script type='text/javascript'>" + msg + "</script>");
			out.flush();
			out.close();
			mav.setViewName("UserList");
			model.addAttribute("user_list", adService.getAllUsers());
			return mav;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "所删除的用户不存在");
			mav.setViewName("UserList");
			model.addAttribute("user_list", adService.getAllUsers());
			return mav;
		}
	}
	@RequestMapping(value = "/go_to_userbook.do/{username}.do", method = RequestMethod.GET)
	public ModelAndView goToUserBook(@PathVariable String username, Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("BorrowingRecords");
		model.addAttribute("borrow_list", adService.selectUserByUserName(username));
		return mav;
	}
	
	@RequestMapping(value = "/delete_borrow.do/{rentid}.do,{username}.do", method = RequestMethod.GET)
	public ModelAndView DeleteBorrowRecord(@PathVariable int rentid, @PathVariable String username, Model model, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<Borrow> blist = adService.selectBorrowByRentid(rentid);
		if(blist.size() == 1)
		{
			adService.deleteBorrowRecord(rentid);
			PrintWriter out = response.getWriter();
			String msg = null;
			msg = "alert( 'Delete success!' );location.href='/BookManagementSystem/backtoborrowingrecords.do'";
			out.print("<script type='text/javascript'>" + msg + "</script>");
			out.flush();
			out.close();
			mav.setViewName("BorrowingRecords");
			model.addAttribute("borrow_list", adService.selectUserByUserName(username));
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", username+"转页错误");
			return mav;
		}
	}
	@RequestMapping(value = "/set_state1.do/{rentid}.do,{username}.do", method = RequestMethod.GET)
	public ModelAndView SetState1(@PathVariable int rentid, @PathVariable String username, Model model, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<Borrow> blist = adService.selectBorrowByRentid(rentid);
		if(blist.size() == 1)
		{
			adService.updateBorrowRecordState1(rentid);
			JOptionPane.showMessageDialog(null, "Update success!");
			mav.setViewName("BorrowingRecords");
			model.addAttribute("borrow_list", adService.selectUserByUserName(username));
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", username+"转页错误");
			return mav;
		}
	}
	@RequestMapping(value = "/set_state2.do/{rentid}.do,{username}.do", method = RequestMethod.GET)
	public ModelAndView SetState2(@PathVariable int rentid, @PathVariable String username, Model model, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<Borrow> blist = adService.selectBorrowByRentid(rentid);
		if(blist.size() == 1)
		{
			adService.addOneBook(rentid);
			adService.updateBorrowRecordState2(rentid);
			JOptionPane.showMessageDialog(null, "Update success!");
			mav.setViewName("BorrowingRecords");
			model.addAttribute("borrow_list", adService.selectUserByUserName(username));
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", username+"转页错误");
			return mav;
		}
	}
	@RequestMapping(value = "/searchbyusername.do", method = RequestMethod.GET)
	public ModelAndView SearchByUserName(String username, Model model) {
		ModelAndView mav = new ModelAndView();
		List<User> list = adService.searchByUserName(username);
		if(list.size() == 1)
		{
			mav.setViewName("UserList");
			model.addAttribute("user_list", list);
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", "没有用户名为"+username+"的信息");
			return mav;
		}
	}
	@RequestMapping(value = "/go_to_update_book.do/{bookid}.do", method = RequestMethod.GET)
	public ModelAndView goToUpdateBook(@PathVariable int bookid, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Book> blist = adService.selectBooks(bookid);
		mav.setViewName("update");
		model.addAttribute("book_list", blist);
		return mav;

	}
	@RequestMapping(value = "/go_to_update_user.do/{id}.do", method = RequestMethod.GET)
	public ModelAndView goToUpdateUser(@PathVariable int id, Model model) {
		ModelAndView mav = new ModelAndView();
		List<User> ulist = adService.selectUser(id);
		mav.setViewName("update_user");
		model.addAttribute("user_list", ulist);
		return mav;

	}
	@RequestMapping(value = "/searchbybookname.do", method = RequestMethod.GET)
	public ModelAndView SearchByBookName(String bookname, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Book> list = adService.searchByBookName(bookname);
		if(list.size() != 0)
		{
			mav.setViewName("BookList");
			model.addAttribute("book_list", list);
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", "没有用户名为"+bookname+"的信息");
			return mav;
		}
	}
	@RequestMapping(value = "/showdescription.do/{bookid}.do", method = RequestMethod.GET)
	public ModelAndView ShowDescription(@PathVariable int bookid, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Book> list = adService.getbookByID(bookid);
		if(list.size() == 1)
		{
			mav.setViewName("BookInfo");
			model.addAttribute("thisbook", list.get(0));
			model.addAttribute("borrowinfolist", adService.getBorrowByBookId(bookid));
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", "没有此书的信息");
			return mav;
		}
	}
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/add_book.do", method = RequestMethod.POST)
	public ModelAndView add_book(Book book, Model model, HttpServletResponse response) throws IOException{
		adService.add_Book(book);
		ModelAndView mav = new ModelAndView();
		PrintWriter out = response.getWriter();
		String msg = null;
		msg = "alert( 'Update success!' );location.href='/BookManagementSystem/backtobooklist.do'";
		out.print("<script type='text/javascript'>" + msg + "</script>");
		out.flush();
		out.close();
		mav.setViewName("BookList");
		model.addAttribute("book_list", adService.getAllBooks());
		return mav;
	}
	
	@RequestMapping(value = "/add_user.do", method = RequestMethod.POST)
	public ModelAndView add_user(User user, Model model, HttpServletResponse response) throws IOException{
		adService.add_user(user);
		ModelAndView mav = new ModelAndView();
		PrintWriter out = response.getWriter();
		String msg = null;
		msg = "alert( 'Add success!' );location.href='/BookManagementSystem/backtouserlist.do'";
		out.print("<script type='text/javascript'>" + msg + "</script>");
		out.flush();
		out.close();
		mav.setViewName("UserList");
		model.addAttribute("user_list", adService.getAllUsers());
		return mav;
	}
	
	@RequestMapping(value="/update_user.do",method=RequestMethod.POST)
	public ModelAndView update_user(User user, Model model, HttpServletResponse response) throws IOException{
		adService.update_user(user);
		ModelAndView mav = new ModelAndView();
		JOptionPane.showMessageDialog(null, "Update success!");
		mav.setViewName("UserList");
		model.addAttribute("user_list", adService.getAllUsers());
		return mav;
	}
	@RequestMapping(value = "/update_book.do", method = RequestMethod.POST)
	public ModelAndView update_book(Book book,Model model, HttpServletResponse response) throws IOException{
		adService.update_Book(book);
		JOptionPane.showMessageDialog(null, "Update success!");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("BookList");
		model.addAttribute("book_list", adService.getAllBooks());
		return mav;
	}
	@RequestMapping(value = "/getbookByID.do", method = RequestMethod.POST)
	public List<Book> getbookByID(int book_id){
		return adService.getbookByID(book_id);
		
	}
	
	@RequestMapping(value = "/getuserByID.do", method = RequestMethod.POST)
	public List<User> getuserByID(int user_id){
		return adService.getuserByID(user_id);
		
	}
}
