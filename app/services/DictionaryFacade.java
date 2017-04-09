package services;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public class DictionaryFacade {

    private FileHandler fileHandler;
    private Map<String, Integer> dictionaryMap;

    public void setFileHandler(File file) {
        fileHandler = new FileHandler();
        fileHandler.setFile(file);
        setDictionaryMap();
    }

    private void setDictionaryMap() {
        WordsCounter wordsCounter = new WordsCounter();
        try {
            Reader reader = fileHandler.getReaderFromFile();
            dictionaryMap = wordsCounter.getDictionary(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getExcelDictionary() {
        if (fileHandler == null || dictionaryMap == null)
            throw new IllegalStateException("FileHandler wasn't set.");

        ExcelMaker excelMaker = new ExcelMaker();
        InputStream excelDictionary = excelMaker.getExcelStream(dictionaryMap);
        //System.out.println(dictionaryMap + "\n" + dictionaryMap.size());

        return excelDictionary;
    }

    public JsonNode getPartialDictionaryJson(int pageIndex) {
        return JsonMaker.getJson(pageIndex, (LinkedHashMap<String, Integer>) dictionaryMap);
    }

}
