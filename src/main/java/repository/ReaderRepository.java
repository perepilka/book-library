package repository;

import exception.LibraryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import util.DatabaseConnection;

public class ReaderRepository {

  public List<Reader> findAll() {
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM readers")) {
      ResultSet rs = stmt.executeQuery();
      List<Reader> readers = new ArrayList<>();
      while (rs.next()) {
        readers.add(mapToReader(rs));
      }
      return readers;
    } catch (SQLException e) {
      throw new LibraryException("Error fetching readers from database: " + e.getMessage());
    }
  }

  public Optional<Reader> findById(Long id) {
    String sql = "select * from readers where id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Reader reader = new Reader(mapToReader(rs));
          return Optional.of(reader);
        }
      }
    } catch (SQLException e) {
      throw new LibraryException("Error finding reader by ID: " + e.getMessage());
    }
    return Optional.empty();
  }

  public Reader save(Reader reader) {
    String sql = "insert into readers (fullname) values (?)";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql,
            java.sql.Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, reader.getName());
      stmt.executeUpdate();
      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          reader.setId(rs.getLong(1));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Reader was not saved: " + e.getMessage(), e);
    }
    return reader;
  }

  private Reader mapToReader(ResultSet rs) throws SQLException {
    Long id = rs.getLong("id");
    String name = rs.getString("fullname");
    return new Reader(id, name);
  }
}
