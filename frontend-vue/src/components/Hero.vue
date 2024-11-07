<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';

const options = [
  { label: 'Meest verkozen partijen', value: 'most_parties' },
  { label: 'Minst verkozen partijen', value: 'least_parties' },
  { label: 'Meest verkozen kandidaten', value: 'most_candidates' },
  { label: 'Minst verkozen kandidaten', value: 'least_candidates' }
];

const selectedOption = ref(options[0].value);
const dropdownSelect = ref<HTMLSelectElement | null>(null);

const triggerDropdown = () => {
  if (dropdownSelect.value) {
    dropdownSelect.value.click();
  }
};

const allParties = ref<any[]>([]);

async function fetchData() {
  try {
    const response = await fetch("http://localhost:3001/votes/affiliations");
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    allParties.value = data;
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error);
  }
}

onMounted(() => {
  fetchData();
});

const visibleBlocks = ref(12);
const isMoreVisible = ref(true);
const isLessVisible = ref(false);

const sortedPartiesBest = computed(() => {
  return allParties.value.slice().sort((a, b) => b.affiliationVotes - a.affiliationVotes);
});

const loadMore = () => {
  visibleBlocks.value += 25;
  if (visibleBlocks.value >= allParties.value.length) {
    isMoreVisible.value = false;
    isLessVisible.value = true;
  }
};

const loadLess = () => {
  visibleBlocks.value = 12;
  isMoreVisible.value = true;
  isLessVisible.value = false;
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

const getShortenedName = (name: string) => {
  return name.length > 30 ? name.slice(0, 30) + '...' : name;
};
</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link href="https://fonts.googleapis.com/css2?family=Beiruti:wght@200..900&display=swap" rel="stylesheet">

  <section class="first main">
    <!-- Dropdown title -->
    <div class="dropdown">
      <select v-model="selectedOption" class="dropdown-select" ref="dropdownSelect">
        <option v-for="option in options" :key="option.value" :value="option.value"
                v-html="option.label">
        </option>
      </select>
      <svg xmlns="http://www.w3.org/2000/svg" class="angle-down-icon" viewBox="0 0 448 512" @click="triggerDropdown">
        <path
            d="M201.4 374.6c12.5 12.5 32.8 12.5 45.3 0l160-160c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L224 306.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l160 160z"/>
      </svg>
    </div>

      <!-- Displaying the parties -->
      <div class="parties">
        <div v-for="party in sortedPartiesBest.slice(0, visibleBlocks)" :key="party.affiliationId" class="party">
          <img class="party-img" :alt="party.affiliationName" src="../assets/img/dummy200x200.jpg">
          <p class="party-votes">{{ party.affiliationVotes }} stemmen</p>
          <p class="party-name">{{ getShortenedName(party.affiliationName) }}</p>
        </div>
      </div>

    <!-- 'Load more' section -->
    <div v-if="isMoreVisible" class="more" @click="loadMore">
      <h2 id="more-title">Laad meer</h2>
      <svg xmlns="http://www.w3.org/2000/svg" class="angle-down-icon" id="more-icon" viewBox="0 0 448 512">
        <path
            d="M201.4 374.6c12.5 12.5 32.8 12.5 45.3 0l160-160c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L224 306.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l160 160z"/>
      </svg>
    </div>

    <!-- 'Load less' section -->
    <div v-if="isLessVisible" class="less" @click="loadLess">
      <h2 id="less-title">Laad minder</h2>
      <svg xmlns="http://www.w3.org/2000/svg" class="angle-up-icon" id="less-icon" viewBox="0 0 448 512">
        <path
            d="M246.6 137.4c-12.5-12.5-32.8-12.5-45.3 0l-160 160c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L224 205.3l137.4 137.4c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3l-160-160z"/>
      </svg>
    </div>
  </section>
  <section class="main">
    <!-- Description section -->
    <div class="desc">
      <div class="desc-text">
        <h1 id="desc-title">
          DeKamer.
        </h1>
        <h2>
          Verkiezingsresultaten en discussie, vernieuwend en betrouwbaar
        </h2>
        <p id="desc-p">
          Welkom bij <strong>DeKamer</strong>, dé plek waar je <strong>betrouwbare verkiezingsresultaten</strong> vindt,
          direct en
          overzichtelijk
          gepresenteerd. Wij geloven in het anders doen. Bij ons staan de belangrijkste informatie en inzichten <strong>op
          het
          eerste gezicht zichtbaar</strong>, zodat je meteen weet waar je aan toe bent.
        </p>
        <p id="desc-p">
          Pas daarna duiken we de diepte in met uitgebreide data-analyse en discussiemogelijkheden. Met ons <strong>forum</strong>
          bieden
          we een ruimte voor <strong>constructieve gesprekken</strong> en <strong>uitwisseling van meningen</strong>,
          zodat je de cijfers en trends kunt bespreken met een community die betrokken is bij de politieke actualiteit.
          <strong>Eenvoud, duidelijkheid en kwaliteit</strong> staan voorop bij DeKamer. Hier ben jij altijd als eerste
          op de hoogte van
          de belangrijkste resultaten, en kun je de diepte in wanneer <strong>jij</strong> dat wil.
        </p>
        <p id="desc-p">
          Bij DeKamer draait het niet alleen om cijfers, maar ook om <strong>context</strong> en
          <strong>inzicht</strong>. Verkiezingsresultaten zijn meer
          dan alleen getallen; ze vertegenwoordigen de stem van het volk. Daarom presenteren wij niet alleen de
          uitslagen, maar bieden we ook <strong>grafieken, trends en historische vergelijkingen</strong> die je helpen
          om het grotere geheel te
          begrijpen. Onze <strong>interactieve tools</strong> maken het gemakkelijk om verbanden te leggen en diepgaand
          te analyseren wat de cijfers werkelijk betekenen voor de <strong>toekomst</strong> van ons land.
        </p>
      </div>
      <img class="desc-img" alt="desc" src="../assets/img/tweede-kamer-720p.jpg">
    </div>
  </section>
  <section class="last main">
    <!-- Graph section -->
    <div class="graph">
      <h1 id="graph-title">
        Stemmenverdeling Tweede Kamer
      </h1>
      <h2 id="graph-subtitle">
        Laatste update: 12-09-2021
      </h2>
      <div class="graph-img-container">
        <img class="graph-img" alt="graph" src="../assets/img/zetelverdeling-tk.png">
      </div>
    </div>
  </section>
</template>

<style scoped>
/** Styling for the main page and layout of the hero section */
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  border-bottom: var(--light-primary) 1px solid;
  z-index: 0;
}

