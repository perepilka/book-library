package repository;

import model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReaderRepository {

    List<Reader> readers = new ArrayList<>();
    private long idCounter = 0;

    public ReaderRepository() {
        seed();
    }

    public List<Reader> findAll() {
        return readers;
    }

    public Optional<Reader> findById(Long id) {
        return readers.stream().filter(reader -> reader.getId().equals(id)).findFirst();
    }

    public Reader save(Reader reader) {
        reader.setId(idCounter++);
        readers.add(reader);
        return reader;
    }

    private void seed() {
        save(new Reader("Lena Cano"));
        save(new Reader("Terry Xiong"));
        save(new Reader("Amayah Burgess"));
    }
}
