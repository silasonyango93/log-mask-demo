package com.kcb.interview.silasonyango.books.service;

import com.kcb.interview.silasonyango.books.domain.Book;
import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookDto create(BookDto dto) {
        Book saved = repository.save(toEntity(dto));
        return toDto(saved);
    }

    public List<BookDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public BookDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElseThrow();
    }

    public BookDto update(Long id, BookDto dto) {
        Book book = repository.findById(id).orElseThrow();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setEmail(dto.getEmail());
        book.setPhoneNumber(dto.getPhoneNumber());
        return toDto(repository.save(book));
    }

    public void delete(Long id) {
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

