# Data Processing Documentation

## Gegevensverzameling

### Overzicht
De gegevensverzameling op onze website is beperkt tot het strikt noodzakelijke om de functionaliteit van het forum en gerelateerde functies te ondersteunen. Er wordt bewust geen aanvullende informatie verzameld of verwerkt.

### Verzamelde gegevens
Bij het gebruik van het forum verzamelen wij de volgende gegevens:
- **E-mailadres**: Nodig voor het aanmaken van accounts en communicatie met gebruikers.
- **Gebruikersnaam**: Gebruikt als unieke identificatie voor het forum.
- **Posts**: Content die gebruikers op het forum plaatsen.
- **Geboortedatum**: Verzameld tijdens registratie.
- **Wachtwoord (gehasht)**: Gebruikt voor authenticatie. Het wachtwoord wordt veilig opgeslagen in een gehashte vorm en is niet leesbaar voor ons.

### Methode van gegevensverzameling
- **Registratie**: Tijdens het registreren van een account worden de benodigde gegevens (e-mailadres, gebruikersnaam, geboortedatum en wachtwoord) verzameld.
- **Plaatsen van posts**: Posts worden opgeslagen op het moment dat een gebruiker deze publiceert.

Er wordt verder geen aanvullende gegevensverzameling uitgevoerd, zoals het loggen van zoekopdrachten of andere gebruikersinteracties.

### Tools en technologieën
Er worden geen externe tools of technologieën gebruikt voor gegevensverzameling. Alle gegevens worden lokaal beheerd.

### Timing van gegevensverzameling
- **Tijdens registratie**: Gegevens worden opgeslagen wanneer een gebruiker succesvol een account aanmaakt.
- **Bij het plaatsen van posts**: Posts worden opgeslagen wanneer deze door de gebruiker worden verzonden.

### Informeren van gebruikers
Gebruikers worden indirect geïnformeerd over de gegevensverzameling door meldingen zoals:
- Succesvolle accountaanmaak.  
  Gebruikers hebben de mogelijkheid om eigen posts te verwijderen, waardoor gegevens permanent uit ons systeem kunnen worden verwijderd.

### Opslaglocatie
Alle verzamelde gegevens worden opgeslagen in een SQL-database. Er worden geen gegevens lokaal op gebruikersapparaten of in externe systemen opgeslagen.

----------------------------------------------------------------------------------------------------------------------------

## Gegevensopslag

### Overzicht
Onze gegevens worden opgeslagen in een externe database-oplossing en zijn beveiligd met passende maatregelen om zowel privacy als beveiliging te waarborgen. Hieronder volgen de details van de opslag en het toegangsbeheer.

### Opslaglocaties
- **Database**:  
  Alle gegevens worden opgeslagen in een externe **Aiven-database**.  
  De database bevat:
    - Gebruikersgegevens (e-mailadres, gebruikersnaam, geboortedatum, gehashte wachtwoorden)
    - Forumdata (posts)

### Beveiligingsmaatregelen
1. **Toegangsbeheer**:
    - Alleen beheerders hebben toegang tot de database via speciale **administratoraccounts**.
    - Toegang tot beheerdersaccounts wordt strikt gecontroleerd en beperkt.
    - Gebruikers kunnen via de website hun eigen gegevens beheren (bijvoorbeeld posts verwijderen).

2. **Versleuteling**:
    - **Wachtwoorden** worden gehashed met **bcrypt** om de beveiliging van gebruikersaccounts te garanderen.
    - **JWT (JSON Web Tokens)** wordt gebruikt voor authenticatie van acties die gebruikers uitvoeren, zoals het beheren van accounts of posts.

3. **Gegevensoverdracht**:
    - Gegevens worden via beveiligde verbindingen (HTTPS) overgedragen tussen de website en de database.

### Back-ups en gegevensintegriteit
- Er worden geen back-ups van de database gemaakt omdat het een projectomgeving betreft (schoolwebsite).

### Toegangsrechten
- **Admins**:  
  Alleen administrators hebben directe toegang tot de database en kunnen deze beheren.
- **Gebruikers**:  
  Gebruikers kunnen via de website hun eigen posts en accounts beheren en indien gewenst verwijderen.

### Logging
Er worden momenteel geen logbestanden opgeslagen.

----------------------------------------------------------------------------------------------------------------------------

## Gegevensverwerking

