package co.com.banistmo.prohibition.userinterfaces;


import net.serenitybdd.screenplay.targets.Target;

public class PersonasPage {

    public static final Target COOKIES = Target.the("COOKIE")
            .locatedBy("//button[@id=\"btn-aceptar-cookies\"]");

    public static final Target PRODUCTOS_SERVICIOS  = Target.the("Productos y Servicios")
            .locatedBy("//a[contains(@class,'dropdown-toggle') and contains(.,'Productos')]");

    public static final Target PRESTAMOS = Target.the("Préstamos")
            .locatedBy("//ul[contains(@class,'nav navbar-nav')] //div[@class]//a[contains (.,'Préstamos')]");

    public static final Target PRESTAMOS_AUTO = Target.the("Préstamos de Auto")
            .locatedBy("//a[@title='Préstamos de Auto' and contains(@href,'/prestamos/automoviles') and contains(.,'Préstamos de Auto')]");

    public static final Target AUTOS_REGULARES = Target.the("Préstamos de Automóviles Regulares")
            .locatedBy("//a[@title='Cuentas de Ahorro' and contains(@href,'/automoviles-regulares') and contains(.,'Préstamos de Automóviles Regulares')]");

    public static final Target TASAS_TARIFAS = Target.the("Tasas y tarifas")
            .locatedBy("//ul[@id='filialTabs']//a[@href='#tab4' and normalize-space(.)='Tasas y tarifas']");
}


