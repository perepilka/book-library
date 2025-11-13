package model;

public class Book{

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

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    @Override
    public String toString() {
        return id + ". \"" + name + "\" - " + author + ". Borrowed by: " + readerId;
    }
}