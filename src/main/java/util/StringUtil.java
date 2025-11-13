package util;

import javax.naming.InvalidNameException;

public class StringUtil {

    public void checkName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty())
            throw new InvalidNameException("Name is empty");
        if (name.length() < 6 || name.length() > 29)
            throw new InvalidNameException("Name should be between 6 and 29 characters");
        if (!name.matches("^[a-zA-Z '-]*$"))
            throw new InvalidNameException("Name must contain only letters, spaces, dashes, apostrophes");
    }

    public void checkTitle(String bookName) throws InvalidNameException {
        if (bookName == null || bookName.isEmpty())
            throw new InvalidNameException("Title is empty");
        if (bookName.length() < 6 || bookName.length() > 99)
            throw new InvalidNameException("Title should be between 6 and 99 characters");
        if (bookName.matches(".*[|/\\\\#%=+*_><].*"))
            throw new InvalidNameException("Title must not contain the following characters: |/\\#%=+*_><");
    }

    public String[] splitString(String bookString) throws InvalidNameException {
        String[] str = bookString.trim().split("/", 2);

        if (str.length < 2 || str[0].isEmpty() || str[1].isEmpty()) {
            throw new InvalidNameException("Input must contain both a title and an author, separated by '/'.");
        }

        str[0] = str[0].trim();
        str[1] = str[1].trim();

        return str;
    }


}
