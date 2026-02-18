package com.kcb.interview.silasonyango.books.service;

import com.kcb.interview.silasonyango.books.entity.BookEntity;
import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.repository.BookRepository;
import com.kcb.interview.silasonyango.books.util.JsonUtil;
import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository repository;
    private final PIIMaskingService maskingService;
    private final JsonUtil jsonUtil;

    public BookDto create(BookDto dto) {

        log.info("Creating book (masked): {}", jsonUtil.toJsonString(dto));

        //Save created book to database
        BookEntity saved = repository.save(toEntity(dto));

        BookDto result = toDto(saved);
        log.info("Created book (masked): {}", jsonUtil.toJsonString(result));

        return result;
    }

    public List<BookDto> findAll() {
        List<BookDto> result = repository.findAll().stream().map(this::toDto).toList();
        log.info("Listing books (masked): {}", jsonUtil.toJsonString(result));
        return result;
    }

    public BookDto findById(Long id) {
        log.info("Finding book by id: {}", id);
        BookDto result = repository.findById(id).map(this::toDto).orElseThrow();
        log.info("Found book (masked): {}", jsonUtil.toJsonString(result));
        return result;
    }

    public BookDto update(Long id, BookDto dto) {
        log.info("Updating book {} with data (masked): {}", id, jsonUtil.toJsonString(dto));
        BookEntity bookEntity = repository.findById(id).orElseThrow();
        bookEntity.setTitle(dto.getTitle());
        bookEntity.setAuthor(dto.getAuthor());
        bookEntity.setEmail(dto.getEmail());
        bookEntity.setPhoneNumber(dto.getPhoneNumber());
        BookDto result = toDto(repository.save(bookEntity));
        log.info("Updated book (masked): {}", jsonUtil.toJsonString(result));
        return result;
    }

    public void delete(Long id) {
        log.info("Deleting book with id: {}", id);
        repository.deleteById(id);
    }

    private BookEntity toEntity(BookDto dto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(dto.getId());
        bookEntity.setTitle(dto.getTitle());
        bookEntity.setAuthor(dto.getAuthor());
        bookEntity.setEmail(dto.getEmail());
        bookEntity.setPhoneNumber(dto.getPhoneNumber());
        return bookEntity;
    }

    private BookDto toDto(BookEntity bookEntity) {
        BookDto dto = new BookDto();
        dto.setId(bookEntity.getId());
        dto.setTitle(bookEntity.getTitle());
        dto.setAuthor(bookEntity.getAuthor());
        dto.setEmail(bookEntity.getEmail());
        dto.setPhoneNumber(bookEntity.getPhoneNumber());
        return dto;
    }
}

