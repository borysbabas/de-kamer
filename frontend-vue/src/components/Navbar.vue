<script setup lang="ts">
import {defineEmits, ref} from "vue"
import Login from "./Login.vue"
import Register from "@/components/Register.vue";

const state = ref()
const emit = defineEmits(["toggle-state"])
let loginStatus = ref(false)

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
</script>

<template>
  <div class="nav">
    <ul>
      <li><a class="no-animation home">DeKamer.</a></li>
      <div class="right-container">
        <li v-on:click="login"><a>Login</a></li>
        <li v-on:click="register"><a>Register</a></li>
        <li>
          <svg xmlns="http://www.w3.org/2000/svg" class="magnifying-glass-icon"
               viewBox="0 0 512 512">
            <path
                d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
          </svg>
        </li>
      </div>
    </ul>
  </div>
  <component :is="state"></component>
</template>

<style>
:root {
  --animation-duration: 0.3s; /* Define the animation duration variable */
}

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

.right-container {
  display: flex;
  align-items: center;
  margin-left: auto;
}

.magnifying-glass-icon {
  fill: white;
  width: 2rem;
  cursor: pointer;
  transition-duration: var(--animation-duration);
}

.magnifying-glass-icon:hover {
  fill: var(--dark-primary);
  animation: rotate-shake 0.5s;
}

.home {
  font-size: 36px;
}

a {
  cursor: pointer;
  position: relative;
}

a::after {
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

a:hover::after {
  width: 100%;
}

a:hover {
  color: var(--light-primary);
}

a.no-animation::after,
a.no-animation:hover::after {
  width: 0;
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

.magnifying-glass-icon:hover {
  fill: var(--dark-primary);
  animation: rotate-shake 0.5s;
}
</style>