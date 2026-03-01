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
import util.DatabaseConnection;

public class LibraryRepository {

  public List<String> findAllReadersWithBorrowedBooks() {
    String sql =
        "SELECT r.id AS r_id, r.fullname AS r_name, b.id AS b_id, b.title, b.fullname AS b_author "
            + "FROM readers r LEFT JOIN books b ON r.id = b.reader_id ORDER BY r.id";
    Map<String, List<String>> readerBooksMap = new LinkedHashMap<>();

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        long readerId = rs.getLong("r_id");
        String readerName = rs.getString("r_name");
        String readerKey = readerId + ". " + readerName;

        readerBooksMap.putIfAbsent(readerKey, new ArrayList<>());

        long bookId = rs.getLong("b_id");
        if (!rs.wasNull()) {
          String bookStr = bookId + "." + rs.getString("title") + " - " + rs.getString("b_author");
          readerBooksMap.get(readerKey).add(bookStr);
        }
      }
    } catch (SQLException e) {
      throw new LibraryException("Error fetching readers with books: " + e.getMessage());
    }

    List<String> result = new ArrayList<>();
    for (Map.Entry<String, List<String>> entry : readerBooksMap.entrySet()) {
      if (entry.getValue().isEmpty()) {
        result.add(entry.getKey() + " (no books borrowed)");
      } else {
        result.add(entry.getKey() + ":" + String.join(", ", entry.getValue()));
      }
    }
    return result;
  }

  public List<String> findAllBooksWithReaders() {
    String sql =
        "SELECT b.id AS b_id, b.title, b.fullname AS b_author, b.reader_id, r.fullname AS r_name "
            + "FROM books b LEFT JOIN readers r ON b.reader_id = r.id ORDER BY b.id";
    List<String> result = new ArrayList<>();

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        long bookId = rs.getLong("b_id");
        String title = rs.getString("title");
        String author = rs.getString("b_author");
        long readerId = rs.getLong("reader_id");
        boolean isAvailable = rs.wasNull();

        String bookBase =
            bookId + ". \"" + title + "\" - " + author + ". Borrowed by: " + (isAvailable ? "null"
                : readerId);

        if (isAvailable) {
          result.add(bookBase + " (available)");
        } else {
          String readerName = rs.getString("r_name");
          result.add(bookBase + "." + readerName);
        }
      }
    } catch (SQLException e) {
      throw new LibraryException("Error fetching books with readers: " + e.getMessage());
    }
    return result;
  }


}
