<script setup lang="ts">
import {ref, computed, onMounted, onUnmounted} from "vue"
const isFixed=ref(false)
const input =ref("");
const candidateList=ref([])
onMounted(() => {
  fetch("http://localhost:3001/candidates")
      .then(response => response.json())
      .then(json => candidateList.value = json)
})


const filteredList = computed(() => {
  const searchQuery = input.value.toLowerCase();

  return candidateList.value.filter(candidate => {
    const fullName = [candidate.firstName, candidate.prefix, candidate.lastName]
        .join(" ")
        .toLowerCase()
        .trim();

    const affiliationName = candidate.affiliationName.toLowerCase();
    return fullName.includes(searchQuery) || affiliationName.includes(searchQuery);
  });
})
//gets value of clicked LI item and redirects user to designated endpoint.
function routeAssigner(event){
const clickedLi = event.target.innerHTML;
let reqValue = clickedLi.split("-")[0]

}
const handleScroll = () => {
  if(window.scrollY > 50){
    isFixed.value=true;
  }
  else{
    isFixed.value=false;
  }
};

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});

</script>

<template>
  <li class="searchbar-container"><input v-model="input" type="text" placeholder="Search..."></li>
  <ul v-if="input.length > 0 && filteredList.length > 0" :class="{'fixed-searchbar' : isFixed, 'search-results': !isFixed}">
    <li v-on:click="routeAssigner" v-for="candidate in filteredList" :key="candidate.candidateId">{{ candidate.firstName }}
      <span v-if="candidate.prefix">{{candidate.prefix}}</span>
      {{candidate.lastName}}- {{candidate.affiliationName}}</li>
  </ul>
</template>

<style scoped>
.fixed-searchbar{
  top:60px;
  right:35.7vw;
  width:600px;
  background-color:white;
  color:black;
  position: absolute;
  flex-direction:column;


}
.searchbar-container{
  display:flex;
  justify-content: flex-start;
  align-items: start;
  text-align:left;
  right:15vw;
  width:600px;
  position:relative;

}
.search-results{
  right:32.1vw;
  width:600px;
  background-color:white;
  color:black;
  position: absolute;
  top:50px;
  flex-direction:column;
}
input {
  border-style: none;
  width: 600px;
  height: 20px;
  border-radius: 5px;
  padding: 5px 10px;
  font-size: 0.9rem;
}
.fixed-searchbar li{
  width:568px;
  font-size: 1rem;
}
li{
  width:568px;
  font-size: 1rem;
}

.search-results li {
  border:solid white 0.5px;
  cursor:pointer;

}
.search-results li:hover{
  border-color: #20638F;
}
.fixed-searchbar li{
  border:solid white 0.5px;
  cursor:pointer;
  padding: 10px 15px;
  margin: 0 20px;

}
.fixed-searchbar li:hover{
  border-color: #20638F
}




</style>