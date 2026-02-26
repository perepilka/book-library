package repository;

import exception.LibraryException;
import exception.ObjectNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import util.DatabaseConnection;

public class BookRepository {

  public List<Book> findAll() {
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM books")) {
      ResultSet rs = stmt.executeQuery();
      List<Book> books = new ArrayList<>();
      while (rs.next()) {
        books.add(mapToBook(rs));
      }
      return books;
    } catch (SQLException e) {
      throw new LibraryException("Error fetching books from database: " + e.getMessage());
    }
  }

  public Optional<Book> findById(Long id) {
    String sql = "SELECT * FROM books where id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            sql)) {

      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Book book = new Book(mapToBook(rs));
          return Optional.of(book);
        }
      }
    } catch (SQLException e) {
      throw new LibraryException("Error finding book by ID: " + e.getMessage());
    }
    return Optional.empty();
  }

  public Book save(Book book) {
    String sql = "insert into books (title, fullname) values (?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, book.getTitle());
      stmt.setString(2, book.getAuthor());
      stmt.executeUpdate();
      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          book.setId(rs.getLong(1));
        }
      }
    } catch (SQLException e) {
      throw new LibraryException("Book was not saved, caused by sql problem: " + e.getMessage());
    }
    return book;
  }

  public Book updateBook(Book bookToUpdate) {
    String sql = "update books set title = ?, fullname = ?, reader_id = ? where id = ?";
    var id = bookToUpdate.getId();

    findById(id).orElseThrow(() -> new ObjectNotFoundException("Book not found, id: " + id));

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            sql)) {
      stmt.setString(1, bookToUpdate.getTitle());
      stmt.setString(2, bookToUpdate.getAuthor());
      if (bookToUpdate.getReaderId() != null) {
        stmt.setLong(3, bookToUpdate.getReaderId());
      } else {
        stmt.setNull(3, java.sql.Types.BIGINT);
      }
      stmt.setLong(4, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new LibraryException("Book was not updated, caused by sql problem: " + e.getMessage());
    }
    return bookToUpdate;
  }

  private Book mapToBook(ResultSet rs) throws SQLException {
    Long id = rs.getLong("id");
    String title = rs.getString("title");
    String author = rs.getString("fullname");
    Long readerId = (Long) rs.getObject("reader_id");

    return new Book(id, title, author, readerId);
  }
}
