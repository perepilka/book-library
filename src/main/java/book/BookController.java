package book;

import util.StringUtil;

import javax.naming.InvalidNameException;
import java.util.List;

public class BookController {

    private BookService bookService;
    private StringUtil stringUtil;

    public BookController(BookService bookService, StringUtil stringUtil) {
        this.bookService = bookService;
        this.stringUtil = stringUtil;
    }

    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    public Book createBook(String unformatted) throws InvalidNameException {
        String[] str = stringUtil.splitString(unformatted);
        stringUtil.checkTitle(str[0]);
        stringUtil.checkName(str[1]);
        return bookService.save(str[0], str[1]);
    }

    public Book borrowBook(String unformatted) throws InvalidNameException {
        String[] str = stringUtil.splitString(unformatted);
        return bookService.borrowBook(Long.parseLong(str[0]), Long.parseLong(str[1]));
    }

    public Book returnBook(Long bookId) {
        return bookService.returnBook(bookId);
    }

    public List<Book> getBorrowedBooksByReader(Long readerId) {
        return bookService.getBooksBorrowedBy(readerId);
    }

    public Long getReaderId(Long bookId) {
        return bookService.getReaderId(bookId);
    }
}
