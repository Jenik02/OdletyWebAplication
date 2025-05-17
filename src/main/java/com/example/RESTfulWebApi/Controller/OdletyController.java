package com.example.RESTfulWebApi.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.RESTfulWebApi.model.Odlety;
import com.example.RESTfulWebApi.service.OdletyService;


@Controller
public class OdletyController {
    @Autowired
    private OdletyService odletyService;

    //při prázdné url se zobrazí index.html s formulářem pro odlety jako úvodní stránka
    @GetMapping("/")
    public String index() {
        return "index";
    }

    //url pro zobrazení formuláře
    @GetMapping("/formular")
    public String strankaFormulare() {
        return "formular";
    }
    
    //url pro zobrazení tabulky s odlety vraci pole datovych struktur(jednotlive odlety)
    @GetMapping("/odlety/url")
    @ResponseBody
    public Odlety[] getMethodName(@RequestParam String letiste, @RequestParam String casZacatek, @RequestParam String casKonec) {
        
        // ukazkova url http://localhost:8080/odlety/url?letiste=EDDF&casZacatek=202505160400&casKonec=202505170400
        //DateTimeFormatter pro nastaveni formatovani pozadovaneho formatu
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        //převod Stringu času nejprve na LocalDateTime podle zadaného formátu(formatter)
        LocalDateTime localDatetimeZacatek = LocalDateTime.parse(casZacatek, formatter);
        LocalDateTime localDatetimeKonec = LocalDateTime.parse(casKonec, formatter);

        // převod LocalDateTime na timestamp long
        long timestampZacatek = localDatetimeZacatek.toEpochSecond(java.time.ZoneOffset.UTC);
        long timestampKonec = localDatetimeKonec.toEpochSecond(java.time.ZoneOffset.UTC);

        // vrácení odletů z opensky api a vypsání na stránku v podobě json
        return odletyService.vratOdlety(letiste, timestampZacatek, timestampKonec);
    }    


    // metoda pro získáni odletů a přiřazeni je modelu(dále zobrazeném v odletyTabulka.html)
    @GetMapping("/odlety")
    public String vratOdlety(
    @RequestParam String letiste,
    @RequestParam String datetimeZacatek,
    @RequestParam String datetimeKonec,
    org.springframework.ui.Model model) {

    // parsovani datetime z formuláře potřeba pro převod na timestamp typu long(potřeba pro opensky api)
    LocalDateTime datetimeOdParse = LocalDateTime.parse(datetimeZacatek);
    LocalDateTime  datetimeDoParse = LocalDateTime.parse(datetimeKonec);

    // převod LocalDateTime na timestamp long
    long timestampOd = datetimeOdParse.toEpochSecond(java.time.ZoneOffset.UTC);
    long timestampDo = datetimeDoParse.toEpochSecond(java.time.ZoneOffset.UTC);

    
    // Výpis do konzole pro ověřeni(v rámci ladění)
    System.out.println("letiste: " + letiste);
    System.out.println("datetimeOd: " + datetimeZacatek + " -> " + timestampOd);
    System.out.println("datetimeDo: " + datetimeKonec + " -> " + timestampDo);

    //načtení odletů z opensky api a jejich uložení do pole datových struktur pomocí funkce z odletyService
    Odlety[] odlety = odletyService.vratOdlety(letiste, timestampOd, timestampDo);
    System.out.println(odlety.toString());

    // ukládání dat do modelu pro zobrazení v odletyTabulka.html
    model.addAttribute("odlety", odlety);
    model.addAttribute("letiste", letiste);
    model.addAttribute("datetimeOd", datetimeZacatek);
    model.addAttribute("datetimeDo", datetimeKonec);

    // zobrazení tabulky s odlety
    return "odletyTabulka";
}
}

