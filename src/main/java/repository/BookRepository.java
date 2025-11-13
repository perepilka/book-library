package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private List<Book> books = new ArrayList<>();

    public List<Book> findAll() {
        return books;
    }

    public Book findById(Long id) throws IndexOutOfBoundsException{
        return books.get(Math.toIntExact(id));
    }

    public Book save(Book book) {
        books.add(book);
        return book;
    }

    public Book updateBook(Book book) {
        books.set(books.indexOf(book), book);
        return book;
    }
}