### Overzicht
De gegevensverwerking op onze website is eenvoudig en gericht op het ondersteunen van de basisfunctionaliteit van de website. Er worden geen complexe verwerkingsstappen uitgevoerd, en de gebruiker heeft controle over hun eigen gegevens.

### Verwerkingsstappen
1. **Validatie**:
    - Tijdens het aanmaken van een account wordt gecontroleerd of:
        - De opgegeven **gebruikersnaam** en **e-mailadres** al in gebruik zijn.
        - Alle vereiste velden correct en volledig zijn ingevuld.
    - Bij een fout ontvangt de gebruiker een algemene foutmelding (“Er is iets fout gegaan”).

2. **Opslag**:
    - Zodra de validatie succesvol is, worden de gegevens rechtstreeks doorgestuurd naar de backend, waar ze worden verwerkt en opgeslagen in de database.
    - Er wordt geen verdere transformatie uitgevoerd.

3. **Gebruikersbeheer**:
    - Gebruikers hebben zelf controle over hun gegevens, zoals het wijzigen van hun wachtwoord of gebruikersnaam.

### Analyse en opschoning
- **Analyse**: Er wordt geen analyse uitgevoerd op de gegevens.
- **Opschoning**: Er worden geen geautomatiseerde opschoningsprocessen uitgevoerd. Gebruikers hebben echter de mogelijkheid om hun eigen gegevens te bewerken of verwijderen, zoals posts of accounts.

### Automatisering
- Er zijn geen geautomatiseerde processen voor gegevensverwerking of rapportages.

----------------------------------------------------------------------------------------------------------------------------


## Gegevensoverdracht

### Overzicht
Gegevens worden tussen de frontend (de website) en de backend overgedragen via verschillende HTTP-methoden. De overdracht van gegevens is beveiligd en maakt gebruik van moderne authenticatie- en autorisatiemethoden om de privacy en veiligheid van gebruikers te waarborgen.

### Methodes van Gegevensoverdracht
- **HTTP-methoden**:
    - **POST**: Gebruikt om nieuwe posts te maken.
    - **GET**: Gebruikt voor het ophalen van data, zoals het ophalen van openbare gegevens en het gebruiken van tools zoals heatmaps en grafieken.
    - **DELETE**: Gebruikt voor het verwijderen van posts.
    - **Fetch API**: Voor het verstrekken van dynamische gegevens vanuit de backend naar de frontend, zoals het ophalen van gebruikers- of postinformatie.

- **Externe API’s**:
    - Voor de **heatmap** wordt een externe API van het CBS aangeroepen om coördinaten van gemeentes in Nederland op te halen voor visualisatie. Dit gebeurt via een GET-request.

### Beveiliging van Gegevensoverdracht
- **HTTPS**:  
  De communicatie tussen de website (frontend) en de backend gebeurt altijd via **HTTPS**, wat zorgt voor een versleutelde verbinding en de beveiliging van gegevens tijdens de overdracht.

- **JWT**:
    - Gebruikersauthenticatie wordt afgedwongen met behulp van **JWT (JSON Web Tokens)**. Het JWT-token wordt versleuteld in de frontend opgeslagen en wordt bij elk verzoek naar de backend meegestuurd voor validatie.
    - Het JWT-token wordt bij elk verzoek gecontroleerd door de backend om de geldigheid van de gebruiker te bevestigen.
    - Wachtwoorden worden **nooit** overgedragen tijdens de gegevensoverdracht. Alleen openbare gebruikersinformatie zoals **e-mail**, **gebruikersnaam**, en **leeftijd** zijn beschikbaar nadat de gebruiker succesvol is ingelogd.

### Gegevens die worden Overgedragen
- **Postdata**:  
  De inhoud van de berichten (inclusief titel en tekst) wordt verzonden naar de backend bij het maken of verwijderen van posts.

- **Gebruikersdata**:  
  Alleen openbare gegevens van de gebruiker (zoals e-mail, gebruikersnaam en leeftijd) worden overgedragen. Gevoelige gegevens, zoals het wachtwoord, worden nooit overgedragen.

- **Toolsdata**:  
  Er worden **GET**-verzoeken gestuurd naar de backend voor het ophalen van openbare gegevens voor tools zoals de heatmap en grafieken.

### Authenticatie en Autorisatie
- **JWT-tokenvalidatie**:  
  De backend controleert elk verzoek met een JWT-token om te bevestigen dat de gebruiker is geauthenticeerd en gemachtigd om de gevraagde actie uit te voeren.

