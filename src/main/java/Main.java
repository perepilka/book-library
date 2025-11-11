import book.Book;
import book.BookController;
import book.BookRepository;
import book.BookService;
import reader.Reader;
import util.StringUtil;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static boolean exitFlag = false;
    private static BookRepository bookRepository;
    private static BookService bookService;
    private static BookController bookController;
    private static StringUtil stringUtil = new StringUtil();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        List<Book> books = new ArrayList<>(seedBooks());
        bookRepository = new BookRepository(books);
        bookService = new BookService(bookRepository);
        bookController = new BookController(bookService, stringUtil);
        List<Reader> readers = seedReaders();


        System.out.println("WELCOME TO THE LIBRARY!");
        while (!exitFlag) {
            System.out.println(
                    """
                            PLEASE, SELECT ONE OF THE FOLLOWING ACTIONS BY TYPING THE OPTION’S NUMBER AND PRESSING ENTER KEY:
                            [1] SHOW ALL BOOKS IN THE LIBRARY
                            [2] SHOW ALL READERS REGISTERED IN THE LIBRARY
                            [4] Please enter new book name and author separated by “/”. Like this: name / author
                            TYPE “EXIT” TO STOP THE PROGRAM AND EXIT!
                            """);
            System.out.println(
                    switch (scanner.nextLine()) {
                        case "1" -> bookController.getAllBooks();
                        case "2" -> readers;
                        case "4" -> {
                            try {
                                yield bookController.createBook(scanner.nextLine());
                            }catch (InvalidNameException e){
                                yield e.getMessage();
                            }
                        }
                        case "EXIT" ->
                        {
                            exitFlag = true;
                            yield "Goodbye!";
                        }
                        default -> "WRONG INPUT!";
                    }
            );
        }
        scanner.close();
    }

    private static List<Book> seedBooks() {
        return List.of(
                new Book(0L, "A Tale of Two Cities", "Charles Dickens"),
                new Book(1L, "The Little Prince", "The Little Prince"),
                new Book(2L, "The Alchemist", "Paulo Coelho")
        );
    }

    private static List<Reader> seedReaders() {
        return List.of(
                new Reader(0L, "Lena Cano"),
                new Reader(1L, "Terry Xiong"),
                new Reader(2L, "Amayah Burgess")
        );
    }

}
