package service;

import java.util.List;
import repository.LibraryRepository;

public class LibraryService {

  private final LibraryRepository libraryRepository;

  public LibraryService() {
    this.libraryRepository = new LibraryRepository();
  }

  public List<String> getAllReadersWithBorrowedBooks(){
    return libraryRepository.findAllReadersWithBorrowedBooks();
  }

  public List<String> getAllBooksWithReaders() {
    return libraryRepository.findAllBooksWithReaders();
  }
}
