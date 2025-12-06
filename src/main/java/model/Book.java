package model;

public class Book {

  private Long id;
  private String name;
  private String author;
  private Long readerId;

  public Book(String name, String author) {
    id = null;
    this.name = name;
    this.author = author;
    readerId = null;
  }

  public Book(Long id, String name, String author, Long readerId) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.readerId = readerId;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
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

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setReaderId(Long readerId) {
    this.readerId = readerId;
  }

  @Override
  public String toString() {
    return id + ". \"" + name + "\" - " + author + ". Borrowed by: " + readerId;
  }
}