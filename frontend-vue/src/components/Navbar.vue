<script setup lang="ts">
import {defineEmits, ref} from "vue"
import Login from "./Login.vue"
import Register from "@/components/Register.vue";
import Hero from  "@/components/Hero.vue";
import Searchbar from "@/components/Searchbar.vue";

const state = ref(null)
const emit = defineEmits(["toggle-state"])
const home =()=>{
  state.value= Hero
  if(state.value){
  emit("toggle-state", true)
  }
}
const login = () => {
  state.value = Login
  if (state.value) {
    emit("toggle-state", true)
  } else {
    console.log("error")
  }
}
const register = () => {
  state.value = Register
  if (state.value) {
    emit("toggle-state", true)
  } else {
    console.log("error")
  }
}
const searchBarState=ref(false)
const toggleSearchBar=()=>{
  searchBarState.value = !searchBarState.value;
}


</script>
<template>
  <div class="nav">
    <ul>
      <li v-on:click="home" class="Home"><a>DeKamer.</a></li>
      <li class="buttons">
        <Searchbar v-if="searchBarState" class="searchbar"></Searchbar>
        <li v-on:click="login"><a>Login</a></li>
        <li v-on:click="register"><a>Register</a></li>
        <svg v-on:click="toggleSearchBar" xmlns="http://www.w3.org/2000/svg" class="magnifying-glass-icon"
             viewBox="0 0 512 512">
          <path
              d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
        </svg>
      </li>
    </ul>
  </div>
  <component :is="state"></component>

</template>

<style>

.nav {
  background-color: var(--button-important);
  position: fixed;
  top: 50px;
  left: 50px;
  right: 50px;
  height: 50px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-family: var(--font), sans-serif;
  z-index: 1;
}
.searchbar{
  display: flex;
  justify-content: left;
}

.Home {
  font-size: 3rem;
}

ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

li {
  margin: 0 20px;
  padding: 10px 15px;
}

.buttons {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 1.5rem;
}

.magnifying-glass-icon {
  fill: white;
  width: 2rem;
  cursor: pointer;
}

a {
  cursor: pointer;
  text-decoration: none;
  color: white;
  font-weight: normal;
}


@media (max-width: 768px) {
  ul {
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
  }

  li {
    margin: 10px 0;
  }
}


</style>