package com.kcb.interview.silasonyango.books.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BookRepository bookRepository;

  @BeforeEach
  void setup() {
    // Clear repository before each test
    bookRepository.deleteAll();
  }

  @Test
  void testCreateBook() throws Exception {
    BookDto dto = BookDto.builder()
        .title("Test Book")
        .author("John Doe")
        .email("john@example.com")
        .phoneNumber("123456789")
        .build();

    mockMvc.perform(post("/client-api/v1/book-management/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title", is("Test Book")))
        .andExpect(jsonPath("$.author", is("John Doe")));
  }
}
