package com.qdu.controller;

import java.util.List;

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
	public ModelAndView adminLogin(Admin admin, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Admin> list = adService.checkAdminLogin(admin);
		if(list.size() == 1) {
			mav.setViewName("BookList");
			model.addAttribute("book_list", adService.getAllBooks());
			return mav;
		}
		mav.setViewName("error");
		mav.addObject("errorMessage", "�û��������������");
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
	public ModelAndView deleteBooks(@PathVariable int bookid, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Book> list = adService.selectBooks(bookid);
		if(list.size() == 1)
		{
			JOptionPane.showMessageDialog(null,"ͼ��"+list.get(0).getBookid()+"."+list.get(0).getBookname()+ "ɾ���ɹ�");
			adService.deleteBooks(bookid);
			
			mav.setViewName("BookList");
			model.addAttribute("book_list", adService.getAllBooks());
			return mav;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "��ɾ����ͼ�鲻����");
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
	public ModelAndView deleteUser(@PathVariable int id, Model model) {
		ModelAndView mav = new ModelAndView();
		List<User> list = adService.selectUser(id);
		if(list.size() == 1)
		{
			adService.deleteUser(id);
			JOptionPane.showMessageDialog(null, "�û�"+list.get(0).getId()+"."+list.get(0).getUsername()+"ɾ���ɹ�");
			mav.setViewName("UserList");
			model.addAttribute("user_list", adService.getAllUsers());
			return mav;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "��ɾ�����û�������");
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
	public ModelAndView DeleteBorrowRecord(@PathVariable int rentid, @PathVariable String username, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Borrow> blist = adService.selectBorrowByRentid(rentid);
		if(blist.size() == 1)
		{
			adService.deleteBorrowRecord(rentid);
			JOptionPane.showMessageDialog(null, "���"+blist.get(0).getRentid()+"  �û�"+blist.get(0).getUsername()+" ��"+blist.get(0).getBookid()+"."+blist.get(0).getBookname()+"��Ϣɾ���ɹ�");
			mav.setViewName("BorrowingRecords");
			model.addAttribute("borrow_list", adService.selectUserByUserName(username));
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", username+"תҳ����");
			return mav;
		}
	}
	@RequestMapping(value = "/set_state1.do/{rentid}.do,{username}.do", method = RequestMethod.GET)
	public ModelAndView SetState1(@PathVariable int rentid, @PathVariable String username, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Borrow> blist = adService.selectBorrowByRentid(rentid);
		if(blist.size() == 1)
		{
			adService.updateBorrowRecordState1(rentid);
			JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
			mav.setViewName("BorrowingRecords");
			model.addAttribute("borrow_list", adService.selectUserByUserName(username));
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", username+"תҳ����");
			return mav;
		}
	}
	@RequestMapping(value = "/set_state2.do/{rentid}.do,{username}.do", method = RequestMethod.GET)
	public ModelAndView SetState2(@PathVariable int rentid, @PathVariable String username, Model model) {
		ModelAndView mav = new ModelAndView();
		List<Borrow> blist = adService.selectBorrowByRentid(rentid);
		if(blist.size() == 1)
		{
			adService.updateBorrowRecordState2(rentid);
			JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
			mav.setViewName("BorrowingRecords");
			model.addAttribute("borrow_list", adService.selectUserByUserName(username));
			return mav;
		}
		else
		{
			mav.setViewName("error");
			mav.addObject("errorMessage", username+"תҳ����");
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
			mav.addObject("errorMessage", "û���û���Ϊ"+username+"����Ϣ");
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
			mav.addObject("errorMessage", "û���û���Ϊ"+bookname+"����Ϣ");
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
			mav.addObject("errorMessage", "û�д������Ϣ");
			return mav;
		}
	}
	
	
	
	/*-------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/add_book.do", method = RequestMethod.POST)
	public ModelAndView add_book(Book book, Model model){
		adService.add_Book(book);
		ModelAndView mav = new ModelAndView();
		JOptionPane.showMessageDialog(null, "��ӳɹ�");
		mav.setViewName("BookList");
		model.addAttribute("book_list", adService.getAllBooks());
		return mav;
	}
	
	@RequestMapping(value = "/add_user.do", method = RequestMethod.POST)
	public ModelAndView add_user(User user, Model model){
		adService.add_user(user);
		ModelAndView mav = new ModelAndView();
		JOptionPane.showMessageDialog(null, "��ӳɹ�");
		mav.setViewName("UserList");
		model.addAttribute("user_list", adService.getAllUsers());
		return mav;
	}
	
	@RequestMapping(value="/update_user.do",method=RequestMethod.POST)
	public ModelAndView update_user(User user, Model model){
		adService.update_user(user);
		ModelAndView mav = new ModelAndView();
		JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
		mav.setViewName("UserList");
		model.addAttribute("user_list", adService.getAllUsers());
		return mav;
	}
	@RequestMapping(value = "/update_book.do", method = RequestMethod.POST)
	public ModelAndView update_book(Book book,Model model){
		adService.update_Book(book);
		ModelAndView mav = new ModelAndView();
		JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
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
