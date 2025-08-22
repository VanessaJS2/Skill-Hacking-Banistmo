package co.com.banistmo.prohibition.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.banistmo.prohibition.userinterfaces.PersonasPage.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class Navegate implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitUntil.the(COOKIES, isClickable()).forNoMoreThan(20).seconds(), Click.on(COOKIES),
                WaitUntil.the(PRODUCTOS_SERVICIOS, isClickable()).forNoMoreThan(20).seconds(), Click.on(PRODUCTOS_SERVICIOS),
                WaitUntil.the(PRESTAMOS,          isClickable()).forNoMoreThan(10).seconds(), Click.on(PRESTAMOS),
                WaitUntil.the(PRESTAMOS_AUTO,     isClickable()).forNoMoreThan(10).seconds(), Click.on(PRESTAMOS_AUTO),
                WaitUntil.the(AUTOS_REGULARES,     isClickable()).forNoMoreThan(10).seconds(), Click.on(AUTOS_REGULARES),
                WaitUntil.the(TASAS_TARIFAS,      isClickable()).forNoMoreThan(20).seconds(), Click.on(TASAS_TARIFAS)

        );
    }

    public static Navegate page() {
        return instrumented(Navegate.class);
    }

}
