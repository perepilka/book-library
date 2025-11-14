package service;

import exception.ReaderNotFoundException;
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

    public Reader findById(Long id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()) {
            return reader.get();
        } else throw new ReaderNotFoundException("Reader not found, id: " + id);
    }

    public Reader save(String name) {
        name = name.trim();
        StringUtil.checkName(name);

        return readerRepository.save(new Reader(null, name));
    }
}
