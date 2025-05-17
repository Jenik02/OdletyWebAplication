package com.example.RESTfulWebApi.service;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.RESTfulWebApi.model.Odlety;

@Service
public class OdletyService {
    // URL pro OpenSky API
    String ApiUrl = "https://opensky-network.org/api";

    RestTemplate restTemplate = new RestTemplate();
    
    // cacheuje data, po zavoláni metody podle zadaných parametrů
    @Cacheable(value = "odlety", key = "#letiste + '_' + #timestampOd + '_' + #timestampDo")

    // metoda pro získání dat odletů z OpenSky API podle parametrů: letiste, cas od, čas do 
    public Odlety[] vratOdlety(String letiste, long timestampOd, long timestampDo) {

        // uložení url s parametry do proměnné
        String url = ApiUrl + "/flights/departure?airport=" + letiste + "&begin=" + timestampOd + "&end=" + timestampDo;
        
        // vymazání bílých znaků z URL
        url = url.replaceAll("\\s+", ""); // odstraní všechny bílé znaky z URL
        
        // výpis pro ladění
        // System.out.println(url);

        // zavolání OpenSky API a uložení odpovědi do proměnné
        ResponseEntity<Odlety[]> responseEntity = restTemplate.getForEntity(url, Odlety[].class);
        
        // vrácení těla odpovědi tzn data 
        return responseEntity.getBody();
    }
}
