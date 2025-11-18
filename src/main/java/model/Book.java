package model;

public class Book {

    private final Long id;
    private String name;
    private String author;
    private Long readerId;
    private static long count = 0;

    public Book(String name, String author) {
        this.id = count++;
        this.name = name;
        this.author = author;
        this.readerId = null;
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