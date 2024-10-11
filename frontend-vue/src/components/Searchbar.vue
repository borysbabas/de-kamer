<script setup lang="ts">
import {ref, computed, onMounted} from "vue"

const input =ref("");
const partyList=ref([])
onMounted(()=>fetch("https://jsonplaceholder.typicode.com/users").then(response=>response.json().then(json=> partyList.value=json)))
const filteredList= computed(()=>{
  return partyList.value.filter((party)=>party.name.toLowerCase().includes(input.value.toLowerCase()));
})
</script>

<template>
  <li class="searchbar-container"><input v-model="input" type="text" placeholder="Search..."></li>
  <ul v-if="input.length > 0 && filteredList.length > 0" class="search-results" >
    <li v-for="party in filteredList" :key="party.name">{{ party.name}} - {{party.votes}}</li>
  </ul>
</template>

<style scoped>
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
  right:31.9vw;
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
li{
  width:568px;
  font-size: 1rem;
}

.search-results li {
  border:solid white 0.5px;
}
.search-results li:hover{
  border-color: #20638F;
}




</style>