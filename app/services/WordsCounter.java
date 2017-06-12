package services;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class WordsCounter {

    private static final int ASCII_DEC_a = 97;
    private static final int ASCII_DEC_z = 122;
    private static final int ASCII_DEC_A = 65;
    private static final int ASCII_DEC_Z = 90;

    Map<String, Integer> getDictionary(Reader reader) throws IOException {

        Map<String, Integer> dictionary = new HashMap<>();
        StringBuilder word = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            if (!isLetter(c) && word.length() > 0) {
                dictionary.merge(word.toString(), 1, (a, b) -> a + b);
                word.delete(0, word.length());
            } else if (isLetter(c)) {
                word.append((char) (isUppercaseLetter(c) ? c += 32 : c)); //c += 32; to lowercase
            }
        }
        dictionary = sortMap(dictionary);

        return dictionary;
    }

    private Map<String, Integer> sortMap(Map<String, Integer> unsortedMap) {
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    private boolean isLetter(int c) {
        return c >= ASCII_DEC_a && c <= ASCII_DEC_z ||
                c >= ASCII_DEC_A && c <= ASCII_DEC_Z;
    }

    private boolean isUppercaseLetter(int c) {
        return c >= ASCII_DEC_A && c <= ASCII_DEC_Z;
    }
}