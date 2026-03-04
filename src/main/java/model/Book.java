package model;

import java.util.Objects;

public class Book {

  private Long id;
  private final String title;
  private final String author;
  private Long readerId;

  public Book(String title, String author) {
    id = null;
    this.title = title;
    this.author = author;
    readerId = null;
  }

  public Book(Long id, String title, String author, Long readerId) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.readerId = readerId;
  }

  public Book(Book book) {
    this.id = book.id;
    this.title = book.title;
    this.author = book.author;
    this.readerId = book.readerId;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public Long getReaderId() {
    return readerId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setReaderId(Long readerId) {
    this.readerId = readerId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(id, book.id) && Objects.equals(title, book.title)
        && Objects.equals(author, book.author) && Objects.equals(readerId,
        book.readerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, readerId);
  }

  @Override
  public String toString() {
    return id + ". \"" + title + "\" - " + author + ". Borrowed by: " + readerId;
  }
}