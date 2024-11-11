<template>
  <Navbar></Navbar>
  <section class=" main">
    <div class="container">
      <div class="wrapper">
        <H1>{{ fetchedData.firstName }} <span v-if="fetchedData.prefix"> {{ fetchedData.prefix }}</span>
          {{ fetchedData.lastName }}</H1>
        <H2>Partij: {{ fetchedData.affiliationName }}</H2>
        <h2>Aantal stemmen: {{ fetchedData.votes }}</h2>
      </div>
    </div>
  </section>
    <Footer></Footer>
</template>
<script setup lang="ts">
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue"
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
const candidateId= ref(null)
const fetchedData = ref({

  candidateId: "",
    firstName: "",
    prefix: "",
    lastName: "",
    affiliationName: "",
  votes: ""
})

async function fetchCandidate(){
  candidateId.value=route.params.candidateId
  console.log(candidateId.value)
  if(candidateId.value) {
    await fetch(`http://localhost:3001/candidates/${candidateId.value}`).then(response => response.json().then(json => fetchedData.value = json))
  }
  else{
    console.log("error")
  }
}

onMounted(  ()=>{
  fetchCandidate()
})

watch(()=>route.params.candidateId,(changedCandidateId)=>{
  candidateId.value = changedCandidateId;
  fetchCandidate()
})
</script>

<style scoped>
.main {
  min-height: calc(100vh - 56px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  z-index: 0;
}

.container{
  padding-top: 160px;
}
</style>