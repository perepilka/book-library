package service;

import java.util.List;
import java.util.Map;
import model.Book;
import model.Reader;
import repository.LibraryRepository;

public class LibraryService {

  private final LibraryRepository libraryRepository;

  public LibraryService() {
    this.libraryRepository = new LibraryRepository();
  }

  public Map<Reader, List<Book>> getAllReadersWithBorrowedBooks() {
    return libraryRepository.findAllReadersWithBorrowedBooks();
  }

  public Map<Book, String> getAllBooksWithReaders() {
    return libraryRepository.findAllBooksWithReaders();
  }
}
