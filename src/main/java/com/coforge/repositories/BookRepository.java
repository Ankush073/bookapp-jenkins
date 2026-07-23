package com.coforge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coforge.entities.Book;

@Repository
public interface BookRepository  extends JpaRepository<Book,Long>{
//	List<Book>findByAuthor(String author);
	
//	Native Query
//	@Query(value="select * from book where title=?",nativeQuery=true)
//	jpql queries
//	@Query(value="select b from Book b  where title=title")
	
	
	@Query(name="findByTitle")
	List<Book> findByTitle(@Param("title") String title);
	
	Book findByAuthorMobile(String authorMobile);
	
	
//	Native Query
//	@Query(value="select * from book where author=?",nativeQuery=true)
//	jpql queries
//	@Query(value="select b from Book b  where author=author")
	
	@Query(name="getAllBooksByAuthor")
	
	List<Book> getAllBooksByAuthorName(@Param("author") String author);
	

}
