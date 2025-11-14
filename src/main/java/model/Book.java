package model;

public class Book {

    private Long id;
    private String name;
    private String author;
    private Long readerId;

    public Book(Long id, String name, String author) {
        this.id = id;
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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ". \"" + name + "\" - " + author + ". Borrowed by: " + readerId;
    }
}