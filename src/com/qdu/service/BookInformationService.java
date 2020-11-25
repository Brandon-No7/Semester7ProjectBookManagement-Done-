package com.qdu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qdu.bean.Book;
import com.qdu.bean.RentInformation;
import com.qdu.dao.BookInformationMapper;

@Service
public class BookInformationService {

	@Autowired
	private BookInformationMapper bookInformationMapper;

	public List<Book> searchBookByName(String bookname) {
		return bookInformationMapper.searchBookByName(bookname);
	}

	public List<Book> searchBookById(String bookid) {
		return bookInformationMapper.searchBookById(bookid);
	}

	public void submitRentInformation(RentInformation rent) {
		bookInformationMapper.submitRentInformation(rent);
	}

	public List<RentInformation> checkRentInformationOnly(RentInformation rent) {
		return bookInformationMapper.checkRentInformationOnly(rent);
	}

	public List<String> allcategory() {
		return bookInformationMapper.allcategory();
	}

	public List<Book> searchBookByCategory(String categoryname) {
		return bookInformationMapper.searchBookByCategory(categoryname);
	}

	public List<RentInformation> userOrderbook(String username) {
		return bookInformationMapper.userOrderbook(username);
	}

	public void cancelOrder(String rentid) {
		bookInformationMapper.cancelOrder(rentid);
	}

	public List<RentInformation> userRentbook(String username) {
		return bookInformationMapper.userRentbook(username);
	}

	public List<RentInformation> userOvertime(Date datenow, String username) {
		return bookInformationMapper.userOvertime(datenow, username);
	}

	public List<RentInformation> userReturnbook(String username) {
		return bookInformationMapper.userReturnbook(username);
	}

	public List<RentInformation> hasRent(String username) {
		return bookInformationMapper.hasRent(username);
	}

	public List<RentInformation> checkRentOnlyByBookId(String username, int bookid) {
		return bookInformationMapper.checkRentOnlyByBookId(username, bookid);
	}

	public int checkBookNumber(int bookid) {
		return bookInformationMapper.checkBookNumber(bookid);
	}

	public void changeNumber(int numbernow, int bookid) {
		bookInformationMapper.changeNumber(numbernow, bookid);
	}

	public int checkBookNumberByRentid(int rentid) {
		return bookInformationMapper.checkBookNumberByRentid(rentid);
	}

	public void changeNumberByRengid(int numbernow, int rentid) {
		bookInformationMapper.changeNumberByRengid(numbernow, rentid);
	}
	
}
