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


console.log(municipalityList.value);

</script>

<template>
  <div class="cards">
    <div class="card" v-for="municipality in municipalityList" :key="municipality">
      <h3>{{municipality.name}}</h3>
      <hr>
      <div class="data">
        <div>Totaal aantal inwoners</div>
        <div>{{municipality.additionalData[0].BevolkingOp1Januari_1}}</div>
        <div>Meest gestemde partij</div>
        <div>{{ municipality.affiliations[0].affiliationName }} ({{municipality.affiliations[0].totalVotes}})</div>
        <div>Meest gestemde kandidaat</div>
        <div>{{ municipality.topCandidate.firstName + " " + municipality.topCandidate.lastName }} ({{municipality.topCandidate.votes}})</div>
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
    max-width: 300px;
    width: 100%;
    background-color: var(--light-primary);
    color: #000;
    padding: 10px;
    border-radius: 4px;
  }

  .card h3 {
    font-size: 1.2rem;
    font-weight: 500;
  }

  .data {
    display: grid;
    grid-template-columns: 1fr 2fr;
    gap: 10px;
  }

  .cards {
    margin: 20px 0;
  }
</style>