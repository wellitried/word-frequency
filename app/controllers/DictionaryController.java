package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import services.DictionaryMaker;
import services.ExcelMaker;
import views.html.index;

import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;

public class DictionaryController extends Controller {

    private final DictionaryMaker dictionaryMaker;
    private final ExcelMaker excelMaker;

    @Inject
    public DictionaryController(DictionaryMaker dictionaryMaker, ExcelMaker excelMaker) {
        this.dictionaryMaker = dictionaryMaker;
        this.excelMaker = excelMaker;
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
        try {
            JsonNode dictionaryJson = request().body().asJson();
            InputStream excelStream = excelMaker.getExcelStream(dictionaryJson);

            return ok(excelStream);
        } catch (Exception e) {
            e.printStackTrace();

            return badRequest("Error during processing file.");
        }
    }

}
