package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.Map;

public class DictionaryMaker {

    private JsonUtil jsonUtil = new JsonUtil();

    public JsonNode getDictionaryJson(File file, String fileName) throws Exception {
        long now = new Date().getTime();

        ObjectNode data = JsonNodeFactory.instance.objectNode();
        data.put("fileName", fileName);

        FileHandler fileHandler = new FileHandler();
        Reader reader = fileHandler.getReaderFromFile(file);
        WordsCounter wordsCounter = new WordsCounter();
        Map<String, Integer> dictionaryMap = wordsCounter.getDictionary(reader);
        data.put("dictionary", jsonUtil.mapToJson(dictionaryMap));

        System.out.println("\n\nJSON making performance check: " + (new Date().getTime() - now) + " ms;\n\n");
        return data;
    }

    public InputStream getExcelDictionary(JsonNode jsonFromRequest) {

        return new ExcelMaker().getExcelStream(jsonUtil.jsonToMap(jsonFromRequest));
    }

}
