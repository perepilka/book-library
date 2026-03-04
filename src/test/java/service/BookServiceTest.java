package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import exception.LibraryException;
import exception.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import model.Book;
import model.Reader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.BookRepository;

@ExtendWith(MockitoExtension.class)
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
    Optional<Book> expectedBook = Optional.of(
        new Book(1L, "A Tale of Two Cities", "Charles Dickens", null));
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
        () -> bookService.save("A Tale of Two Cities/"));
    assertEquals("Input must contain both a title and an author, separated by '/'.",
        exception.getMessage());
  }

  @Test
  void givenEmptyName_whenSave_thenThrowsException() {
    LibraryException exception = assertThrows(LibraryException.class,
        () -> bookService.save("/Charles Dickens"));
    assertEquals("Input must contain both a title and an author, separated by '/'.",
        exception.getMessage());
  }

  @Test
  void givenValidString_whenBorrowBook_thenReturnsBook() {
    //Arrange
    Book availableBook = new Book(1L, "1984", "George Orwell", null);
    Book expectedBook = new Book(1L, "1984", "George Orwell", 1L);
    Reader reader = new Reader(1L, "George Orwell");
    when(bookRepository.findById(1L)).thenReturn(Optional.of(availableBook));
    when(readerService.findById(1L)).thenReturn(Optional.of(reader));
    when(bookRepository.updateBook(any(Book.class))).thenReturn(expectedBook);

    //Act
    Book actualBook = bookService.borrowBook("1/1");

    //Assert
    assertEquals(expectedBook, actualBook);
  }

  @Test
  void givenInvalidString_whenBorrowBook_thenThrowsException() {
    LibraryException exception = assertThrows(LibraryException.class,
        () -> bookService.borrowBook("1/1*"));
    assertEquals("Id must be a valid positive integer!", exception.getMessage());
  }

  @Test
  void givenBookIdThatNotExists_whenBorrowBook_thenThrowsException() {
    //Arrange
    when(bookRepository.findById(1L)).thenReturn(Optional.empty());

    //Act & Assert
    ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
        () -> bookService.borrowBook("1/1"));
    assertEquals("Book not found, id: 1", exception.getMessage());
  }

  @Test
  void givenBookIsAlreadyBorrowed_whenBorrowBook_thenThrowsException() {
    //Arrange
    Book borrowedBook = new Book(1L, "1984", "George Orwell", 1L);
    when(bookRepository.findById(1L)).thenReturn(Optional.of(borrowedBook));

    //Act & Assert
    LibraryException exception = assertThrows(LibraryException.class,
        () -> bookService.borrowBook("1/1"));
    assertEquals("This book is already borrowed!", exception.getMessage());
  }

  @Test
  void givenReaderIsNotFound_whenBorrowBook_thenThrowsException() {
    //Arrange
    Book availableBook = new Book(1L, "1984", "George Orwell", null);
    when(bookRepository.findById(1L)).thenReturn(Optional.of(availableBook));
    when(readerService.findById(1L)).thenReturn(Optional.empty());

    //Act & Assert
    ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
        () -> bookService.borrowBook("1/1"));
    assertEquals("Reader not found, id: 1", exception.getMessage());
  }

  @Test
  void givenValidBookId_whenReturnBook_thenReturnsBook() {
    //Arrange
    Book book = new Book(1L, "1984", "George Orwell", 1L);
    Book expectedBook = new Book(1L, "1984", "George Orwell", null);
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    when(bookRepository.updateBook(any(Book.class))).thenReturn(expectedBook);

    //Act
    Book actualBook = bookService.returnBook("1");

    //Assert
    assertEquals(expectedBook, actualBook);
  }

  @Test
  void givenInvalidBookId_whenReturnBook_thenThrowsException() {
    LibraryException exception = assertThrows(LibraryException.class,
        () -> bookService.returnBook("1*"));
    assertEquals("Id must be a valid positive integer!", exception.getMessage());
  }

  @Test
  void givenBookIdThatNotExists_whenReturnBook_thenThrowsException() {
    //Arrange
    when(bookRepository.findById(1L)).thenReturn(Optional.empty());

    //Act & Assert
    ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
        () -> bookService.returnBook("1"));
    assertEquals("Book not found, id: 1", exception.getMessage());
  }

  @Test
  void givenValidReaderId_whenGetBooksBorrowedBy_thenReturnsBooks() {
    //Arrange
    List<Book> books = List.of(
        new Book(1L, "1984", "George Orwell", null),
        new Book(2L, "Dance of the Moon Fairies", "Rostislav Long", 1L),
        new Book(3L, "Legend of the Blue Bird", "Igor Clark", 1L),
        new Book(4L, "Garden of Stone Flowers", "Alina Edwards", null),
        new Book(5L, "The Last Prophecy", "Larissa Ramirez", null),
        new Book(6L, "Master of the Storm", "Angelina Patterson", 1L)
        );
    List<Book> expectedBooks = List.of(
        new Book(2L, "Dance of the Moon Fairies", "Rostislav Long", 1L),
        new Book(3L, "Legend of the Blue Bird", "Igor Clark", 1L),
        new Book(6L, "Master of the Storm", "Angelina Patterson", 1L)
    );
    when(readerService.findById(1L)).thenReturn(Optional.of(new Reader(1L, "Rostislav Long")));
    when(bookService.findAll()).thenReturn(books);

    //Act
    List<Book> actualBooks = bookService.getBooksBorrowedBy("1");

    //Assert
    assertEquals(expectedBooks, actualBooks);
  }

  @Test
  void givenInvalidReaderId_whenGetBooksBorrowedBy_thenThrowsException() {
    LibraryException exception =  assertThrows(LibraryException.class,
        () -> bookService.getBooksBorrowedBy("1*"));
    assertEquals("Id must be a valid positive integer!", exception.getMessage());
  }

  @Test
  void givenReaderIdThatNotExists_whenGetBooksBorrowedBy_thenThrowsException() {
    //Arrange
    when(readerService.findById(1L)).thenReturn(Optional.empty());

    //Act & Assert
    ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
        () -> bookService.getBooksBorrowedBy("1"));
    assertEquals("Reader not found, id: 1", exception.getMessage());
  }

  @Test
  void getReaderIdByBookId() {
  }

  @Test
  void getReaderByBookId() {
  }
}