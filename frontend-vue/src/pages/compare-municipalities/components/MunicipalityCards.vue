<script setup lang="ts">
import type {Municipality} from "@/types/Municipality";
import type {PropType} from "vue";
import { useAdditionalMunicipalitiesData} from "@/composables/useMunicipalities";

const props = defineProps({
  municipalities: {
    type: Object as PropType<Municipality[]>,
    required: true
  }
})

const {municipalityList} = useAdditionalMunicipalitiesData(props.municipalities);

function formatNumber(value: string | number) {
  let numberValue = value;
  if (typeof value === "string") {
    numberValue = parseInt(value);
  }
  return numberValue.toLocaleString("nl-NL");
}

</script>

<template>
  <div class="cards">
    <div class="card">
      <div class="title">
        <h3>Aantal inwoners</h3>
      </div>
      <div class="municipalities">
        <div v-for="municipality in municipalityList" :key="municipality">
          <h2 class="value">{{formatNumber(municipality.additionalData[0].BevolkingOp1Januari_1)}}</h2>
          <div class="municipality">{{municipality.name}}</div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="title">
        <h3>Meest gestemde partijen</h3>
      </div>
      <div class="municipalities">
        <div v-for="municipality in municipalityList" :key="municipality">
          <h2 class="value">{{municipality.affiliations[0].affiliationName}}</h2>
          <p>{{formatNumber(municipality.affiliations[0].totalVotes)}} stemmen</p>
          <div class="municipality">{{municipality.name}}</div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="title">
        <h3>Meest gestemde kandidaten</h3>
      </div>
      <div class="municipalities">
        <div v-for="municipality in municipalityList" :key="municipality">
          <h2 class="value">{{municipality.topCandidate.name}}</h2>
          <p>{{formatNumber(municipality.topCandidate.votes)}} stemmen  </p>
          <div class="municipality">{{municipality.name}}</div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="title">
        <h3>Opkomst percentage</h3>
      </div>
      <div class="municipalities">
        <div v-for="municipality in municipalityList" :key="municipality">
          <h2 class="value">{{formatNumber(municipality.additionalData[0].BevolkingOp1Januari_1)}}</h2>
          <div class="municipality">{{municipality.name}}</div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="title">
        <h3>Aantal gehaalde zetels</h3>
      </div>
      <div class="municipalities">
        <div v-for="municipality in municipalityList" :key="municipality">
          <h2 class="value">{{formatNumber(municipality.additionalData[0].BevolkingOp1Januari_1)}}</h2>
          <div class="municipality">{{municipality.name}}</div>
        </div>
      </div>
    </div>

    <div class="card">
      <div class="title">
        <h3>Gemiddelde leeftijd</h3>
        <p>in jaren</p>
      </div>
      <div class="municipalities">
        <div v-for="municipality in municipalityList" :key="municipality">
          <h2 class="value">{{formatNumber(municipality.additionalData[0].BevolkingOp1Januari_1)}}</h2>
          <div class="municipality">{{municipality.name}}</div>
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
    flex-grow: 1;
  }

  .municipalities {
    display: flex;
    gap: 40px;
    margin: 13px 20px;
  }

  .value {
    font-weight: 700;
  }

</style>