package co.com.banistmo.prohibition.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.actions.MoveMouse;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.banistmo.prohibition.userinterfaces.PdfPage.PDF;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.targets.Target.the;
;

public class DownloadPdf implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
          //      WaitUntil.the(PDF, isClickable()).forNoMoreThan(20).seconds(), Click.on(PDF)
                WaitUntil.the(PDF, isClickable()).forNoMoreThan(15).seconds(),
                Scroll.to(PDF),
                MoveMouse.to(PDF),
                Click.on(PDF),
                JavaScriptClick.on(PDF)
        );
    }

    public static DownloadPdf Descarga() {
        return instrumented(DownloadPdf.class);
    }


}
