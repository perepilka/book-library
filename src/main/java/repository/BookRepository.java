package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    private List<Book> books = new ArrayList<>();
    private long idCounter = 0;

    public BookRepository() {
        seed();
    }

    public List<Book> findAll() {
        return books;
    }

    public Optional<Book> findById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book save(Book book) {
        book.setId(idCounter++);
        books.add(book);
        return book;
    }

    public Book updateBook(Book book) {
        findById(book.getId()).ifPresent(b -> {
            b.setName(book.getName());
            b.setAuthor(book.getAuthor());
            b.setReaderId(book.getReaderId());
        });
        return book;
    }

    private void seed() {
        save(new Book(null, "A Tale of Two Cities", "Charles Dickens"));
        save(new Book(null, "The Little Prince", "The Little Prince"));
        save(new Book(null, "The Alchemist", "Paulo Coelho"));
    }
}
