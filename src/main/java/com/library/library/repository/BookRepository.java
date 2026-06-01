package com.library.library.repository;

import com.library.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    
    List<Book> findTop6ByOrderByViewCountDesc();
    
    List<Book> findTop6ByOrderByAddedDateDesc();
    
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:query% OR b.author LIKE %:query% OR b.category LIKE %:query%")
    List<Book> searchBooks(@Param("query") String query);
}
