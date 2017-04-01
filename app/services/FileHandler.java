package services;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.nio.charset.Charset;

class FileHandler {

    private File text;

    public void setFile(File file) {
        this.text = file;
    }

    Reader getReaderFromFile() throws IOException {
        //text = getFile();
        if (text == null)
            throw new IllegalStateException("FileHandler wasn't set.");

        String encoding = figureOutEncoding(text);

        return new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(text),
                        Charset.forName(encoding)));
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

        if (encoding == null)
            throw new IOException("Something wrong with encoding.");

        return encoding;
    }

}