.first {
  padding-top: 150px;
  margin: 0 auto;
}

/** Styling for the dropdown */
.dropdown {
  display: flex;
  align-items: center;
  position: relative;
  width: fit-content;
  margin-bottom: 30px;
}

/* Styling for the dropdown */
.dropdown-select {
  font-size: 38px;
  appearance: none;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-family: inherit;
}

.dropdown-select:focus {
  outline: none;
}

.dropdown-select option {
  padding: 20px;
  border-radius: 0;
}

.dropdown-select option:last-child {
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
}

.dropdown-select option:first-child {
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

.angle-down-icon {
  fill: var(--button-important);
  width: 2rem;
  cursor: pointer;
}

.angle-up-icon {
  fill: var(--button-important);
  width: 2rem;
  cursor: pointer;
}

/** Styling for the parties display section */
.parties {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
}

.party-img {
  width: 200px;
  height: 200px;
  border-radius: 20px;
  object-fit: cover;
  cursor: pointer;
  margin-bottom: 10px;
  background-color: var(--light-primary);
  transition-duration: var(--animation-duration);
}

.party-img:hover {
  transform: scale(1.05);
  background-color: var(--dark-primary);
  filter: brightness(50%);
}

.party-votes, .party-name {
  text-align: center;
}

/** Styling for the load more part */
.more, .less {
  padding-top: 50px;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}

.less {
  padding-bottom: 50px;
}

#more-title, #more-icon, #less-title, #less-icon {
  cursor: pointer;
}

/** Styling for the description section */
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

#desc-title {
  font-weight: normal;
  padding: 0;
}

#desc-p {
  line-height: 1.6rem;
}

.desc-img {
  width: 70%;
  height: 100%;
  object-fit: cover;
  border-radius: 20px;
}

/** Styling for the graph section */
.last {
  padding-bottom: 50px;
  border-bottom: none;
}

.graph {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 20px;
  padding-top: 50px;
}

#graph-title {
  padding-bottom: 20px;
}

#graph-subtitle {
  padding-bottom: 50px;
}

.graph-img-container {
  display: flex;
  justify-content: center;
  width: 100%;
}

.graph-img {
  max-height: 500px;
  max-width: 1000px;
  width: 100%;
  border-radius: 20px;
}
</style>