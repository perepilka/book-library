package repository;

import exception.LibraryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Book;
import model.Reader;
import util.DatabaseConnection;

public class LibraryRepository {

  public Map<Reader, List<Book>> findAllReadersWithBorrowedBooks() {
    String sql =
        """
            SELECT
                r.id        AS r_id,
                r.fullname  AS r_name,
                b.id        AS b_id,
                b.title,
                b.fullname  AS b_author
            FROM readers r
                     LEFT JOIN books b ON r.id = b.reader_id
            ORDER BY r.id
            """;
    Map<Reader, List<Book>> readerBooksMap = new LinkedHashMap<>();

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        long readerId = rs.getLong("r_id");
        String readerName = rs.getString("r_name");
        Reader reader = new Reader(readerId, readerName);

        readerBooksMap.putIfAbsent(reader, new ArrayList<>());

        long bookId = rs.getLong("b_id");
        if (!rs.wasNull()) {
          String bookTitle = rs.getString("title");
          String bookAuthor = rs.getString("b_author");
          Book book = new Book(bookId, bookTitle, bookAuthor, readerId);
          readerBooksMap.get(reader).add(book);
        }
      }
    } catch (SQLException e) {
      throw new LibraryException("Error fetching readers with books: " + e.getMessage());
    }

    return readerBooksMap;
  }

  public Map<Book, String> findAllBooksWithReaders() {
    String sql =
        """
            SELECT
                b.id AS b_id,
                b.title,
                b.fullname AS b_author,
                b.reader_id,
                r.fullname AS r_name
            FROM books b
                LEFT JOIN readers r ON b.reader_id = r.id
            ORDER BY b.id
            """;
    Map<Book, String> booksWithReaderId = new LinkedHashMap<>();

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        long bookId = rs.getLong("b_id");
        String title = rs.getString("title");
        String author = rs.getString("b_author");
        long readerId = rs.getLong("reader_id");
        String readerName = rs.getString("r_name");

        Book book = new Book(bookId, title, author, readerId);

        booksWithReaderId.put(book, readerName);
      }
    } catch (SQLException e) {
      throw new LibraryException("Error fetching books with readers: " + e.getMessage());
    }
    return booksWithReaderId;
  }


}
