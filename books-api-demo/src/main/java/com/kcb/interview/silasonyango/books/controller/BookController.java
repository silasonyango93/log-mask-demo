package com.kcb.interview.silasonyango.books.controller;

import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-api/v1/book-management/books")
@RequiredArgsConstructor
public class BookController {

  private final BookServiceImpl service;

  @PostMapping
  public ResponseEntity<BookDto> create(@RequestBody BookDto dto) {

    BookDto created = service.create(dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping
  public ResponseEntity<List<BookDto>> all() {

    List<BookDto> books = service.findAll();

    return ResponseEntity.ok(books);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> byId(@PathVariable Long id) {

    BookDto book = service.findById(id);

    return ResponseEntity.ok(book);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookDto> update(@PathVariable Long id,
      @RequestBody BookDto dto) {

    BookDto updated = service.update(id, dto);

    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {

    service.delete(id);

    return ResponseEntity.noContent().build();
  }
}

