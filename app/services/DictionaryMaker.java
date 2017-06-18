package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.Reader;
import java.util.Map;

public class DictionaryMaker {

    public JsonNode getDictionaryJson(File file, String fileName) throws Exception {
        ObjectNode data = JsonNodeFactory.instance.objectNode();
        data.put("fileName", fileName);

        TextFileReader textFileReader = new TextFileReader();
        Reader reader = textFileReader.getReaderFromFile(file);
        WordsCounter wordsCounter = new WordsCounter();
        Map<String, Integer> dictionaryMap = wordsCounter.getDictionary(reader);
        JsonUtil jsonUtil = new JsonUtil();
        data.put("dictionary", jsonUtil.mapToJson(dictionaryMap));

        return data;
    }

}
