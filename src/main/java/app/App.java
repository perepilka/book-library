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

  private boolean exitFlag = false;


  public void run() {
    System.out.println("WELCOME TO THE LIBRARY!");
    menu();
    scanner.close();
  }

  private void menu() {
    while (!exitFlag) {
      System.out.println("""
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
        case "1" -> printAllBooks();
        case "2" -> printAllReaders();
        case "3" -> registerNewReader();
        case "4" -> addNewBook();
        case "5" -> borrowBook();
        case "6" -> returnBook();
        case "7" -> printBorrowedBooks();
        case "8" -> printBookReader();
        case "EXIT" -> exit();
        default -> System.out.println("WRONG INPUT!");
      }
    }
  }

  private void printAllBooks() {
    System.out.println(bookService.findAll());
  }

  private void printAllReaders() {
    System.out.println(readerService.findAll());
  }

  private void registerNewReader() {
    System.out.println("Please enter new reader full name!");
    try {
      var createdReader = readerService.save(scanner.nextLine());
      System.out.println(createdReader);
    } catch (LibraryException e) {
      System.out.println(e.getMessage());
    }
  }

  private void addNewBook() {
    System.out.println(
        "Please enter new book name and author separated by “/”. Like this: name / author");
    try {
      var createdBook = bookService.save(scanner.nextLine());
      System.out.println(createdBook);
    } catch (LibraryException e) {
      System.out.println(e.getMessage());
    }
  }

  private void borrowBook() {
    System.out.println(
        "Please enter book id and reader id separated by “/”. Like this: BookId/ReaderId");
    try {
      var borrowedBook = bookService.borrowBook(scanner.nextLine());
      System.out.println(borrowedBook);
    } catch (LibraryException | ObjectNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  private void returnBook() {
    System.out.println("Please enter book id!");
    try {
      var returnedBook = bookService.returnBook(scanner.nextLine());
      System.out.println(returnedBook);
    } catch (LibraryException | ObjectNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  private void printBorrowedBooks() {
    System.out.println("Please enter reader id!");
    try {
      List<Book> books = bookService.getBooksBorrowedBy(scanner.nextLine());
      if (books.isEmpty()) {
        System.out.println("This reader has not borrowed books");
      } else {
        System.out.println(books);
      }
    } catch (LibraryException | ObjectNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  private void printBookReader() {
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
