<script setup lang="ts">

</script>

<template>
  <div class="desc">
    <div class="desc-text">
      <h1>
        DeKamer.
      </h1>
      <h2>
        Verkiezingsresultaten en discussie, vernieuwend en betrouwbaar
      </h2>
      <p>
        Welkom bij <strong>DeKamer</strong>, dé plek waar je <strong>betrouwbare verkiezingsresultaten</strong> vindt,
        direct en
        overzichtelijk
        gepresenteerd. Wij geloven in het anders doen. Bij ons staan de belangrijkste informatie en inzichten <strong>op
        het
        eerste gezicht zichtbaar</strong>, zodat je meteen weet waar je aan toe bent.
      </p>
      <p>
        Pas daarna duiken we de diepte in met uitgebreide data-analyse en discussiemogelijkheden. Met ons
        <strong>forum</strong>
        bieden
        we een ruimte voor <strong>constructieve gesprekken</strong> en <strong>uitwisseling van meningen</strong>,
        zodat je de cijfers en trends kunt bespreken met een community die betrokken is bij de politieke actualiteit.
        <strong>Eenvoud, duidelijkheid en kwaliteit</strong> staan voorop bij DeKamer. Hier ben jij altijd als eerste
        op de hoogte van
        de belangrijkste resultaten, en kun je de diepte in wanneer <strong>jij</strong> dat wil.
      </p>
      <p>
        Bij DeKamer draait het niet alleen om cijfers, maar ook om <strong>context</strong> en
        <strong>inzicht</strong>. Verkiezingsresultaten zijn meer
        dan alleen getallen; ze vertegenwoordigen de stem van het volk. Daarom presenteren wij niet alleen de
        uitslagen, maar bieden we ook <strong>grafieken, trends en historische vergelijkingen</strong> die je helpen
        om het grotere geheel te
        begrijpen. Onze <strong>interactieve tools</strong> maken het gemakkelijk om verbanden te leggen en diepgaand
        te analyseren wat de cijfers werkelijk betekenen voor de <strong>toekomst</strong> van ons land.
      </p>
    </div>
    <img class="desc-img" alt="desc" src="../../../assets/img/tweede-kamer-720p.jpg">
  </div>
</template>

<style scoped>
.desc {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 50px;
  padding-top: 50px;
}

.desc-text {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.desc-text p {
  line-height: 1.6rem;
}

.desc-text h1 {
  font-weight: normal;
  padding: 0;
}

.desc-img {
  object-fit: cover;
  border-radius: 20px;
}

@media only screen and (min-width: 1401px) {
  .desc-img {
    width: 40vw;
  }
}

@media only screen and (max-width: 1400px) {
  .desc {
    flex-direction: column;
    gap: 20px;
  }

  .desc-img {
    width: 100%;
  }
}
</style>