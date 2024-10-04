<script setup lang="ts">


</script>

<template>
  <li class="searchbar-container"><input placeholder="Search..."></li>
  <ul class="search-results">
    <li>PVV</li>
    <li>D66</li>
    <li>VVD</li>
    <li>NSC</li>
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
  left:25%;
  width: 620px;
  max-height:200px;
  display: flex;
  flex-direction: column;
  background-color:white;
  color:black;
}



</style>