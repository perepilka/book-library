package reader;

import java.util.ArrayList;
import java.util.List;

public class ReaderRepository {

    List<Reader> readers = new ArrayList<>();

    public ReaderRepository() {
    }

    public ReaderRepository(List<Reader> readers) {
        this.readers = readers;
    }

    public List<Reader> findAll() {
        return readers;
    }

    public Reader save(Reader reader) {
        readers.add(reader);
        return reader;
    }
}
