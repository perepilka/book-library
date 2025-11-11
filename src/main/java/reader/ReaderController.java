package reader;

import util.StringUtil;

import javax.naming.InvalidNameException;
import java.util.List;

public class ReaderController {

    private ReaderService readerService;
    private StringUtil stringUtil;

    public ReaderController(ReaderService readerService, StringUtil stringUtil) {
        this.readerService = readerService;
        this.stringUtil = stringUtil;
    }

    public List<Reader> getAllReaders() {
        return readerService.findAll();
    }

    public Reader createReader(String name) throws InvalidNameException {
        stringUtil.checkName(name);
        return readerService.save(name);
    }

}