- **Fetch Interceptor**:  
  De frontend bevat een **fetch interceptor** die controleert of de gebruiker een geldig account heeft en of de gebruiker gemachtigd is om de gevraagde actie uit te voeren. Als de gebruiker niet geautoriseerd is, wordt de actie geblokkeerd.

### Foutafhandeling
- **Foutmeldingen**:  
  Bij fouten, zoals het proberen toegang te krijgen tot niet-bestaande pagina's, wordt een **404-error** weergegeven. Gebruikers krijgen een popupmelding en worden automatisch doorgestuurd naar een geldige pagina.

### Beperkingen
- **Geen bestandsoverdracht**:  
  Er zijn geen mogelijkheden om bestanden naar de database te uploaden. Alleen tekstgebaseerde berichten kunnen worden verzonden en opgeslagen, samen met een titel.

----------------------------------------------------------------------------------------------------------------------------


## Gegevensgebruik

### Overzicht
De gegevens die worden verzameld op onze website worden alleen gebruikt voor de basisfunctionaliteit en zijn niet bedoeld voor geavanceerde toepassingen zoals personalisatie, statistische analyse, machine learning of marketing.

### Toepassingen
1. **Personalisatie**:  
   De verzamelde gegevens worden **niet** gebruikt voor personalisatie van de gebruikerservaring. Er worden geen inhoudsaanpassingen of gerichte aanbevelingen gedaan op basis van de gegevens van de gebruiker.

2. **Statistische analyse**:  
   Er wordt **geen** statistische analyse uitgevoerd op de verzamelde gegevens. Er worden geen gegevens verzameld of geanalyseerd om statistieken over gebruikersgedrag of postfrequentie te berekenen.

3. **Machine Learning**:  
   Er wordt geen gebruik gemaakt van machine learning voor de verwerking van gebruikersgegevens of voor het doen van voorspellingen of aanbevelingen.

4. **Marketing**:  
   De verzamelde gegevens worden **niet** gebruikt voor marketingdoeleinden, zoals gerichte advertenties of e-mailcampagnes.

### Gebruikerscontrole
- Omdat de verzamelde gegevens uitsluitend voor basisfunctionaliteit worden gebruikt, is er geen behoefte aan specifieke gebruikersinstellingen of voorkeuren met betrekking tot het gebruik van hun gegevens voor personalisatie of marketing.

### Gegevensdeling met Derden
- Er worden **geen** gegevens gedeeld met derden voor gebruik. Alle verzamelde gegevens blijven binnen het systeem van de website en worden niet doorgestuurd naar externe partijen voor andere doeleinden.

----------------------------------------------------------------------------------------------------------------------------

## Beveiliging en privacy

### Beveiligingsmaatregelen
1. **Encryptie**:
    - **Wachtwoorden** worden gehasht met **bcrypt** voordat ze in de database worden opgeslagen. Dit betekent dat zelfs de beheerders geen toegang hebben tot de wachtwoorden van gebruikers.

2. **Beveiliging van hosting**:
    - De **frontend** wordt gehost bij **Strato**, de **backend** bij **Render**, en de **database** bij **Aiven**. Elk van deze externe partijen heeft hun eigen beveiligingsmaatregelen, maar deze zijn buiten onze controle. Aangezien dit een schoolproject betreft, is er geen aanvullende verantwoordelijkheid voor ons om deze beveiliging te beheren.

3. **Beveiligingsmaatregelen van derden**:
    - Er is geen verdere beveiliging van persoonsgegevens door onszelf, aangezien de externe partijen waarschijnlijk hun eigen beveiligingsmaatregelen hebben geïmplementeerd. We vertrouwen op hun infrastructuur en beveiliging.

### Gebruikersrechten
1. **Toegang tot gegevens**:
    - Gebruikers kunnen hun eigen gegevens inzien via de **detailpagina** van hun account. Ze kunnen hun **e-mail**, **gebruikersnaam** en **leeftijd** bekijken.

2. **Recht op verwijdering**:
    - Gebruikers hebben het recht om hun gegevens volledig te **verwijderen**. Dit kan eenvoudig worden gedaan via de website, waarbij gebruikers hun eigen posts en accounts kunnen verwijderen.
    - Wij als beheerders kunnen handmatig gegevens verwijderen, maar gebruikers hebben de volledige controle om hun eigen gegevens te verwijderen.

