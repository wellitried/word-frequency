package services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public class DictionaryFacade {

    private FileHandler fileHandler;

    public void setFileHandler(File file) {
        fileHandler = new FileHandler();
        fileHandler.setFile(file);
    }

    public InputStream getDictionary() {
        if (fileHandler == null)
            throw new IllegalStateException("FileHandler wasn't set.");

        Reader reader = null;
        Map<String, Integer> dictionaryMap = null;
        ExcelMaker excelMaker = null;
        InputStream dictionary = null;
        try {
            reader = fileHandler.getReaderFromFile();
            WordsCounter wordsCounter = new WordsCounter();
            dictionaryMap = wordsCounter.getDictionary(reader);
            excelMaker = new ExcelMaker();
            dictionary = excelMaker.getExcelStream(dictionaryMap);
            //System.out.println(dictionaryMap + "\n" + dictionaryMap.size());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictionary;
    }
}
