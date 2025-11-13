package service;

import model.Book;
import repository.BookRepository;
import util.StringUtil;

import javax.naming.InvalidNameException;
import java.util.List;

public class BookService {

    private BookRepository bookRepository;
    private StringUtil stringUtil;

    public BookService(StringUtil stringUtil) {
        this.bookRepository = new BookRepository();
        this.stringUtil = stringUtil;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(String unformatted) throws InvalidNameException {
        String[] str = stringUtil.splitString(unformatted);
        stringUtil.checkTitle(str[0]);
        stringUtil.checkName(str[1]);
        Long id = (long) findAll().size();
        Book book = new Book(id, str[0], str[1]);
        return bookRepository.save(book);
    }

    public Book borrowBook(String unformatted) throws InvalidNameException {
        String[] str = stringUtil.splitString(unformatted);
        Book book = findById(Long.parseLong(str[0]));
        book.setReaderId(Long.parseLong(str[1]));
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