3. **Geen controle over gegevens**:
    - Omdat gebruikers zelf in staat zijn om hun gegevens te verwijderen, hebben wij geen controle over hun gegevens na verwijdering. Zodra ze hun gegevens verwijderen, worden ze uit het systeem verwijderd.

### Datalekken en incidenten
- Er zijn momenteel **geen procedures** voor datalekken of beveiligingsincidenten, aangezien dit een schoolproject is en zulke maatregelen niet verwacht werden. Er is geen beleid voor het melden van datalekken aan gebruikers.

### Compliance met AVG/GDPR
- Aangezien dit een schoolproject is, wordt er **geen expliciete toestemming** gevraagd voor het verzamelen van gegevens, en er wordt geen verdere naleving van de AVG/GDPR verwacht of uitgevoerd. De gegevens worden uitsluitend verzameld voor de werking van de website en worden niet voor andere doeleinden gebruikt.

----------------------------------------------------------------------------------------------------------------------------

## Gegevensverwijdering

### Methoden van Gegevensverwijdering
1. **Verwijdering van gegevens**:
    - Wanneer een gebruiker zijn/haar **account** verwijdert, worden **alle posts** van die gebruiker eveneens verwijderd, samen met het account zelf. Na deze actie worden er geen gegevens meer van de gebruiker bewaard, en er worden geen logbestanden of andere gegevens geretourneerd of bewaard. De verwijdering is volledig.
    - Gebruikers kunnen ook **individuele posts** verwijderen, wat betekent dat zij controle hebben over hun eigen bijdragen aan de website. De verwijdering van een post is definitief en kan niet worden hersteld.

2. **Anonimisering**:
    - Er is momenteel geen gebruik van **anonimisering** van gegevens. De gegevens worden in hun geheel verwijderd wanneer een gebruiker zijn/haar account of post verwijdert.

### Tijdschema voor Gegevensverwijdering
1. **Verwijdering bij account- of postverwijdering**:
    - Gegevens worden **direct verwijderd** zodra een gebruiker zijn/haar account of post verwijdert. Na de bevestiging door de gebruiker is de verwijdering permanent en kan deze niet worden teruggedraaid.

2. **Bewaartermijnen**:
    - Er is geen **bewaartermijn** voor gegevens. Alle gegevens worden onmiddellijk verwijderd zodra de gebruiker besluit deze te verwijderen. Er worden geen gegevens bewaard na verwijdering van het account of de posts.

### Verwijdering en Verificatie
1. **Verificatie van verwijdering**:
    - Na het verwijderen van gegevens wordt de actie gecontroleerd door middel van een **JWT-tokenvalidatie** in de backend. Als het token geldig is, wordt de actie goedgekeurd en kan de gebruiker de gegevens verwijderen.
    - Na een succesvolle verwijdering wordt de gebruiker doorverwezen naar de **homepagina** van de website.

### Backups en Onvolledige Verwijdering
1. **Backups**:
    - Er wordt geen gegevens bewaard in backups na de verwijdering. Alle gegevens kunnen volledig worden verwijderd, en er zijn geen **residuen** van verwijderde gegevens in het systeem, behalve openbare gegevens die worden gebruikt voor tools zoals de heatmap (deze gegevens worden **permanent bewaard** en zijn **niet gepland om verwijderd te worden**).

2. **Gegevens die niet verwijderd kunnen worden**:
    - Er worden geen gegevens bewaard of geretourneerd nadat een account of post is verwijderd, met uitzondering van de openbare gegevens die door de websitetools worden gebruikt (zoals voor de heatmap).

### Juridische of Administratieve Redenen voor Bewaring
- Er zijn **geen** juridische verplichtingen die vereisen dat gegevens langer worden bewaard dan strikt noodzakelijk. Gegevens worden alleen verzameld voor de werking van de website en worden volledig verwijderd wanneer de gebruiker dat wenst.

----------------------------------------------------------------------------------------------------------------------------

## Procesbeschrijving

### 1. Verzameling van Gegevens
- **Bij accountregistratie** vult de gebruiker de volgende gegevens in:
    - **Username**
    - **Email**
    - **Wachtwoord** (dit wordt **gehasht** met bcrypt in de backend en verlaat nooit de backend, zodat het niet toegankelijk is)
    - **Geboortedatum**
