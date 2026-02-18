package com.kcb.interview.silasonyango.books.repository;

import com.kcb.interview.silasonyango.books.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

}

