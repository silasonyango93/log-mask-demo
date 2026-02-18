package com.kcb.interview.silasonyango.books.service.impl;

import com.kcb.interview.silasonyango.books.entity.BookEntity;
import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.mapper.BookMapper;
import com.kcb.interview.silasonyango.books.repository.BookRepository;
import com.kcb.interview.silasonyango.books.service.BookService;
import com.kcb.interview.silasonyango.books.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository repository;
    private final JsonUtil jsonUtil;
    private final BookMapper bookMapper;

    public BookDto create(BookDto dto) {

        log.info("Creating book (masked): {}", jsonUtil.toJsonString(dto));

        //Save created book to database
        BookEntity bookRequest = bookMapper.toEntity(dto);
        BookEntity saved = repository.save(bookRequest);

        BookDto result = bookMapper.toDto(saved);
        log.info("Created book (masked): {}", jsonUtil.toJsonString(result));

        return result;
    }

    public List<BookDto> findAll() {
        List<BookDto> result = repository.findAll().stream().map(bookMapper::toDto).toList();
        log.info("Listing books (masked): {}", jsonUtil.toJsonString(result));
        return result;
    }

    public BookDto findById(Long id) {
        log.info("Finding book by id: {}", id);
        BookDto result = repository.findById(id).map(bookMapper::toDto).orElseThrow();
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
        BookDto result = bookMapper.toDto(repository.save(bookEntity));

        log.info("Updated book (masked): {}", jsonUtil.toJsonString(result));

        return result;
    }

    public void delete(Long id) {
        log.info("Deleting book with id: {}", id);
        repository.deleteById(id);
    }
}

