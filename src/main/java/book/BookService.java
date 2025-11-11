package book;

import java.util.List;

public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(String name, String author) {
        Long id = (long) findAll().size();
        Book book = new Book(id, name, author);
        bookRepository.save(book);
        return book;
    }
}
