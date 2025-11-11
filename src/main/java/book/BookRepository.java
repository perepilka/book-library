package book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    List<Book> books = new ArrayList<>();

    public BookRepository() {
    }

    public BookRepository(List<Book> books) {
        this.books = books;
    }

    public List<Book> findAll() {
        return books;
    }

    public Book save(Book book) {
        books.add(book);
        return book;
    }
}
