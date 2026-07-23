package com.coforge.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coforge.entities.Book;
import com.coforge.service.BookService;

import jakarta.validation.Valid;

@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService service;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello Book Controller");
    }

    // Add Book
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book savedBook = service.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Get All Books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {

        logger.info("getAllBooks API started at {}", LocalDateTime.now());

        List<Book> books = service.getAllBooks();

        logger.info("getAllBooks API completed at {}", LocalDateTime.now());

        return ResponseEntity.ok(books);
    }

    // Get Book By ID
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        Book book = service.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Update Book
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book book) {

        Book updatedBook = service.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete Book
    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id) {

        service.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK)
                             .body("Book Deleted Successfully");
    }

    // Find By Author
    @GetMapping("/books/author/{author}")
    public ResponseEntity<List<Book>> findByAuthor(@PathVariable String author) {

        List<Book> books = service.findByAuthor(author);
        return ResponseEntity.ok(books);
    }

    // Find By Title
    @GetMapping("/books/title/{title}")
    public ResponseEntity<List<Book>> findByTitle(@PathVariable String title) {

        List<Book> books = service.findByTitle(title);
        return ResponseEntity.ok(books);
    }
}