package com.kcb.interview.silasonyango.books.repository;

import com.kcb.interview.silasonyango.books.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

