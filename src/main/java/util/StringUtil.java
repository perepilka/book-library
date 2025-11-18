package util;

import exception.LibraryException;

public class StringUtil {

    public static void checkName(String name) {
        if (name == null || name.isEmpty())
            throw new LibraryException("Name is empty");
        if (name.length() < 6 || name.length() > 29)
            throw new LibraryException("Name should be between 6 and 29 characters");
        if (!name.matches("^[a-zA-Z '-]*$"))
            throw new LibraryException("Name must contain only letters, spaces, dashes, apostrophes");
    }

    public static void checkTitle(String bookName) {
        if (bookName == null || bookName.isEmpty())
            throw new LibraryException("Title is empty");
        if (bookName.length() < 6 || bookName.length() > 99)
            throw new LibraryException("Title should be between 6 and 99 characters");
        if (bookName.matches(".*[|/\\\\#%=+*_><].*"))
            throw new LibraryException("Title must not contain the following characters: |/\\#%=+*_><");
    }

    public static void checkId(String bookId) {
        if (!bookId.matches("-?\\d+(\\\\.\\d+)?")) throw new LibraryException("Id must contain numbers!");
    }

    public static String[] splitString(String bookString) {
        String[] str = bookString.trim().split("/", 2);

        if (str.length < 2 || str[0].isEmpty() || str[1].isEmpty()) {
            throw new LibraryException("Input must contain both a title and an author, separated by '/'.");
        }

        str[0] = str[0].trim();
        str[1] = str[1].trim();

        return str;
    }


}
