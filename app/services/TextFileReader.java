package services;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.nio.charset.Charset;

class TextFileReader {

    Reader getReaderFromFile(File text) throws IOException {

        if (text == null)
            throw new IllegalStateException();

        InputStreamReader inputStreamReader;
        String encoding = figureOutEncoding(text);
        if (encoding == null) {
            inputStreamReader = new InputStreamReader(new FileInputStream(text));
        } else {
            inputStreamReader = new InputStreamReader(new FileInputStream(text), Charset.forName(encoding));
        }

        return new BufferedReader(inputStreamReader);
    }

    private String figureOutEncoding(File file) throws IOException {
        byte[] buf = new byte[4096];
        FileInputStream fis = new FileInputStream(file);
        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();

        return encoding;
    }

}
