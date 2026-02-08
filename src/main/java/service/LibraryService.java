package service;

import java.util.ArrayList;
import java.util.List;
import model.Reader;
import repository.BookRepository;

public class LibraryService {

  private final BookService bookService;
  private final ReaderService readerService;

  public LibraryService(ReaderService readerService, BookService bookService) {
    this.readerService = readerService;
    this.bookService = bookService;
  }

  public List<String> getAllReadersWithBorrowedBooks(){
    List<Reader> readers = readerService.findAll();
    List<String> result = new ArrayList<>();
    for (Reader reader : readers) {

    }
  }
}
