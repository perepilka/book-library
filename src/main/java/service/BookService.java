package service;

import exception.LibraryException;
import exception.ObjectNotFoundException;
import model.Book;
import model.Reader;
import repository.BookRepository;
import util.StringUtil;

import java.util.List;
import java.util.Optional;

public class BookService {

  private final BookRepository bookRepository;
  private ReaderService readerService;

  public BookService(ReaderService readerService) {
    this.bookRepository = new BookRepository();
    this.readerService = readerService;
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(Long id) {
    return bookRepository.findById(id);
  }

  public Book save(String unformatted) {
    String[] str = StringUtil.splitString(unformatted);
    StringUtil.checkTitle(str[0]);
    StringUtil.checkName(str[1]);
    return bookRepository.save(new Book(str[0], str[1]));
  }

  public Book borrowBook(String unformatted) {

    String[] str = StringUtil.splitString(unformatted);

    StringUtil.checkId(str[0]);
    StringUtil.checkId(str[1]);

    var readerId = Long.parseLong(str[1]);

    Book book = findById(Long.parseLong(str[0])).orElseThrow(
        () -> new ObjectNotFoundException("Book not found, id: " + str[0]));

    if (book.getReaderId() != null) {
      throw new LibraryException("This book is already borrowed!");
    }

    readerService.findById(readerId)
        .orElseThrow(() -> new ObjectNotFoundException("Reader not found, id: " + readerId));

    book.setReaderId(readerId);
    return bookRepository.updateBook(book)
        .orElseThrow(() -> new ObjectNotFoundException("Book not found, id: " + book.getId()));
  }

  public Book returnBook(String string) {
    StringUtil.checkId(string);

    Book book = findById(Long.parseLong(string)).orElseThrow(
        () -> new ObjectNotFoundException("Book not found, id: " + string));
    book.setReaderId(null);
    return bookRepository.updateBook(book)
        .orElseThrow(() -> new ObjectNotFoundException("Book not found, id: " + book.getId()));
  }

  public List<Book> getBooksBorrowedBy(String string) {
    StringUtil.checkId(string);
    Long readerId = Long.parseLong(string);
    readerService.findById(readerId);

    return findAll().stream()
        .filter(book -> readerId.equals(book.getReaderId()))
        .toList();
  }

  public Long getReaderIdByBookId(String string) {
    StringUtil.checkId(string);
    Book book = findById(Long.parseLong(string)).orElseThrow(
        () -> new ObjectNotFoundException("Book not found, id: " + string));
    var readerId = book.getReaderId();
    if (readerId == null) {
      throw new LibraryException("Book is not borrowed!");
    }

    return readerId;
  }

  public Reader getReaderByBookId(String string) {
    Long readerId = getReaderIdByBookId(string);
    return readerService.findById(readerId)
        .orElseThrow(() -> new ObjectNotFoundException("Reader not found, id: " + readerId));
  }
}