- **Bij het plaatsen van een post** vult de gebruiker een **titel** en een **body** in. Deze gegevens worden gekoppeld aan de gebruiker en in de database opgeslagen. De post wordt daarnaast gemarkeerd met de **tijd van plaatsing** en de **username** van de gebruiker die de post heeft gemaakt.
- **Opmerking**: Er worden geen andere gegevens van gebruikers verzameld, aangezien er geen andere gegevens worden gevraagd.

### 2. Opslag van Gegevens
- De verzamelde gegevens worden naar de **backend** gestuurd voor verwerking en worden vervolgens opgeslagen in een **Aiven-database**.
    - Het **wachtwoord** wordt geëncrypteerd (met bcrypt) en wordt veilig opgeslagen, zonder dat het de backend ooit verlaat.
    - De **gebruikersnaam**, **email**, **geboortedatum** en **posts** worden open opgeslagen in de database, maar de database is alleen toegankelijk voor **geautoriseerde beheerders** met een account bij Aiven.
    - Er worden geen andere gegevens opgeslagen.

### 3. Verwerking van Gegevens
- **Registratie**:
    - Bij het registreren van een gebruiker wordt gecontroleerd of de **gebruikersnaam** of **email** al in gebruik is. Als dit het geval is, ontvangt de gebruiker een foutmelding.
- **Postplaatsing**:
    - Bij het plaatsen van een post wordt gecontroleerd of de post niet leeg is. Dit wordt gecontroleerd door de **frontend**.
    - **Id-based** opslag wordt gebruikt voor posts, wat betekent dat duplicaten in principe geen probleem vormen.
    - Er wordt verder geen verwerking van de gegevens uitgevoerd. De gegevens worden enkel opgeslagen en weergegeven in de gebruikersinterface.

### 4. Gebruik van Gegevens
- **Gebruikersnaam** wordt gebruikt om de naam van de gebruiker weer te geven bij een post die hij of zij heeft gemaakt.
- De **titel** en **body** van de post worden openbaar weergegeven op de website.
- **Email** en **geboortedatum** zijn alleen zichtbaar op de **accountdetailpagina**, maar alleen voor de ingelogde gebruiker zelf (wanneer deze zijn/haar eigen account bekijkt).
- Er worden geen andere **statistieken** of **analyse** van de gegevens uitgevoerd. Alle gegevens die niet zichtbaar zijn op de accountdetailpagina blijven **verborgen**.

### 5. Verwijdering van Gegevens
- Gebruikers kunnen altijd hun **posts** of hun **account** verwijderen.
    - Wanneer een gebruiker een post verwijdert, wordt die post **permanent verwijderd**.
    - Wanneer een gebruiker zijn/haar account verwijdert, wordt zowel het account als **alle posts** van de gebruiker **permanent verwijderd**.

### 6. Beveiliging tijdens het Proces
- **JWT-tokenvalidatie**:
    - Bij elke postactie (zoals het plaatsen of verwijderen van een post) wordt de gebruiker gecontroleerd via een **JWT-token**. Dit token bepaalt of de gebruiker gerechtigd is om de actie uit te voeren.
- Alle communicatie tussen frontend en backend vindt plaats via **veilige verbindingen (HTTPS)**, waardoor gegevens onderweg versleuteld zijn.

----------------------------------------------------------------------------------------------------------------------------

## Tools en Technologieën

### 1. **Database**
- **Database**: De website maakt gebruik van een **Aiven-managed PostgreSQL database** voor het opslaan van gebruikersgegevens en posts.
    - **Beheer**: De database wordt beheerd via **Aiven**, een externe service die de database-hosting verzorgt, en is alleen toegankelijk voor beheerders met een account bij Aiven.

### 2. **Backend Framework**
- **Technologieën en Frameworks**:
    - De **backend** van de website is gebouwd met **Java** en maakt gebruik van het **Spring Boot** framework, gecombineerd met **JPA** (Java Persistence API) voor databasetoegang en -beheer.
    - De projectafhankelijkheden worden beheerd via **Maven**.

### 3. **Externe API's**
- **Externe API**: De website maakt gebruik van de **CBS Open Data API** om **informatie over gemeenten** in Nederland op te halen, wat wordt gebruikt voor de heatmap-functionaliteit.

