package com.kcb.interview.silasonyango.books.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.entity.BookEntity;
import com.kcb.interview.silasonyango.books.mapper.BookMapper;
import com.kcb.interview.silasonyango.books.mapper.BookMapperImpl;
import com.kcb.interview.silasonyango.books.repository.BookRepository;
import com.kcb.interview.silasonyango.books.service.impl.BookServiceImpl;
import com.kcb.interview.silasonyango.books.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

  @Mock private BookRepository repository;
  @Mock private JsonUtil jsonUtil;
  @Mock private BookMapper bookMapper;
  @InjectMocks private BookServiceImpl serviceUnderTest;

  @BeforeEach
  void setUp() {
    bookMapper = new BookMapperImpl();

    serviceUnderTest = spy(new BookServiceImpl(repository, jsonUtil, bookMapper));
  }

  @Test
  void create__shouldSaveBooksToDb() {

    BookDto bookDto = new BookDto();
    BookEntity bookEntity = createBookEntity();

    doReturn(bookEntity).when(repository).save(any(BookEntity.class));

    BookDto actual = serviceUnderTest.create(bookDto);

    //Repository is always called to save book
    verify(repository, times(1)).save(any(BookEntity.class));

    Assertions.assertEquals("joshua.bloch@example.com", actual.getEmail());
  }

  @Test
  void findAll__shouldReturnListOfBooks() {

    BookEntity bookEntity = createBookEntity();

    doReturn(java.util.List.of(bookEntity)).when(repository).findAll();

    var actual = serviceUnderTest.findAll();

    verify(repository, times(1)).findAll();

    Assertions.assertEquals(1, actual.size());
    Assertions.assertEquals("joshua.bloch@example.com", actual.get(0).getEmail());
  }

  @Test
  void findById__shouldReturnBookWhenExists() {

    BookEntity bookEntity = createBookEntity();

    doReturn(java.util.Optional.of(bookEntity)).when(repository).findById(1L);

    var actual = serviceUnderTest.findById(1L);

    verify(repository, times(1)).findById(1L);

    Assertions.assertEquals("Joshua Bloch", actual.getAuthor());
  }

  @Test
  void update__shouldUpdateExistingBook() {

    BookEntity existing = createBookEntity();

    BookDto updateDto = new BookDto();
    updateDto.setTitle("Clean Code");
    updateDto.setAuthor("Robert Martin");
    updateDto.setEmail("unclebob@example.com");
    updateDto.setPhoneNumber("0799999999");

    doReturn(java.util.Optional.of(existing)).when(repository).findById(1L);
    doReturn(existing).when(repository).save(any(BookEntity.class));

    var actual = serviceUnderTest.update(1L, updateDto);

    verify(repository, times(1)).findById(1L);
    verify(repository, times(1)).save(any(BookEntity.class));

    Assertions.assertEquals("unclebob@example.com", actual.getEmail());
    Assertions.assertEquals("Clean Code", actual.getTitle());
  }

  private BookEntity createBookEntity() {

    return BookEntity.builder()
        .title("Effective Java")
        .author("Joshua Bloch")
        .email("joshua.bloch@example.com")
        .phoneNumber("0712345678")
        .build();
  }
}
