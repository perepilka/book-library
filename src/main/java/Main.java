import book.Book;
import reader.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static boolean exitFlag = false;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Book> books = seedBooks();
        List<Reader> readers = seedReaders();

        System.out.println("WELCOME TO THE LIBRARY!");
        while (!exitFlag) {
            System.out.println("PLEASE, SELECT ONE OF THE FOLLOWING ACTIONS BY TYPING THE OPTION’S NUMBER AND PRESSING ENTER KEY:");
            System.out.println("[1] SHOW ALL BOOKS IN THE LIBRARY\n" +
                    "[2] SHOW ALL READERS REGISTERED IN THE LIBRARY\n" +
                    "TYPE “EXIT” TO STOP THE PROGRAM AND EXIT!");
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println(books);
                    break;
                case "2":
                    System.out.println(readers);
                    break;
                case "EXIT":
                    System.out.println("EXIT!");
                    exitFlag = true;
                    break;
                default:
                    System.out.println("WRONG INPUT!");
                    break;
            }
        }
    }

    private static List<Book> seedBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "A Tale of Two Cities", "Charles Dickens"));
        books.add(new Book(2L, "The Little Prince", "The Little Prince"));
        books.add(new Book(3L, "The Alchemist", "Paulo Coelho"));
        return books;
    }
    private static List<Reader> seedReaders() {
        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader(1L, "Lena Cano"));
        readers.add(new Reader(2L, "Terry Xiong"));
        readers.add(new Reader(3L, "Amayah Burgess"));
        return readers;
    }

}
