package app;

import exception.LibraryException;
import exception.ObjectNotFoundException;
import model.Book;
import model.Reader;
import service.BookService;
import service.ReaderService;

import java.util.List;
import java.util.Scanner;

public class App {

    private ReaderService readerService = new ReaderService();
    private BookService bookService = new BookService(readerService);
    private Scanner scanner = new Scanner(System.in);

    boolean exitFlag = false;


    public void run() {
        System.out.println("WELCOME TO THE LIBRARY!");
        menu();
        scanner.close();
    }

    private void menu() {
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

            switch (scanner.nextLine().trim().toUpperCase()) {
                case "1" -> getAllBooks();
                case "2" -> getAllReaders();
                case "3" -> registerNewReader();
                case "4" -> addNewBook();
                case "5" -> borrowBook();
                case "6" -> returnBook();
                case "7" -> getBorrowedBooks();
                case "8" -> getBookReader();
                case "EXIT" -> exit();
                default -> System.out.println("WRONG INPUT!");
            }
        }
    }

    private void getAllBooks() {
        System.out.println(bookService.findAll());
    }

    private void getAllReaders() {
        System.out.println(readerService.findAll());
    }

    private void registerNewReader() {
        System.out.println("Please enter new reader full name!");
        try {
            System.out.println(readerService.save(scanner.nextLine()));
        } catch (LibraryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addNewBook() {
        System.out.println("Please enter new book name and author separated by “/”. Like this: name / author");
        try {
            System.out.println(bookService.save(scanner.nextLine()));
        } catch (LibraryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void borrowBook() {
        System.out.println("Please enter book id and reader id separated by “/”. Like this: BookId/ReaderId");
        try {
            System.out.println(bookService.borrowBook(scanner.nextLine()));
        } catch (LibraryException | ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void returnBook() {
        System.out.println("Please enter book id!");
        try {
            System.out.println(bookService.returnBook(scanner.nextLine()));
        } catch (LibraryException | ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getBorrowedBooks() {
        System.out.println("Please enter reader id!");
        try {
            List<Book> books = bookService.getBooksBorrowedBy(scanner.nextLine());
            if (books.isEmpty()) {
                System.out.println("This reader has not borrowed books");
            } else System.out.println(books);
        } catch (LibraryException | ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getBookReader() {
        System.out.println("Please enter book id!");
        try {
            Reader reader = bookService.getReaderByBookId(scanner.nextLine());
            System.out.println(reader);
        } catch (ObjectNotFoundException | LibraryException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void exit() {
        exitFlag = true;
        System.out.println("Goodbye!");
    }

}
