package model;

public record Reader(Long id, String name) {

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
