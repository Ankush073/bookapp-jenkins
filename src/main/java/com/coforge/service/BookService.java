package com.coforge.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coforge.Dao.BookDao;
import com.coforge.entities.Book;
import com.coforge.exceptions.BookNotFoundException;

@Service
public class BookService {

	@Autowired
	BookDao dao;
	
	public List<Book> getAllBooks() {
		return dao.getAllBooks();
	}
	
	public  Book addBook(Book book) {
		return dao.addBook(book);
	}
	public Book getBookById(long id) {
		return dao.getBookById(id)
				.orElseThrow(()->new BookNotFoundException("Book with this id does not exist"));
	}

	public Book updateBook(Long id, Book book) {
		
		Book exBook=dao.getBookById(id).orElseThrow(()->new BookNotFoundException("Book with this id does not exist"));;
		
			if(book.getTitle()!=null)
			exBook.setTitle(book.getTitle());
			if(book.getAuthor()!=null)
			exBook.setAuthor(book.getAuthor());
			if(book.getPrice()>=0)
			exBook.setPrice(book.getPrice());
			return dao.addBook(exBook);
		
		

	}

	public void deleteBook(Long id) {
		Book exBook = dao.getBookById(id).orElseThrow(()->new BookNotFoundException("Book with this id does not exist"));;
		if(exBook!=null)
		 dao.deleteBook(id);
		else 
			 System.out.println("No Book Available");
		
	}
	public List<Book> findByAuthor(String author) {
        return dao.findByAuthor(author);
    }
	
	public List<Book> findByTitle(String title) {
		return dao.findByTitle(title);
	}

}