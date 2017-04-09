package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.DictionaryFacade;

import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DictionaryController extends Controller {

    private final DictionaryFacade dictionaryFacade;

    @Inject
    public DictionaryController(DictionaryFacade dictionaryFacade) {
        this.dictionaryFacade = dictionaryFacade;
    }

    public Result uploadText() {
        try {
            Http.MultipartFormData.FilePart requestFilePart = request().body().asMultipartFormData().getFile("text");
            File uploaded = new File("/tmp/uploaded"); //TODO /tmp/uploaded + some id
            Files.copy(((File) requestFilePart.getFile()).toPath(), uploaded.toPath(), StandardCopyOption.REPLACE_EXISTING);
            dictionaryFacade.setFileHandler(uploaded);
            return redirect(routes.HomeController.index());
        } catch (Exception e) {
            e.printStackTrace();
            return badRequest("Error :(");
        }
    }

    public Result getDictionary() {
        InputStream dictionary = dictionaryFacade.getExcelDictionary();
        if (dictionary == null) {
            return badRequest("Error :(");
        }
        response().setHeader("Content-Disposition", "attachment; filename=result.xls");
        return ok(dictionary);
    }

    public Result getJson(int pageIndex) {
        return ok(dictionaryFacade.getPartialDictionaryJson(pageIndex));
    }
}
