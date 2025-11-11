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
            System.out.println(
                    """
                            PLEASE, SELECT ONE OF THE FOLLOWING ACTIONS BY TYPING THE OPTION’S NUMBER AND PRESSING ENTER KEY:
                            [1] SHOW ALL BOOKS IN THE LIBRARY
                            [2] SHOW ALL READERS REGISTERED IN THE LIBRARY
                            TYPE “EXIT” TO STOP THE PROGRAM AND EXIT!
                            """);
            System.out.println(
                    switch (scanner.nextLine()) {
                        case "1" -> books;
                        case "2" -> readers;
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
                new Book(1L, "A Tale of Two Cities", "Charles Dickens"),
                new Book(2L, "The Little Prince", "The Little Prince"),
                new Book(3L, "The Alchemist", "Paulo Coelho")
        );
    }

    private static List<Reader> seedReaders() {
        return List.of(
                new Reader(1L, "Lena Cano"),
                new Reader(2L, "Terry Xiong"),
                new Reader(3L, "Amayah Burgess")
        );
    }

}
