package co.com.banistmo.prohibition.questions;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.net.URL;


public class ValidarPdf implements Question<String> {

    public static Question<String> leer() {
        return new ValidarPdf();
    }

    @Override
    public String answeredBy(Actor actor) {
        try {
            String url = BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
           // System.out.println(url );
            if (url == null) return "";
            try (var in = new URL(url).openStream();
                 var doc = PDDocument.load(in)) {
                return new PDFTextStripper().getText(doc);
            }
        } catch (Exception e) {
            return "";
        }
    }
}