### 4. **Authenticatie**
- **Authenticatie**: De website maakt gebruik van **JWT (JSON Web Tokens)** voor gebruikersauthenticatie. Dit zorgt ervoor dat elke gebruikersactie die authenticatie vereist, zoals het plaatsen of verwijderen van een post, wordt gecontroleerd aan de hand van een geldig token.

### 5. **Encryptie**
- **Encryptie**:
    - **Wachtwoorden**: Wachtwoorden worden geëncrypteerd met **bcrypt** voordat ze worden opgeslagen in de database. Dit zorgt ervoor dat wachtwoorden niet in platte tekst worden opgeslagen.
    - **Communicatie**: Alle communicatie tussen de frontend en backend is beveiligd via **HTTPS**, wat zorgt voor versleuteling van gegevens tijdens de overdracht.

### 6. **Frontend Framework**
- **Frontend**: De website is gebouwd met **Vue.js** en **TypeScript**. De volgende npm-pakketten worden gebruikt:
    - **ApexCharts** voor het genereren van grafieken en statistieken.
    - **Leaflet** en **Leaflet-Heat** voor het weergeven van interactieve kaarten en de heatmap-functionaliteit.

### 7. **Monitoring en Logging**
- **Logging**: Op dit moment wordt er **geen logging** uitgevoerd voor de verwerking van gegevens of gebruikersactiviteiten.

### 8. **Beveiliging**
- **Beveiliging**: Alle gegevensoverdracht tussen de frontend en backend is versleuteld via **HTTPS**, wat de beveiliging van gegevens tijdens communicatie garandeert.

----------------------------------------------------------------------------------------------------------------------------

## Beveiligingsmaatregelen

### 1. **Encryptie**
- **Wachtwoordbeveiliging**: Wachtwoorden worden veilig opgeslagen door middel van **bcrypt**, een veilige hashingtechniek. Hierdoor kunnen de wachtwoorden niet in platte tekst worden ingezien, zelfs niet door beheerders.
- **Versleuteling van gegevens**: Op dit moment worden er geen andere gegevens dan wachtwoorden versleuteld. Alle communicatie tussen de frontend en backend wordt beveiligd via **HTTPS**, wat zorgt voor encryptie van gegevens tijdens de overdracht.

### 2. **Toegangscontrole**
- **Rolgebaseerde Toegang**:
    - Er zijn twee hoofdrollen: **admin** en **gebruiker**.
    - **Admins** hebben de mogelijkheid om gebruikers te beheren, bijvoorbeeld om gebruikers te verbannen. Alleen beheerders kunnen **handmatig accounts aanmaken** in de database.
    - **Gebruikers** hebben alleen toegang tot hun eigen gegevens en kunnen posts maken en verwijderen.

- **Toegang tot de Database**:
    - De **database** is beveiligd en alleen toegankelijk voor beheerders met een geldig account bij **Aiven**. Dit voorkomt ongeautoriseerde toegang tot de gegevens in de database.

- **Authenticatie**:
    - **JWT** wordt gebruikt voor de **authenticatie** van gebruikers. Dit zorgt ervoor dat gebruikers alleen geautoriseerde acties kunnen uitvoeren als ze inloggen en een geldig token hebben.

### 3. **Gegevensbescherming**
- **Bescherming tegen Datalekken**:
    - **Wachtwoorden** zijn geëncrypteerd met bcrypt, waardoor ze niet in platte tekst kunnen worden ingezien. Dit biedt bescherming tegen datalekken van wachtwoorden.
    - Er is geen aanvullende laag van gegevensbeveiliging, maar de database is alleen toegankelijk met een account dat beheerd wordt door **Aiven**, en er zijn geen bekende mogelijkheden voor onbevoegde toegang.

### 4. **Logging en Monitoring**
- **Logging en Monitoring**:
    - Momenteel is er **geen logging** van verdachte activiteiten of andere beveiligingsincidenten.
    - Er zijn geen tools geïmplementeerd om actieve beveiligingsmonitoring te doen.

### 5. **Backups en Herstel**
- **Backups**:
    - Er worden **geen regelmatige back-ups** van de gegevens gemaakt, gezien dit een schoolproject betreft. Dit betekent dat als er gegevens verloren gaan, herstel niet mogelijk is.

### 6. **API-beveiliging**
- **Externe API's**:
    - De website maakt gebruik van de **CBS Open Data API** om **gemeente-informatie** op te halen voor de heatmap-functionaliteit. Deze API is een **GET-request** die geen gebruikersdata vereist.
    - Omdat deze API alleen openbare gegevens ophaalt, is er geen beveiligingsrisico voor gebruikersdata.

