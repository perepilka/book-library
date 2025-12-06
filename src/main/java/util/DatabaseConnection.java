package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private static final String DB_NAME = System.getenv("DB_NAME");
  private static final String DB_USER = System.getenv("DB_USER");
  private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
  private static final String DB_HOST = System.getenv("DB_HOST");
  private static final String DB_PORT = System.getenv("DB_PORT");

  private static final String URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
  }



}
