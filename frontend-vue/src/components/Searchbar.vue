<script setup lang="ts">
import {ref,computed} from "vue"

const input =ref("");
const partyList=[{name: "VVD", votes:1980},{name:"PVV",votes:1678},{name:"D66",votes: 1900}]
const filteredList= computed(()=>{
  return partyList.filter((party)=>party.name.toLowerCase().includes(input.value.toLowerCase()));
})
</script>

<template>
  <li class="searchbar-container"><input v-model="input" type="text" placeholder="Search..."></li>
  <ul class="search-results" >
    <li v-for="party in filteredList" :key="party.name">{{ party.name}} - {{party.votes}}</li>



  </ul>
</template>

<style scoped>
li{
  font-size:0.95rem;
}
input{
  border-style:none;
  width:600px;
  height:20px;
  border-radius:5px;
  padding:5px 10px;
  font-size:0.9rem;
}
.searchbar-container{
  position: relative;
  margin-right:100px;
}

.search-results {
  position: absolute;
  top: 100%;
  left:27.9%;
  width: 620px;
  max-height:200px;
  display: flex;
  flex-direction: column;
  background-color:white;
  color:black;
}
@media(min-height:470px){
  .search-results{
    left:28.6%;
  }
}



</style>