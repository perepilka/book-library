package repository;

import exception.ObjectNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import util.DatabasePool;

public class BookRepository {

  private List<Book> books = new ArrayList<>();

  public List<Book> findAll() {
    try (Connection conn = DatabasePool.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books");
        ResultSet rs = stmt.executeQuery();) {

      List<Book> books = new ArrayList<>();
      while (rs.next()) {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String author = rs.getString("author");
        Long readerId = rs.getLong("reader_id");

        books.add(new Book(id, name, author, readerId));
      }
      return books;
    } catch (SQLException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  public Optional<Book> findById(Long id) {
    return books.stream().filter(book -> book.getId().equals(id)).findFirst();
  }

  public void save() {
//    return book;
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


}
