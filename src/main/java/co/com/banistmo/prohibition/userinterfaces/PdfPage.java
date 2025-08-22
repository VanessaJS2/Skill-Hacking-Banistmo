package co.com.banistmo.prohibition.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public class PdfPage {

    public static final Target PDF = Target.the("Descarga PDF")
            .locatedBy("//div[@id='tab4']//tr[td[@data-th='Documento' and normalize-space()='Prohibiciones']] /td[@data-th='PDF']//a[contains(@href,'.pdf')]");
}
