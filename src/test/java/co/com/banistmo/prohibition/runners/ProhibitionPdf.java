package co.com.banistmo.prohibition.runners;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;   // ← en lugar de cucumber.api.*
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/download_pdf.feature",
        glue = "co.com.banistmo.prohibition.stepdefinitions",
        plugin = {"pretty"},
        snippets = CucumberOptions.SnippetType.CAMELCASE // ← SnippetType actualizado
)
public class ProhibitionPdf {}