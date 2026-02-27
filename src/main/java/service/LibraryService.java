package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Book;
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
    List<Book> books = bookService.findAll();

    List<String> result = new ArrayList<>();
    for (Reader reader : readers) {
      var borrowedBooks = books.stream()
          .filter(book -> reader.getId().equals(book.getReaderId()))
          .toList();
      if (borrowedBooks.isEmpty()) {
        result.add(reader.toString() + " (no books borrowed)");
      } else {
        String bookString = borrowedBooks.stream()
            .map(book -> book.getId() + "." + book.getTitle() + " - " + book.getAuthor())
            .collect(Collectors.joining(", "));
        result.add(reader.toString() + ":" + bookString);
      }
    }
    return result;
  }

  public List<String> getAllBooksWithReaders() {
    List<Book> books = bookService.findAll();
    List<Reader> readers = readerService.findAll();
    Map<Long, String> readerNames = readers.stream()
        .collect(Collectors.toMap(Reader::getId, Reader::getName));

    List<String> result = new ArrayList<>();
    for (Book book : books) {
      var readerId = book.getReaderId();
      if(readerId == null){
        result.add(book.toString() + " (available)");
      }  else {
        var readerName = readerNames.getOrDefault(readerId, "Unknown");
        result.add(book.toString() + "." + readerName);
      }
    }
    return result;
  }
}
