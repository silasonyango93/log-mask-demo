package com.kcb.interview.silasonyango.books.service;

import com.kcb.interview.silasonyango.books.domain.Book;
import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.repository.BookRepository;
import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository repository;
    private final PIIMaskingService maskingService;

    public BookService(BookRepository repository, PIIMaskingService maskingService) {
        this.repository = repository;
        this.maskingService = maskingService;
    }

    public BookDto create(BookDto dto) {
        log.info("Creating book (masked): {}", maskingService.maskForLogging(dto));
        Book saved = repository.save(toEntity(dto));
        BookDto result = toDto(saved);
        log.info("Created book (masked): {}", maskingService.maskForLogging(result));
        return result;
    }

    public List<BookDto> findAll() {
        List<BookDto> result = repository.findAll().stream().map(this::toDto).toList();
        log.info("Listing books (masked): {}", maskingService.maskForLogging(result));
        return result;
    }

    public BookDto findById(Long id) {
        log.info("Finding book by id: {}", id);
        BookDto result = repository.findById(id).map(this::toDto).orElseThrow();
        log.info("Found book (masked): {}", maskingService.maskForLogging(result));
        return result;
    }

    public BookDto update(Long id, BookDto dto) {
        log.info("Updating book {} with data (masked): {}", id, maskingService.maskForLogging(dto));
        Book book = repository.findById(id).orElseThrow();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setEmail(dto.getEmail());
        book.setPhoneNumber(dto.getPhoneNumber());
        BookDto result = toDto(repository.save(book));
        log.info("Updated book (masked): {}", maskingService.maskForLogging(result));
        return result;
    }

    public void delete(Long id) {
        log.info("Deleting book with id: {}", id);
        repository.deleteById(id);
    }

    private Book toEntity(BookDto dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setEmail(dto.getEmail());
        book.setPhoneNumber(dto.getPhoneNumber());
        return book;
    }

    private BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setEmail(book.getEmail());
        dto.setPhoneNumber(book.getPhoneNumber());
        return dto;
    }
}

