package book;

public record Book(Long id, String name, String author) {

    @Override
    public String toString() {
        return id + ". \"" + name  + "\" - " + author + "\n";
    }
}