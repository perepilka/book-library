package book;

import util.StringUtil;

import javax.naming.InvalidNameException;
import java.util.HashMap;
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
        HashMap<String, String> bookInfo = stringUtil.splitBookString(unformatted);
        stringUtil.checkTitle(bookInfo.get("title"));
        stringUtil.checkName(bookInfo.get("author"));
        return bookService.save(bookInfo.get("title"), bookInfo.get("author"));
    }


}
