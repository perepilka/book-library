package util;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DatabaseInitializer {

  public static void initDatabase() {
    try{
      String sql = getSqlFromFile("schema.sql");
      executeSqlScript(sql);
      sql = getSqlFromFile("data.sql");
      executeSqlScript(sql);
    }catch (Exception e){
      System.err.println(e.getMessage());
    }

  }

  private static String getSqlFromFile(String fileName) throws Exception {
    InputStream inputStream = DatabaseInitializer.class
        .getClassLoader()
        .getResourceAsStream(fileName);

    if (inputStream == null) {
      throw new IllegalArgumentException("File not found: " + fileName);
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      return reader.lines().collect(Collectors.joining("\n"));
    }
  }

  private static void executeSqlScript(String sql) throws SQLException {
    try (Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement()) {

      String[] statements = sql.split(";");

      for (String statement : statements) {
        String trimmed = statement.trim();
        if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
          stmt.execute(trimmed);
        }
      }
    }
  }


}
