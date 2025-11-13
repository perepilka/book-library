package service;

import model.Reader;
import repository.ReaderRepository;
import util.StringUtil;

import javax.naming.InvalidNameException;
import java.util.List;

public class ReaderService {

    private ReaderRepository readerRepository;
    private StringUtil stringUtil;

    public ReaderService( StringUtil stringUtil) {
        this.readerRepository = new ReaderRepository();
        this.stringUtil = stringUtil;
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader findById(Long id) {
        return readerRepository.findById(id);
    }

    public Reader save(String name) throws InvalidNameException {
        name = name.trim();
        stringUtil.checkName(name);
        Long id = (long) findAll().size();
        Reader reader = new Reader(id, name);
        return readerRepository.save(reader);
    }
}
