package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import exception.LibraryException;
import java.util.List;
import java.util.Optional;
import model.Book;
import model.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import repository.BookRepository;

class BookServiceTest {

  @Mock
  private BookRepository bookRepository;
  @Mock
  private ReaderService readerService;
  @InjectMocks
  private BookService bookService;

  @Test
  void whenFindAll_thenReturnsListOfBooks() {
    //Arrange
    List<Book> expectedBooks = List.of(
        new Book(1L, "A Tale of Two Cities", "Charles Dickens", null),
        new Book(2L, "The Little Prince", "Charles Dickens", 1L)
    );
    when(bookRepository.findAll()).thenReturn(expectedBooks);

    //Act
    List<Book> actualBooks = bookService.findAll();

    //Assert
    assertEquals(expectedBooks, actualBooks);
    assertEquals(2, actualBooks.size());
  }

  @Test
  void whenFindById_thenReturnsOptionalBook() {
    //Arrange
    Optional<Book> expectedBook = Optional.of(new Book(1L, "A Tale of Two Cities", "Charles Dickens", null));
    when(bookRepository.findById(1L)).thenReturn(expectedBook);

    //Act
    Optional<Book> actualBook = bookService.findById(1L);

    //Assert
    assertEquals(expectedBook, actualBook);
  }

  @Test
  void givenValidTitleAndName_whenSave_thenReturnsSavedBook() {
    //Arrange
    Book expectedBook = new Book(1L, "A Tale of Two Cities", "Charles Dickens", null);
    when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);

    //Act
    Book actualBook = bookService.save("A Tale of Two Cities/Charles Dickens");

    //Assert
    assertEquals(expectedBook, actualBook);
  }

  @Test
  void givenEmptyTitle_whenSave_thenThrowsException() {
    LibraryException exception = assertThrows(LibraryException.class,
        () -> bookService.save("/Charles Dickens"));
    assertEquals("Title is empty", exception.getMessage());
  }

  @Test
  void givenEmptyName_whenSave_thenThrowsException() {
    LibraryException exception = assertThrows(LibraryException.class,
        () -> bookService.save("/Charles Dickens"));
    assertEquals("Name is empty", exception.getMessage());
  }

  @Test
  void borrowBook() {

  }

  @Test
  void returnBook() {
  }

  @Test
  void getBooksBorrowedBy() {
  }

  @Test
  void getReaderIdByBookId() {
  }

  @Test
  void getReaderByBookId() {
  }
}