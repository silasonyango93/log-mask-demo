package com.kcb.interview.silasonyango.books.web;

import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto dto) {
        log.info("Creating book: {}", dto);
        return service.create(dto);
    }

    @GetMapping
    public List<BookDto> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public BookDto byId(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @RequestBody BookDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

