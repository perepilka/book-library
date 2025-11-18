package model;

public class Reader {

  private Long id;
  private String name;
  private static long counter = 0;

  public Reader(String name) {
    this.id = counter++;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return id + ". " + name;
  }
}
