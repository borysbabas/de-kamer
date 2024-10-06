<script setup lang="ts">
import {defineEmits, onMounted, onUnmounted, ref} from "vue"
import Login from "./Login.vue"
import Register from "@/components/Register.vue"
import Hero from "@/components/Hero.vue"
import Searchbar from "@/components/Searchbar.vue"

type ComponentType = typeof Hero | typeof Login | typeof Register;

const state = ref<ComponentType>(Hero)
const emit = defineEmits(["toggle-state"])

const home = () => {
  state.value = Hero
  if (state.value) {
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

const searchBarState = ref(false)
const toggleSearchBar = () => {
  searchBarState.value = !searchBarState.value
}

const handleScroll = () => {
  const navbar = document.getElementById("navbar")
  if (window.scrollY > 50) {
    navbar?.classList.add("fixed-top")
  } else {
    navbar?.classList.remove("fixed-top")
  }
}

onMounted(() => {
  window.addEventListener("scroll", handleScroll)
})

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll)
})
</script>

<template>
  <div id="navbar" class="nav">
    <ul>
      <li @click="home" class="Home">DeKamer.</li>
      <li class="buttons">
        <Searchbar v-if="searchBarState" class="searchbar"></Searchbar>
        <li @click="login"><a class="nav-text">Login</a></li>
        <li @click="register"><a class="nav-text">Register</a></li>
        <svg @click="toggleSearchBar" xmlns="http://www.w3.org/2000/svg" class="magnifying-glass-icon"
             viewBox="0 0 512 512">
          <path
              d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
        </svg>
      </li>
    </ul>
  </div>
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
  transition: top 0.3s;
}

.fixed-top {
  top: 0;
  left: 0;
  right: 0;
  border-radius: 0;
}

.searchbar {
  display: flex;
  justify-content: left;
}

.Home {
  font-size: 36px;
  cursor: pointer;
}

.nav-text {
  font-size: 20px;
}

.nav-text::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1px;
  background-color: var(--light-primary);
  width: 0;
  transition: width var(--animation-duration);
}

.nav-text:hover::after {
  width: 100%;
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

.magnifying-glass-icon:hover {
  fill: var(--dark-primary);
  animation: rotate-shake 0.5s;
}

@keyframes rotate-shake {
  0% {
    transform: rotate(0deg) translateX(0);
  }
  25% {
    transform: rotate(2deg) translateX(-2px);
  }
  50% {
    transform: rotate(-2deg) translateX(2px);
  }
  75% {
    transform: rotate(2deg) translateX(-2px);
  }
  100% {
    transform: rotate(0deg) translateX(0);
  }
}
</style>