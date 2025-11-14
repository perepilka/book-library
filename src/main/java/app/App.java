package app;

import exception.BookNotFoundException;
import exception.InvalidStringException;
import exception.ReaderNotFoundException;
import model.Book;
import model.Reader;
import service.BookService;
import service.ReaderService;

import java.util.List;
import java.util.Scanner;

public class App {

    private static ReaderService readerService;
    private static BookService bookService;
    private static Scanner scanner;


    public App() {
        readerService = new ReaderService();
        bookService = new BookService(readerService);
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("WELCOME TO THE LIBRARY!");

        menu();

        scanner.close();
    }

    private void menu() {
        boolean exitFlag = false;
        while (!exitFlag) {
            System.out.println(
                    """
                            PLEASE, SELECT ONE OF THE FOLLOWING ACTIONS BY TYPING THE OPTION’S NUMBER AND PRESSING ENTER KEY:
                            [1] SHOW ALL BOOKS IN THE LIBRARY
                            [2] SHOW ALL READERS REGISTERED IN THE LIBRARY
                            [3] REGISTER NEW READER
                            [4] ADD NEW BOOK
                            [5] BORROW A BOOK TO A READER
                            [6] RETURN A BOOK TO THE LIBRARY
                            [7] SHOW ALL BORROWED BOOK BY USER ID
                            [8] SHOW CURRENT READER OF A BOOK WITH ID
                            TYPE “EXIT” TO STOP THE PROGRAM AND EXIT!""");

            System.out.println(

                    switch (scanner.nextLine()) {
                        case "1" -> getAllBooks();
                        case "2" -> getAllReaders();
                        case "3" -> {
                            try {
                                yield registerNewReader();
                            } catch (InvalidStringException e) {
                                yield e.getMessage();
                            }
                        }
                        case "4" -> {
                            try {
                                yield addNewBook();
                            } catch (InvalidStringException e) {
                                yield e.getMessage();
                            }
                        }
                        case "5" -> {
                            try {
                                yield borrowBook();
                            } catch (RuntimeException e) {
                                yield e.getMessage();
                            }
                        }
                        case "6" -> {
                            try {
                                yield returnBook();
                            } catch (RuntimeException e) {
                                yield e.getMessage();
                            }
                        }
                        case "7" -> {
                            try {
                                List<Book> books = getBorrowedBooks();
                                if (books.isEmpty()) {
                                    yield "This reader has not borrowed books";
                                } else yield books;
                            } catch (RuntimeException e) {
                                yield e.getMessage();
                            }

                        }
                        case "8" -> {
                            try {
                                yield getBookReader();
                            } catch (RuntimeException e) {
                                yield e.getMessage();
                            }
                        }
                        case "EXIT" -> {
                            exitFlag = true;
                            yield "Goodbye!";
                        }
                        default -> "WRONG INPUT!";
                    }
            );
        }
    }

    private List<Book> getAllBooks() {
        return bookService.findAll();
    }

    private List<Reader> getAllReaders() {
        return readerService.findAll();
    }

    private Reader registerNewReader() {
        System.out.println("Please enter new reader full name!");

        return readerService.save(scanner.nextLine());
    }

    private Book addNewBook() {
        System.out.println("Please enter new book name and author separated by “/”. Like this: name / author");

        return bookService.save(scanner.nextLine());
    }

    private Book borrowBook() {
        System.out.println("Please enter book id and reader id separated by “/”. Like this: BookId/ReaderId");

        return bookService.borrowBook(scanner.nextLine());
    }

    private Book returnBook() {
        System.out.println("Please enter book id!");

        return bookService.returnBook(scanner.nextLine());
    }

    private List<Book> getBorrowedBooks() {
        System.out.println("Please enter reader id!");

        return bookService.getBooksBorrowedBy(scanner.nextLine());
    }

    private Reader getBookReader() {
        System.out.println("Please enter book id!");

        Long readerId = bookService.getReaderId(scanner.nextLine());

        return readerService.findById(readerId);
    }
}
