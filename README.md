RESTfulWebApi – Odlety letadel z letiště

Tato aplikace umožňuje zobrazit seznam odletů z konkrétního letiště za zvolené časové období. Data jsou získávána z veřejného API OpenSky Network.

dependencies pro pom.xml přidávány z: maven central repository
https://central.sonatype.com/

Technologie

Java 24
Spring Boot 3
Maven
Thymeleaf
Caffeine

fungování na portu: 8080

zpracovaná URL: `/api/odlety?letiste=XXXX&casKonec=RRRRMMDDHHMM&casZacatek=RRRRMMDDHHMM`
Vrací JSON seznam odletů z daného letiště za časový interval.
K otestování například: 'http://localhost:8080/api/odlety/url?letiste=EDDF&casZacatek=202505160400&casKonec=202505170400'


Pomocí HTML formuláře pro výběr letiště a časového rozmezí, 
se zobrazí pouze záznamy letů začínajících na zadaném letišti a v časovém rozmezí
Výsledky jsou zobrazeny v tabulce.


Data z OpenSky API jsou cachována po dobu 1 hodiny, pomocí Caffeine CacheManageru

OdletyWebAplication - hlavní třída SpringBootu, obsahuje main, spouští aplikaci

OdletyController - zajišťuje práci s daty z formuláře a podává je OdletyService, získává data poskytnuté OdletyService a zobrazuje je.
Přepíná html pohledy

OdletyService - zajišťuje komunikaci s Opensky api, posílá get requesty, vrací data, předává data 

Odlety - udává strukturu dat, odpovídá struktuře odletů očekáváných z opensky api


HTML

index.html - úvodní stránka webové aplikace, možnost přepnutí do ostatních pohledů, obsahuje odkaz na vypsání přímo z url.

formular.html - obsahuje formulář pro zobrazení odletů filtrovaných dle letiště a času od, do

odletyTabulka - stránka s tabulkou, zobrazuje uložená data v model entitě vytvořené v OdletyController ve formě tabulky