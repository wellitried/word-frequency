package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.DictionaryFacade;

import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;

public class DictionaryController extends Controller {

    private final DictionaryFacade dictionaryFacade;

    @Inject
    public DictionaryController(DictionaryFacade dictionaryFacade) {
        this.dictionaryFacade = dictionaryFacade;
    }

    public Result uploadText() {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart text = body.getFile("text");
        if (text != null) {
            dictionaryFacade.setFileHandler((File) text.getFile());
            return redirect(routes.HomeController.index());
        } else {
            return badRequest("Error :(");
        }
    }

    public Result getDictionary() {
        InputStream dictionary = dictionaryFacade.getDictionary();
        if (dictionary == null) {
            return badRequest("Error :(");
        }

        response().setHeader("Content-Disposition", "attachment; filename=result.xls");
        return ok(dictionary);
    }
}
