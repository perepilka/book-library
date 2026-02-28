package util;

import static org.junit.jupiter.api.Assertions.*;

import exception.LibraryException;
import org.junit.jupiter.api.Test;

class StringUtilTest {

  @Test
  void givenValidName_whenCheckName_thenNotThrowsException() {
    //Arrange
    String givenName = "Lena Cano";
    //Act and Asser
    assertDoesNotThrow(() -> StringUtil.checkName(givenName));
  }

  @Test
  void givenEmptyName_whenCheckName_thenThrowsException() {
    //Arrange
    String givenName = "";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkName(givenName));
    assertEquals("Name is empty", exception.getMessage());
  }

  @Test
  void givenLongName_whenCheckName_thenThrowsException() {
    //Arrange
    String givenName = "Lena Canoooooooooooooooooooooooooooooooooooooooooooooooooooo";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkName(givenName));
    assertEquals("Name should be between 6 and 29 characters", exception.getMessage());
  }

  @Test
  void givenShortName_whenCheckName_thenThrowsException() {
    //Arrange
    String givenName = "Lena";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkName(givenName));
    assertEquals("Name should be between 6 and 29 characters", exception.getMessage());
  }

  @Test
  void givenNameWithInvalidSymbols_whenCheckName_thenThrowsException() {
    //Arrange
    String givenName = "Lena! Cano*";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkName(givenName));
    assertEquals("Name must contain only letters, spaces, dashes, apostrophes", exception.getMessage());
  }

  @Test
  void givenValidTitle_whenCheckTitle_thenNotThrowsException() {
    //Arrange
    String givenTitle = "A Tale of Two Cities";
    //Act and Asser
    assertDoesNotThrow(() -> StringUtil.checkTitle(givenTitle));
  }

  @Test
  void givenEmptyTitle_whenCheckTitle_thenThrowsException() {
    //Arrange
    String givenTitle = "";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkTitle(givenTitle));
    assertEquals("Title is empty", exception.getMessage());
  }

  @Test
  void givenLongTitle_whenCheckTitle_thenThrowsException() {
    //Arrange
    String givenTitle = "A Tale of Two Citiesssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkTitle(givenTitle));
    assertEquals("Title should be between 6 and 99 characters", exception.getMessage());
  }

  @Test
  void givenShortTitle_whenCheckTitle_thenThrowsException() {
    //Arrange
    String givenTitle = "A T";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkTitle(givenTitle));
    assertEquals("Title should be between 6 and 99 characters", exception.getMessage());
  }

  @Test
  void givenTitleWithInvalidSymbols_whenCheckTitle_thenThrowsException() {
    //Arrange
    String givenTitle = "A Tale of Two Cities%^!";
    //Act and Asser
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.checkTitle(givenTitle));
    assertEquals("Title must not contain the following characters: |/\\#%=+*_><", exception.getMessage());
  }

  @Test
  void givenValidId_whenCheckId_thenNotThrowsException() {
    String givenId = "1";
    assertDoesNotThrow(() -> StringUtil.checkId(givenId));
  }

  @Test
  void givenNull_whenCheckID_thenThrowsException() {
    String givenId = null;
    assertThrows(LibraryException.class, () -> StringUtil.checkId(givenId));
  }

  @Test
  void givenInvalidId_whenCheckID_thenThrowsException() {
    String givenId = "1.0";
    assertThrows(LibraryException.class, () -> StringUtil.checkId(givenId));
  }

  @Test
  void givenValidString_whenSplitString_thenReturnsArrayWithTwoElements() {
    //Arrange
    String givenString = "A Tale of Two Cities / Charles Dickens";
    //Act
    String[] result = StringUtil.splitString(givenString);
    //Assert
    assertNotNull(result);
    assertEquals(2, result.length);
    assertEquals("A Tale of Two Cities", result[0]);
    assertEquals("Charles Dickens", result[1]);
  }

  @Test
  void givenStringWithExtraSpaces_whenSplitString_thenReturnsTrimmedArray() {
    //Arrange
    String givenString = "  The Little Prince  /  Antoine de Saint-Exupéry  ";
    //Act
    String[] result = StringUtil.splitString(givenString);
    //Assert
    assertEquals(2, result.length);
    assertEquals("The Little Prince", result[0]);
    assertEquals("Antoine de Saint-Exupéry", result[1]);
  }

  @Test
  void givenStringWithMultipleSlashes_whenSplitString_thenSplitsOnlyOnFirst() {
    //Arrange
    String givenString = "Book Title / Author Name / Extra Part";
    //Act
    String[] result = StringUtil.splitString(givenString);
    //Assert
    assertEquals(2, result.length);
    assertEquals("Book Title", result[0]);
    assertEquals("Author Name / Extra Part", result[1]);
  }

  @Test
  void givenStringWithoutSlash_whenSplitString_thenThrowsException() {
    //Arrange
    String givenString = "A Tale of Two Cities";
    //Act and Assert
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.splitString(givenString));
    assertEquals("Input must contain both a title and an author, separated by '/'.", exception.getMessage());
  }

  @Test
  void givenStringWithEmptyTitle_whenSplitString_thenThrowsException() {
    //Arrange
    String givenString = " / Charles Dickens";
    //Act and Assert
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.splitString(givenString));
    assertEquals("Input must contain both a title and an author, separated by '/'.", exception.getMessage());
  }

  @Test
  void givenStringWithEmptyAuthor_whenSplitString_thenThrowsException() {
    //Arrange
    String givenString = "A Tale of Two Cities / ";
    //Act and Assert
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.splitString(givenString));
    assertEquals("Input must contain both a title and an author, separated by '/'.", exception.getMessage());
  }

  @Test
  void givenStringWithOnlySlash_whenSplitString_thenThrowsException() {
    //Arrange
    String givenString = "/";
    //Act and Assert
    LibraryException exception = assertThrows(LibraryException.class, () -> StringUtil.splitString(givenString));
    assertEquals("Input must contain both a title and an author, separated by '/'.", exception.getMessage());
  }

  @Test
  void givenStringWithSpacesAroundSlash_whenSplitString_thenReturnsCorrectArray() {
    //Arrange
    String givenString = "The Alchemist/Paulo Coelho";
    //Act
    String[] result = StringUtil.splitString(givenString);
    //Assert
    assertEquals(2, result.length);
    assertEquals("The Alchemist", result[0]);
    assertEquals("Paulo Coelho", result[1]);
  }
}