----------------------------------------------------------------------------------------------------------------------------

### Gegevensstromen

#### 1. **Gebruikersregistratie**:
- **Input**: Gebruiker voert in:
    - Username
    - Email
    - Leeftijd
    - Wachtwoord
- **Validatie**:
    - Backend controleert of het email of username al bestaat.
    - Wachtwoord wordt gehashed (bcrypt) voordat het in de database wordt opgeslagen.
- **Opslag**:
    - De gegevens worden opgeslagen in de **Aiven database**, inclusief gehashte wachtwoorden.
- **Output**:
    - Gebruiker ontvangt een JWT-token voor toekomstige authenticatie.

#### 2. **Login**:
- **Input**: Gebruiker voert in:
    - Email
    - Wachtwoord
- **Validatie**:
    - Backend vergelijkt de ingevoerde gegevens met de gegevens in de database.
    - Als geldig, ontvangt de gebruiker een JWT-token.
- **Opslag**:
    - Geen extra gegevens worden opgeslagen, maar de gebruiker heeft nu toegang tot zijn/haar account en bijbehorende data.
- **Output**:
    - JWT-token wordt opgeslagen in de frontend (voor authenticatie bij toekomstige acties).

#### 3. **Gegevensopslag**:
- **Opslaglocatie**:
    - Gegevens van gebruikers (username, email, leeftijd, en gehashte wachtwoorden) worden opgeslagen in de **Aiven database**.
    - Geen gegevens worden lokaal opgeslagen in de frontend, behalve het JWT-token, dat alleen dient voor authenticatie.

#### 4. **Gegevensoverdracht**:
- **Input**:
    - Frontend vraagt via **GET**-requests data op voor tools zoals de **heatmap** en **grafieken**.
    - Backend levert aangepaste data voor de **chart** en **leaflet** via unieke controllers.
- **Gegevensverwerking**:
    - CBS data (gemeentegegevens, bevolkingsaantallen, etc.) wordt opgehaald via een externe API zonder gebruikersdata.
    - Geen gebruikersdata wordt gedeeld met de CBS API; de backend vergelijkt enkel de gegevens.
- **Beveiliging**:
    - Alle communicatie tussen de frontend en backend is beveiligd via **HTTPS**.
    - JWT-token wordt gebruikt voor het valideren van requests.

#### 5. **Gegevensverwijdering**:
- **Verwijdering van gegevens**:
    - Als een gebruiker zijn/haar account verwijdert, worden alle gegevens van die gebruiker (inclusief posts) direct uit de database verwijderd.
    - Gebruikers kunnen hun eigen posts ook verwijderen via de website.
- **Validatie**:
    - Gebruikers moeten zijn ingelogd met een geldig JWT-token om hun account te kunnen verwijderen.

#### 6. **Gebruik van Externe API’s (CBS)**:
- **Input**:
    - Gemeente- en bevolkingsdata wordt opgevraagd via de **CBS Open Data API**.
    - Deze API levert openbare gegevens zoals gemeentecordinaten en bevolkingsaantallen.
- **Output**:
    - Gegevens worden verwerkt door de backend en gebruikt voor de tools (zoals de heatmap en de vergelijkingsfunctie).
    - Er wordt geen gebruikersdata gedeeld met de CBS API, en gebruikers hoeven niet ingelogd te zijn om de tools te gebruiken.

---

### Visuele Gegevensstroom

Als we deze gegevensstromen visueel zouden weergeven in een flowchart, ziet het er ongeveer als volgt uit:

1. **Registratie**:
    - Gebruiker → Website (input) → Validatie (Backend) → Opslag in Aiven DB → JWT-token → Gebruiker (output).

2. **Login**:
    - Gebruiker → Website (input) → Validatie (Backend) → JWT-token → Gebruiker (output).

3. **Data-opvraging voor Tools**:
    - Frontend → Backend (via GET-requests) → Backend verwerkt CBS-gegevens → Frontend ontvangt aangepaste data (bijv. voor heatmap/grafieken).

4. **Gegevensverwijdering**:
    - Gebruiker → Website (verwijder verzoek) → Validatie JWT-token → Verwijdering uit Aiven DB → Gebruiker (output).
