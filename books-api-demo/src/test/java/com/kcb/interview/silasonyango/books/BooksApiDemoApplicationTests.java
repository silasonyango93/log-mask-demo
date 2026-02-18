package com.kcb.interview.silasonyango.books;

import com.kcb.interview.silasonyango.books.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BooksApiDemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void logsUseMaskedValuesButDatabaseStoresPlainValues() {
        BookDto request = new BookDto();
        request.setTitle("Test Book");
        request.setAuthor("Author");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumber("1234567890");

        ResponseEntity<BookDto> createResponse =
                restTemplate.postForEntity("/books", request, BookDto.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        BookDto created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(created.getPhoneNumber()).isEqualTo("1234567890");

        ResponseEntity<BookDto> getResponse =
                restTemplate.exchange("/books/{id}", HttpMethod.GET, HttpEntity.EMPTY, BookDto.class, created.getId());
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        BookDto fetched = getResponse.getBody();
        assertThat(fetched).isNotNull();
        assertThat(fetched.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(fetched.getPhoneNumber()).isEqualTo("1234567890");
    }
}

