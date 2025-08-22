package co.com.banistmo.prohibition.questions;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.Normalizer;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidarPdf implements Question<Boolean> {

    private final String textoEsperado;
    private final Integer minPaginasFallback; // si no encuentras texto, valida por páginas
    private final Duration timeout = Duration.ofSeconds(15);

    public static ValidarPdf texto(String t){ return new ValidarPdf(t, null); }
    public static ValidarPdf textoOPaginas(String t, int minPaginas){
        return new ValidarPdf(t, minPaginas);
    }
    private ValidarPdf(String t, Integer minPaginas){ this.textoEsperado = t; this.minPaginasFallback = minPaginas; }

    @Override public Boolean answeredBy(Actor actor) {
        WebDriver d = BrowseTheWeb.as(actor).getDriver();
        String original = d.getWindowHandle();

        // Espera nueva pestaña
        long end = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < end && d.getWindowHandles().size() == 1) {
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        if (d.getWindowHandles().size() < 2) {
            debug("No se abrió nueva pestaña");
            return false;
        }

        // Cambia a nueva pestaña
        for (String h : d.getWindowHandles()) if (!h.equals(original)) { d.switchTo().window(h); break; }

        // Resolver URL real del PDF (viewer/blob -> embed/iframe/performance)
        String url = d.getCurrentUrl();
        debug("URL inicial: " + url);
        if (url == null || url.startsWith("blob")) {
            try {
                url = (String)((JavascriptExecutor)d).executeScript(
                        "const e=document.querySelector('embed[type=\"application/pdf\"],iframe'); return e? e.src : null;"
                );
                debug("src detectado: " + url);
            } catch (Exception ignored) {}
        }
        if (url == null) {
            try {
                Object candidate = ((JavascriptExecutor)d).executeScript(
                        "const es=performance.getEntriesByType('resource').map(r=>r.name).filter(n=>n.toLowerCase().includes('.pdf')); return es.length? es[es.length-1]: null;"
                );
                if (candidate instanceof String) url = (String)candidate;
                debug("performance URL: " + url);
            } catch (Exception ignored) {}
        }
        if (url == null) {
            debug("No se pudo resolver URL del PDF");
            volver(d, original);
            return false;
        }

        // Descarga con cookies de la sesión actual
        try {
            String cookieHeader = d.manage().getCookies().stream()
                    .map(c -> c.getName()+"="+c.getValue())
                    .collect(Collectors.joining("; "));

            HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .header("Cookie", cookieHeader)
                    .header("Accept", "application/pdf,*/*")
                    .GET().build();

            HttpResponse<byte[]> resp = client.send(req, HttpResponse.BodyHandlers.ofByteArray());
            debug("HTTP status: " + resp.statusCode());
            if (resp.statusCode() != 200) {
                volver(d, original);
                return false;
            }

            try (PDDocument doc = PDDocument.load(new ByteArrayInputStream(resp.body()))) {
                int paginas = doc.getNumberOfPages();
                debug("páginas: " + paginas);

                // Extrae texto ORDENADO por posición y normaliza
                PDFTextStripper st = new PDFTextStripper();
                st.setSortByPosition(true);
                String raw = st.getText(doc);
                String texto = norm(raw);
                String esperado = norm(textoEsperado);
                String preview = texto.length() > 300 ? texto.substring(0, 300) : texto;
                debug("preview texto norm: " + preview);

                boolean ok = !esperado.isBlank() && texto.contains(esperado);

                // Fallback por páginas si se configuró
                if (!ok && minPaginasFallback != null) {
                    ok = paginas >= minPaginasFallback;
                    debug("fallback páginas >= " + minPaginasFallback + " -> " + ok);
                }

                volver(d, original);
                return ok;
            }
        } catch (Exception e) {
            debug("Excepción: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            volver(d, original);
            return false;
        }
    }

    private static String norm(String s){
        if (s == null) return "";
        String t = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}+","");
        return t.toLowerCase().replaceAll("\\s+"," ").trim();
    }

    private static void volver(WebDriver d, String original){
        try { d.close(); } catch (Exception ignored) {}
        try { d.switchTo().window(original); } catch (Exception ignored) {}
    }

    private static void debug(String msg){
        Serenity.recordReportData().withTitle("PDF DEBUG").andContents(msg);
        System.out.println("[PDF DEBUG] " + msg);
    }
}

