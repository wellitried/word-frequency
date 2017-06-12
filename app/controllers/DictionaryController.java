package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import services.DictionaryMaker;

import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class DictionaryController extends Controller {

    private final DictionaryMaker dictionaryMaker;

    @Inject
    public DictionaryController(DictionaryMaker dictionaryMaker) {
        this.dictionaryMaker = dictionaryMaker;
    }


    public Result getJsonFromBook() {
        try {
            String filename = request().body().asMultipartFormData().getFile("file").getFilename();
            File requestFile = (File) request().body().asMultipartFormData().getFile("file").getFile();

            File uploaded = new File("/tmp/uploaded/" + filename + " " + new Date().toString());
            uploaded.getParentFile().mkdirs();
            uploaded.createNewFile();

            Files.copy(requestFile.toPath(), uploaded.toPath(), StandardCopyOption.REPLACE_EXISTING);

            JsonNode result = dictionaryMaker.getDictionaryJson(uploaded, filename);

            return ok(result);
        } catch (Exception e) {
            e.printStackTrace();

            return badRequest("Error during processing file.");
        }
    }

    public Result getExcelDictionary() {
        InputStream dictionary = dictionaryMaker.getExcelDictionary(null/*jsonNodeFromRequest*/);

        response().setHeader("Content-Disposition", "attachment; filename=result.xls");
        return ok(dictionary);
    }

}
