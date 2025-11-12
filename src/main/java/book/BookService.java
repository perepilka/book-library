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

    public Book findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(String name, String author) {
        Long id = (long) findAll().size();
        Book book = new Book(id, name, author);
        bookRepository.save(book);
        return book;
    }

    public Book borrowBook(Long bookId, Long readerId) {
        Book book = findById(bookId);
        book.setReaderId(readerId);
        return bookRepository.updateBook(book);
    }

    public Book returnBook(Long bookId) {
        Book book = findById(bookId);
        book.setReaderId(null);
        return bookRepository.updateBook(book);
    }

    public List<Book> getBooksBorrowedBy(Long readerId) {
        return findAll().stream()
                .filter(book -> readerId.equals(book.getReaderId()))
                .toList();
    }

    public Long getReaderId(Long bookId) {
        return bookRepository.findById(bookId).getReaderId();
    }
}
