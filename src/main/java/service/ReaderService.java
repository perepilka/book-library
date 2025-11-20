package service;

import model.Reader;
import repository.ReaderRepository;
import util.StringUtil;

import java.util.List;
import java.util.Optional;

public class ReaderService {

  private ReaderRepository readerRepository;

  public ReaderService() {
    this.readerRepository = new ReaderRepository();
  }

  public List<Reader> findAll() {
    return readerRepository.findAll();
  }

  public Optional<Reader> findById(Long id) {
    return readerRepository.findById(id);
  }

  public Reader save(String name) {
    name = name.trim();
    StringUtil.checkName(name);

    return readerRepository.save(new Reader(name));
  }
}
