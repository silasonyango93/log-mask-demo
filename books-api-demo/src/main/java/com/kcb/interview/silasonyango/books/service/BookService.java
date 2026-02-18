package com.kcb.interview.silasonyango.books.service;

import com.kcb.interview.silasonyango.books.dto.BookDto;
import java.util.List;

public interface BookService {

  BookDto create(BookDto dto);

  List<BookDto> findAll();

  BookDto findById(Long id);

  BookDto update(Long id, BookDto dto);

  void delete(Long id);
}
