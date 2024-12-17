<script setup lang="ts">
</script>

<template>
  <section class="first main">
    <h1 class="info-title">Het Nederlandse kiesstelsel: Uitleg voor iedereen</h1>
    <p class="info-text">
      Het Nederlandse kiesstelsel is ontworpen om ervoor te zorgen dat iedereen, ongeacht hun politieke voorkeur, eerlijk vertegenwoordigd wordt in de politiek. Dit betekent dat het aantal zetels dat een partij in het parlement krijgt, recht evenredig is aan het aantal stemmen dat die partij ontvangt van de kiezers.
    </p>

    <h2>Wat zijn de verkiezingen in Nederland?</h2>
    <p class="info-text">
      In Nederland worden verschillende soorten verkiezingen gehouden. De belangrijkste verkiezingen zijn de:
      <ul>
        <li><strong>Tweede Kamerverkiezingen</strong>: Dit zijn de verkiezingen voor het Nederlandse parlement. De Tweede Kamer bestaat uit 150 leden die door de burgers worden gekozen.</li>
        <li><strong>Gemeenteraadsverkiezingen</strong>: Dit zijn verkiezingen voor de gemeenteraad, het lokaal bestuur van de gemeenten in Nederland.</li>
        <li><strong>Provinciale Statenverkiezingen</strong>: Hierbij kiezen de inwoners van de provincies hun vertegenwoordigers in de Provinciale Staten.</li>
        <li><strong>Europese Verkiezingen</strong>: Hierbij kiezen Nederlanders hun vertegenwoordigers voor het Europese Parlement, dat in Brussel zit.</li>
      </ul>
    </p>

    <div class="info-styling-container">
    <div class="info-text-container">
    <h2>Hoe werkt het kiesstelsel?</h2>
    <p class="info-text">
      Het Nederlandse kiesstelsel is gebaseerd op het principe van <strong>evenredige vertegenwoordiging</strong>. Dit betekent dat het aantal zetels dat een politieke partij krijgt in verhouding staat tot het aantal stemmen dat de partij heeft behaald. Dus als een partij 30% van de stemmen krijgt, krijgt die partij ook ongeveer 30% van de zetels in de Tweede Kamer.
    </p>

    <p class="info-text">
      Het is belangrijk om te weten dat Nederland een <strong>lijststelsel</strong> heeft. Dit betekent dat kiezers niet direct op een kandidaat stemmen, maar op een politieke partij. Elke partij heeft een lijst van kandidaten, en de zetels worden verdeeld aan de hand van de stemmen voor die partij.
    </p>

    <h2>De verkiezingen en zetelverdeling</h2>
    <p class="info-text">
      Bij de Tweede Kamerverkiezingen stemmen de mensen op politieke partijen, niet op individuele kandidaten. De stemmen worden geteld en de zetels in de Tweede Kamer worden verdeeld op basis van de hoeveelheid stemmen die elke partij heeft gekregen. Dit gebeurt volgens een systeem genaamd het <strong>d'Hondt-systeem</strong>, een wiskundige formule die bepaalt hoeveel zetels een partij krijgt.
    </p>

    <p class="info-text">
      Om te voorkomen dat partijen die nauwelijks stemmen krijgen te veel invloed hebben, geldt er een <strong>kiesdrempel</strong>. Deze drempel zorgt ervoor dat een partij minimaal een bepaald percentage van de stemmen moet behalen (meestal rond de 0,67%) om überhaupt zetels te kunnen krijgen.
    </p>

    <h2>Waarom evenredige vertegenwoordiging?</h2>
    <p class="info-text">
      Het idee achter evenredige vertegenwoordiging is dat iedere stem telt en dat het parlement een goed afspiegeling moet zijn van de politieke voorkeuren van de burgers. In Nederland betekent dit dat er veel politieke partijen zijn, van grote partijen tot kleinere partijen. Dit zorgt ervoor dat ook kleinere partijen, die misschien maar een klein aantal stemmen krijgen, alsnog een plek in het parlement kunnen veroveren.
    </p>
    </div>
    <div class="info-img-container">
      <img class="info-img" src="@/assets/img/tweede-kamer-1280p.jpg" alt="tweede-kamer-1280p">
    </div>
    </div>

    <p class="info-text">
      Dit systeem heeft voor- en nadelen. Een voordeel is dat de politiek breder vertegenwoordigd is, maar een nadeel kan zijn dat er veel kleinere partijen zijn, wat het moeilijk maakt om een stabiele regering te vormen. Daarom moeten partijen vaak samenwerken om een coalitie te vormen.
    </p>

    <h2>Wie mag stemmen?</h2>
    <p class="info-text">
      In Nederland mag iedere Nederlander vanaf 18 jaar oud stemmen bij de verkiezingen. Dit geldt voor zowel de landelijke verkiezingen als de lokale verkiezingen. Voor de Europese verkiezingen mogen ook Nederlanders die in het buitenland wonen stemmen, zolang ze in een EU-land wonen.
    </p>

    <h2>Wat gebeurt er na de verkiezingen?</h2>
    <p class="info-text">
      Na de verkiezingen worden de stemmen geteld, en op basis van de uitkomst worden de zetels verdeeld. Vervolgens begint de formatie van de regering. Dit gebeurt door partijen die samen een meerderheid in de Tweede Kamer vormen. Deze partijen gaan met elkaar in gesprek om een coalitie te vormen. Dit is vaak een langdurig proces, omdat verschillende partijen compromissen moeten sluiten over beleid.
    </p>

    <h2>Waarom is dit belangrijk?</h2>
    <p class="info-text">
      Het kiesstelsel in Nederland is belangrijk omdat het zorgt voor een eerlijker politiek systeem. Het geeft mensen van verschillende politieke stromingen een kans om vertegenwoordigd te worden. Dit systeem draagt bij aan een divers en dynamisch politiek landschap, waarin meerdere partijen invloed kunnen uitoefenen op het beleid.
    </p>

    <p class="info-text">
      Kortom, de Nederlandse verkiezingen en het kiesstelsel zorgen ervoor dat iedereen een stem heeft in hoe het land wordt bestuurd. Dit maakt het politieke systeem van Nederland bijzonder representatief en democratisch.
    </p>
  </section>
</template>

<style scoped>
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 20px var(--page-padding);
  position: relative;
  z-index: 0;
}

.info-title {
  margin-top: 20px;
  margin-bottom: 12px;
  padding: 0;
  letter-spacing: 0;
}

h2 {
  margin-top: 20px;
  margin-bottom: 8px;
  letter-spacing: 0;

}

.info-text {
  margin-top: 4px;
  margin-bottom: 8px;
  letter-spacing: 0;
}

ul {
  margin-left: 20px;
  letter-spacing: 0;
}

.info-styling-container {
  display: flex;
  flex-direction: row;
  gap: 50px;
  max-width: 100vw;
}

.info-img-container {
  height: 100%;
}

.info-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px;
  margin-top: 10px;
}
</style>