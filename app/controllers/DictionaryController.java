package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import services.DictionaryMaker;
import views.html.index;

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

    public Result index() {
        return ok(index.render());
    }

    public Result getJsonFromBook() {
        try {
            String filename = request().body().asMultipartFormData().getFile("file").getFilename();
            File requestFile = (File) request().body().asMultipartFormData().getFile("file").getFile();
            JsonNode result = dictionaryMaker.getDictionaryJson(requestFile, filename);

            return ok(result);
        } catch (Exception e) {
            e.printStackTrace();

            return badRequest("Error during processing file.");
        }
    }

    public Result getExcelDictionary() {
        InputStream dictionary = dictionaryMaker.getExcelDictionary(request().body().asJson());

        return ok(dictionary);
    }

}
