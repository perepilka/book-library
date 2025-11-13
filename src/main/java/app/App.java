package app;

import model.Book;
import service.BookService;
import service.ReaderService;
import util.StringUtil;

import javax.naming.InvalidNameException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static ReaderService readerService;
    private static BookService bookService;

    public App() {
        StringUtil stringUtil = new StringUtil();
        readerService = new ReaderService(stringUtil);
        bookService = new BookService(stringUtil);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        try {
            seedReaders(readerService);
            seedBooks(bookService);
        } catch (InvalidNameException e) {
            System.out.println("Exception catch in seeding. Exception message: " + e.getMessage());
        }
        System.out.println("WELCOME TO THE LIBRARY!");

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
                        case "1" -> bookService.findAll();
                        case "2" -> readerService.findAll();
                        case "3" -> {
                            System.out.println("Please enter new reader full name!");
                            try {
                                yield readerService.save(scanner.nextLine());
                            } catch (InvalidNameException e) {
                                yield e.getMessage();
                            }
                        }
                        case "4" -> {
                            System.out.println("Please enter new book name and author separated by “/”. Like this: name / author");
                            try {
                                yield bookService.save(scanner.nextLine());
                            } catch (InvalidNameException e) {
                                yield e.getMessage();
                            }
                        }
                        case "5" -> {
                            System.out.println("Please enter book id and reader id separated by “/”. Like this: BookId/ReaderId");
                            try {
                                yield bookService.borrowBook(scanner.nextLine());
                            } catch (Exception e) {
                                yield e.getMessage();
                            }
                        }
                        case "6" -> {
                            System.out.println("Please enter book id!");
                            try {
                                yield bookService.returnBook(Long.parseLong(scanner.nextLine()));
                            } catch (IndexOutOfBoundsException e) {
                                yield "There is no book with that ID!";
                            }
                        }
                        case "7" -> {
                            System.out.println("Please enter reader id!");
                            List<Book> books = bookService.getBooksBorrowedBy(Long.parseLong(scanner.nextLine()));
                            if  (books.isEmpty()) {
                                yield "This reader has not borrowed books";
                            } else yield books;
                        }
                        case "8" -> {
                            try {
                                System.out.println("Please enter book id!");
                                Long readerId = bookService.getReaderId(Long.parseLong(scanner.nextLine()));
                                if (readerId == null) {
                                    yield "This book is not borrowed by anyone!";
                                }
                                yield readerService.findById(readerId);
                            } catch (IndexOutOfBoundsException e) {
                                yield "There is no book with that ID!";
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
        scanner.close();
    }

    private void seedBooks(BookService bookService) throws InvalidNameException {
        bookService.save("A Tale of Two Cities/Charles Dickens");
        bookService.save("The Little Prince/The Little Prince");
        bookService.save("The Alchemist/Paulo Coelho");
    }

    private void seedReaders(ReaderService readerService) throws InvalidNameException {
        readerService.save("Lena Cano");
        readerService.save("Terry Xiong");
        readerService.save("Amayah Burgess");
    }

}
