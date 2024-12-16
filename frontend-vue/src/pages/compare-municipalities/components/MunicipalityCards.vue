<script setup lang="ts">
import type {Municipality} from "@/types/Municipality";
import {computed, type PropType} from "vue";
import { useAdditionalMunicipalitiesData} from "@/composables/useMunicipalities";
import type {MunicipalityCard} from "@/types/MunicipalityCard";

const props = defineProps({
  municipalities: {
    type: Object as PropType<Municipality[]>,
    required: true
  }
})

const cards: MunicipalityCard[] = [];

const {municipalityList, isLoading} = useAdditionalMunicipalitiesData(props.municipalities);

/**
 * Format a number to the Dutch number format
 * @param {string | number} value
 */
function formatNumber(value: string | number ) {
  if (!value) {
    return undefined;
  }
  let numberValue = value;
  if (typeof value === "string") {
    numberValue = parseInt(value);
  }
  return numberValue.toLocaleString("nl-NL");
}

/**
 * Reset the cards array to the initial state
 * From the initial state, the cards array is filled with the data from the municipalities
 */
function resetCards() {
  cards.length = 0;
  cards.push(
      {
        title: "Aantal inwoners",
        municipalities: [],
      },
      {
        title: "Meest gestemde partijen",
        municipalities: [],
      },
      {
        title: "Meest gestemde kandidaten",
        municipalities: [],
      },
      {
        title: "Opkomst percentage",
        municipalities: [],
      },
      {
        title: "Gemiddelde leeftijd",
        subtitle: "in jaren",
        municipalities: [],
      },
      {
        title: "Kiesgerechtigden",
        municipalities: [],
      },
  );
}

/**
 * Returns a string representing a number in fixed-point notation using a comma(,) instead of a dot(.).
 * @param value The number to be converted to a string.
 * @param fractionDigits Number of digits after the decimal point. Must be in the range 0 - 20, inclusive.
 */
function toDutchFixed(value: number, fractionDigits: number = 1) {
  if (fractionDigits < 0 || fractionDigits > 20) {
    throw new RangeError("fractionDigits must be in the range 0 - 20, inclusive.");
  }
  if (!value) {
    return undefined;
  }
  return value.toFixed(fractionDigits).replace(".", ",");
}

/**
 * Add the data from the municipalities to the cards array
 * Each card has a title and an array of municipalities with their data
 */
const municipalityCards = computed(() => {
  if (isLoading.value) {
    return [];
  }
  resetCards();
  cards.forEach((card, cardIndex) => {
    municipalityList.value.forEach((municipality) => {
      switch (cardIndex) {
        case 0:
          card.municipalities.push({
            municipalityName: municipality.name,
            value: formatNumber(municipality.additionalData.population),
          });
          break;
        case 1:
          card.municipalities.push({
            municipalityName: municipality.name,
            value: municipality.affiliations[0].affiliationName,
            subValue: formatNumber(municipality.affiliations[0].totalVotes),
            subValueSuffix: " stemmen",
          });
          break;
        case 2:
          card.municipalities.push({
            municipalityName: municipality.name,
            value: municipality.topCandidate.name,
            subValue: formatNumber(municipality.topCandidate.votes),
            subValueSuffix: " stemmen",
          });
          break;
        case 3:
          card.municipalities.push({
            municipalityName: municipality.name,
            value: toDutchFixed(municipality.additionalData.turnoutPercentage),
            valueSuffix: "%",
          });
          break;
        case 4:
          card.municipalities.push({
            municipalityName: municipality.name,
            value: toDutchFixed(municipality.additionalData.averageAge),
          });
          break;
        case 5:
          card.municipalities.push({
            municipalityName: municipality.name,
            value: formatNumber(municipality.additionalData.eligibleVoters),
          });
          break;
      }
    });
  });
  return cards;
});

</script>

<template>
  <div v-if="isLoading" class="center">
    <div class="loader"></div>
  </div>
  <div class="cards" v-if="municipalityList.length > 0">
    <div class="card" v-for="card in municipalityCards.filter((c) => {
      return c.municipalities[0].value !== undefined;
    })" :key="card.municipality">
      <div class="title">
        <h3>{{card.title}}</h3>
        <p v-if="card.subtitle">{{card.subtitle}}</p>
      </div>
      <div class="municipalities">
        <div v-for="municipality in card.municipalities" :key="municipality.municipalityName">
          <h2 class="value">{{municipality.value}}{{municipality.valueSuffix}}</h2>
          <p v-if="municipality.subValue">{{municipality.subValue}}{{municipality.subValueSuffix}}</p>
          <div class="municipality">{{municipality.municipalityName}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .cards {
    display: flex;
    width: 100%;
    gap: 10px;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .card {
    border: 1px solid var(--light-primary);
    border-radius: 4px;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
  }

  .card h3 {
    font-size: 1.2rem;
    font-weight: 500;
  }

  .title {
    background-color: var(--dark-secondary);
    padding: 13px 20px;
    border-bottom: 1px solid var(--light-primary);
    min-height: 36px;
  }

  .municipalities {
    display: flex;
    gap: 40px;
    margin: 13px 20px;
  }

  .value {
    font-weight: 700;
  }

  .center {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
  }

  /* Spinner styles */
  .loader {
    transform: rotateZ(45deg);
    perspective: 1000px;
    border-radius: 50%;
    width: 48px;
    height: 48px;
    color: var(--button-important);
  }

  .loader:before,
  .loader:after {
    content: '';
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    width: inherit;
    height: inherit;
    border-radius: 50%;
    transform: rotateX(70deg);
    animation: 1s spin linear infinite;
  }

  .loader:after {
    color: var(--button);
    transform: rotateY(70deg);
    animation-delay: 0.4s;
  }

  @keyframes rotate {
    0% {
      transform: translate(-50%, -50%) rotateZ(0deg);
    }
    100% {
      transform: translate(-50%, -50%) rotateZ(360deg);
    }
  }

  @keyframes rotateccw {
    0% {
      transform: translate(-50%, -50%) rotate(0deg);
    }
    100% {
      transform: translate(-50%, -50%) rotate(-360deg);
    }
  }

  @keyframes spin {
    0%,
    100% {
      box-shadow: 0.2em 0px 0 0px currentcolor;
    }
    12% {
      box-shadow: 0.2em 0.2em 0 0 currentcolor;
    }
    25% {
      box-shadow: 0 0.2em 0 0px currentcolor;
    }
    37% {
      box-shadow: -0.2em 0.2em 0 0 currentcolor;
    }
    50% {
      box-shadow: -0.2em 0 0 0 currentcolor;
    }
    62% {
      box-shadow: -0.2em -0.2em 0 0 currentcolor;
    }
    75% {
      box-shadow: 0px -0.2em 0 0 currentcolor;
    }
    87% {
      box-shadow: 0.2em -0.2em 0 0 currentcolor;
    }
  }
</style>

