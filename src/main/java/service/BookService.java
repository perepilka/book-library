package service;

import exception.BookNotFoundException;
import model.Book;
import repository.BookRepository;
import util.StringUtil;

import java.util.List;
import java.util.Optional;

public class BookService {

    private final BookRepository bookRepository;
    private ReaderService readerService;

    public BookService(ReaderService readerService) {
        this.bookRepository = new BookRepository();
        this.readerService = readerService;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else throw new BookNotFoundException("Book not found, id: " + id);
    }

    public Book save(String unformatted) {
        String[] str = StringUtil.splitString(unformatted);
        StringUtil.checkTitle(str[0]);
        StringUtil.checkName(str[1]);
        return bookRepository.save(new Book(null, str[0], str[1]));
    }

    public Book borrowBook(String unformatted) {

        String[] str = StringUtil.splitString(unformatted);

        StringUtil.checkId(str[0]);
        StringUtil.checkId(str[1]);

        var readerId = Long.parseLong(str[1]);

        Book book = findById(Long.parseLong(str[0]));

        if (book.getReaderId() != null) throw new RuntimeException("This book is already borrowed!");

        readerService.findById(readerId);

        book.setReaderId(readerId);
        return bookRepository.updateBook(book);
    }

    public Book returnBook(String string) {
        StringUtil.checkId(string);

        Book book = findById(Long.parseLong(string));
        book.setReaderId(null);
        return bookRepository.updateBook(book);
    }

    public List<Book> getBooksBorrowedBy(String string) {
        StringUtil.checkId(string);
        Long readerId = Long.parseLong(string);
        readerService.findById(readerId);

        return findAll().stream()
                .filter(book -> readerId.equals(book.getReaderId()))
                .toList();
    }

    public Long getReaderId(String string) {
        StringUtil.checkId(string);
        var readerId = findById(Long.parseLong(string)).getReaderId();
        if (readerId == null) throw new RuntimeException("Book is not borrowed!");

        return readerId;
    }
}
