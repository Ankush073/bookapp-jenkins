package com.coforge.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.coforge.entities.Book;
import com.coforge.repositories.BookRepository;

@Repository
public class BookDao {
	
	@Autowired
	BookRepository repository;

	
	public List<Book> getAllBooks() {
		return repository.findAll();
	}
	
	public  Book addBook(Book book) {
		return repository.save(book);
	}
	
	public Optional<Book> getBookById(long id) {
		return repository.findById(id);
	}

	public void deleteBook(Long id) {
		 repository.deleteById(id);
		 System.out.println("book deleted successfully");
		
	}
	
	public List<Book> findByAuthor(String author) {
        return repository.getAllBooksByAuthorName(author);
    }
	
	public List<Book> findByTitle(String title) {
		return repository.findByTitle(title);
	}
	
	
	
}