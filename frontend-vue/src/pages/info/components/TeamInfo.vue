<script setup lang="ts">
</script>

<template>
  <section class="main">
    <h2>Ons Team</h2>
    <p>Wij zijn een dynamisch team met verschillende talenten, allemaal gedreven om samen te werken en het beste uit ons project te halen. Ieder van ons heeft zijn eigen sterke punten, en samen maken we het verschil!</p>

    <h3>Tom</h3>
    <p>Tom is de leider van ons team en de onmisbare kracht achter onze organisatie. Als de database expert weet hij precies waar iedereen mee bezig is en zorgt ervoor dat alles gestructureerd en op tijd wordt afgerond. Zijn analytische en helpende houding maakt hem de perfecte planner. Buiten het project houdt Tom van klimmen, waarbij hij zich net zo snel naar de top werkt als hij onze teamdoelen bereikt!</p>

    <h3>Sjoerd</h3>
    <p>Sjoerd is onze taalmeester, die alles weet over dataformaten en de backend. Zijn kritische blik zorgt ervoor dat alles klopt, van de code tot de content. Hij brengt niet alleen technische expertise, maar ook sfeer in ons team, en weet altijd het juiste moment te kiezen om de boel op te vrolijken. Als hij niet met code bezig is, kun je hem vinden in de wereld van gaming en Pokémon, waar hij net zo strategisch is als in ons project!</p>

    <h3>Borys</h3>
    <p>Borys is onze design expert, en heeft de basis voor zowel design als UX gelegd. Hij zorgt ervoor dat alles er niet alleen goed uitziet, maar ook functioneel is. Als teamplayer en 'jack of all trades' weet hij precies hoe hij het team scherp houdt en iedereen naar hun beste werk drijft. Wanneer hij niet bezig is met het verbeteren van de gebruikerservaring, duikt hij in de wereld van audiophile technologie, keyboards, en hardware tech, altijd op zoek naar de nieuwste snufjes!</p>

    <h3>Senna</h3>
    <p>Senna is onze frontend specialist, met een passie voor Vue.js, grafieken en charts. Zijn analytische blik helpt ons de juiste data te presenteren en zorgt ervoor dat we altijd helder en overzichtelijk blijven. Als hij niet bezig is met het bouwen van mooie visualisaties, vind je hem in de keuken waar hij de lekkerste gerechten maakt. Of hij gamet om even te ontspannen, zijn creativiteit is altijd in topvorm!</p>

    <h3>Mathijs</h3>
    <p>Mathijs is onze security en privacy expert, en zorgt ervoor dat alles veilig en goed beschermd is, van login tot registratie. Met zijn sterke focus en stiptheid is hij een harde werker die zowel op de frontend als backend uitblinkt. Zijn gedrevenheid om alles perfect te doen, komt zowel in de werkweek als in zijn hobby's naar voren – of hij nu aan het roeien is of zich in de sportschool voorbereidt op de volgende uitdaging!</p>
  </section>
</template>

<style scoped>
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 50px var(--page-padding);
  position: relative;
  z-index: 0;
  border-bottom: none;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

h3 {
  margin-top: 20px;
}

p {
  margin-bottom: 15px;
}
</style>