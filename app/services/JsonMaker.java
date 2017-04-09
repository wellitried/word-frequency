package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Date;
import java.util.LinkedHashMap;

public class JsonMaker {
    private static final int PAGE_SIZE = 30;

    public static JsonNode getJson(int pageIndex, LinkedHashMap<String, Integer> map) {

        int lowerBoundary = PAGE_SIZE * pageIndex;
        if (lowerBoundary > map.size() || lowerBoundary < 0)
            throw new IllegalArgumentException
                    ("Incorrect lower boundary: " + lowerBoundary + " in map.size " + map.size());
        int upperBoundary = map.size() - lowerBoundary > PAGE_SIZE ?
                PAGE_SIZE :
                (map.size() - lowerBoundary);

        ArrayNode rootJson = JsonNodeFactory.instance.arrayNode();
        long now = new Date().getTime();
        map.entrySet().stream()
                .skip(lowerBoundary)
                .limit(upperBoundary)
                .forEach(entry -> {
                    ObjectNode node = JsonNodeFactory.instance.objectNode();
                    node.put("freq", entry.getValue());
                    node.put("word", entry.getKey());
                    rootJson.add(node);
                });
        System.out.println("JSON making performance check: " +
                (new Date().getTime() - now) + " ms;");

        return rootJson;
    }
}