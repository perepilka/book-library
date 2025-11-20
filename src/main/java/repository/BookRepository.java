package repository;

import exception.ObjectNotFoundException;
import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {

  private List<Book> books = new ArrayList<>();

  public BookRepository() {
    seed();
  }

  public List<Book> findAll() {
    return books;
  }

  public Optional<Book> findById(Long id) {
    return books.stream().filter(book -> book.getId().equals(id)).findFirst();
  }

  public Book save(Book book) {
    books.add(book);
    return book;
  }

  public Book updateBook(Book bookToUpdate) {
    var id = bookToUpdate.getId();
    var existingBook = findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Book not found, id: " + id));
    existingBook.setName(bookToUpdate.getName());
    existingBook.setAuthor(bookToUpdate.getAuthor());
    existingBook.setReaderId(bookToUpdate.getReaderId());
    return existingBook;
  }

  private void seed() {
    save(new Book("A Tale of Two Cities", "Charles Dickens"));
    save(new Book("The Little Prince", "The Little Prince"));
    save(new Book("The Alchemist", "Paulo Coelho"));
  }
}
