package model;

public class Reader {

  private Long id;
  private final String name;

  public Reader(String name) {
    this.name = name;
  }

  public Reader(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return id + ". " + name;
  }
}
