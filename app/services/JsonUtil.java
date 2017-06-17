package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class JsonUtil {
    Map<String, Integer> jsonToMap(JsonNode json) {
        Map<String, Integer> map = new HashMap<>();
        json.forEach(node -> map.put(node.get("word").asText(), node.get("freq").asInt()));
        return sortMap(map);
    }

    JsonNode mapToJson(Map<String, Integer> map) {
        ArrayNode resultJson = JsonNodeFactory.instance.arrayNode();
        map.entrySet()
                .forEach(entry -> {
                    ObjectNode node = JsonNodeFactory.instance.objectNode();
                    node.put("freq", entry.getValue());
                    node.put("word", entry.getKey());
                    resultJson.add(node);
                });

        return resultJson;
    }

    Map<String, Integer> sortMap(Map<String, Integer> unsortedMap) {
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }
}
