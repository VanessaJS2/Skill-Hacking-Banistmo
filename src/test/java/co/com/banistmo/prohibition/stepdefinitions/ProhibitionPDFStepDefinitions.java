package co.com.banistmo.prohibition.stepdefinitions;


import co.com.banistmo.prohibition.questions.ValidarPdf;
import co.com.banistmo.prohibition.tasks.DownloadPdf;
import co.com.banistmo.prohibition.tasks.Navegate;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static co.com.banistmo.prohibition.constants.Constants.URL;
import static co.com.banistmo.prohibition.constants.Constants.USER;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.*;
import static org.hamcrest.Matchers.containsString;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;


public class ProhibitionPDFStepDefinitions {

    @Before

    public void preparation() {
        setTheStage(new OnlineCast());
        theActorCalled(USER);
        WebDriverManager.chromedriver().setup();

    }

    @Given("el usuario entra ala pagina de banistmo Personas")
    public void elUsuarioEntraAlaPaginaDeBanistmoPersonas() {
        theActorInTheSpotlight().wasAbleTo(Open.url(URL));

    }
    @When("el usuario busca el documento requerido")
    public void elUsuarioBuscaElDocumentoRequerido() {
        theActorInTheSpotlight().attemptsTo(Navegate.page());

    }

    @And("hace clic en el ícono PDF de Prohibiciones")
    public void haceClicEnElÍconoPDFDeProhibiciones() {
        theActorInTheSpotlight().attemptsTo(DownloadPdf.Descarga());

    }


    @Then("el PDF de Prohibiciones se muestra correctamente")
    public void elPDFDeProhibicionesSeMuestraCorrectamente() {
        String texto = "Prohibiciones";
        var d = BrowseTheWeb.as(theActorInTheSpotlight()).getDriver();
        var tabs = new java.util.ArrayList<>(d.getWindowHandles());
        d.switchTo().window(tabs.get(tabs.size()-1));

        theActorInTheSpotlight().should(
                seeThat( ValidarPdf.leer(), containsString(texto))

        );
    }
}



