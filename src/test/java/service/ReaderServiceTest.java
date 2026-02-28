package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import exception.LibraryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Reader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ReaderRepository;

@ExtendWith(MockitoExtension.class)
class ReaderServiceTest {

  @Mock
  private ReaderRepository readerRepository;
  @InjectMocks
  private ReaderService readerService;

  @Test
  void whenFindAll_thenReturnsListOfReaders() {
    //Arrange
    List<Reader> expectedReaders = List.of(
        new Reader(1L, "Lena Cano"),
        new Reader(2L, "Terry Xiong")
    );
    when(readerRepository.findAll()).thenReturn(expectedReaders);

    //Act
    List<Reader> returnedReaders = readerService.findAll();

    //Assert
    assertEquals(2, returnedReaders.size());
    assertEquals(expectedReaders, returnedReaders);


  }

  @Test
  void whenFindById_thenReturnsOptionalReader() {
    //Arrange
    Optional<Reader> expectedReader = Optional.of(new Reader(1L, "Lena Cano"));
    when(readerRepository.findById(1L)).thenReturn(expectedReader);

    //Act
    var actualReader = readerService.findById(1L);

    //Assert
    assertEquals(expectedReader, actualReader);
  }

  @Test
  void givenValidName_whenSave_thenReturnsSavedReader() {
    //Arrange
    Reader expectedReader = new Reader(1L, "Lena Cano");
    when(readerRepository.save(any(Reader.class))).thenReturn(expectedReader);

    //Act
    var actualReader = readerService.save("Lena Cano");

    //Assert
    assertEquals(expectedReader, actualReader);
  }

  @Test
  void givenEmptyName_whenSave_thenThrowsException() {
    LibraryException exception = assertThrows(LibraryException.class,
        () -> readerService.save(""));
    assertEquals("Name is empty", exception.getMessage());
  }
}

