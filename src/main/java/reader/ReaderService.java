package reader;

import java.util.List;

public class ReaderService {

    private ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader save(String name) {
        Long id = (long) findAll().size();
        Reader reader = new Reader(id, name);
        readerRepository.save(reader);
        return reader;
    }

}
