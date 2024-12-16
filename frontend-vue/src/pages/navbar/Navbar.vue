<script setup lang="ts">
import {inject, onMounted, onUnmounted, ref} from "vue";
import {useRoute} from "vue-router";
import Searchbar from "@/pages/navbar/components/Searchbar.vue";

//navbar status
const navbarMargin = ref("50px");
const route = useRoute();
const isForumPage = ref(route.path.startsWith("/forum"));
const searchBarState = ref(false);
const toggleSearchBar = () => {
  searchBarState.value = !searchBarState.value;
};

//services
const sessionService = inject("sessionService")
const userName =ref(sessionService.getUsernameFormBrowserStorage())
const Authenticated= ref(sessionService.getTokenFromBrowserStorage())

function handleSignOut(){
  sessionService.clearToken()
  sessionService.clearUserName()
  window.location.reload()
}

/**
 * Handles the scroll event
 * If the user scrolls down, the navbar will be fixed to the top
 */
const handleScroll = () => {
  const navbar = document.getElementById("navbar");
  if (window.scrollY > 50) {
    navbar?.classList.add("fixed-top");
  } else {
    navbar?.classList.remove("fixed-top");
  }
};

onMounted(() => {
  const searchDOM = document.getElementById("searchbar");
  if (searchDOM) {
    searchDOM.addEventListener("focusout", () => this.searchBarState.value = false);
  } else {
    console.log("error");
  }
});

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});

</script>

<template>
  <div id="navbar" class="nav">
    <div>
      <ul>
        <li class="navbar-logo">
          <router-link to="/">DeKamer.</router-link>
        </li>
        <li class="buttons">
          <Searchbar v-if="searchBarState" id="searchbar" class="searchbar"></Searchbar>
          <template v-if="isForumPage">
            <router-link v-if="!Authenticated" to="/forum/login"><p class="nav-text clickable">Login</p></router-link>
            <router-link v-if="!Authenticated" to="/forum/register"><p class="nav-text clickable">Register</p></router-link>
            <div v-if="Authenticated"><p class="nav-text"> Welcome, {{ userName }}</p></div>
            <div v-if="Authenticated"><p @click="handleSignOut" class="nav-text clickable">Sign out</p></div>
          </template>
          <template v-else>
            <router-link to="/uitslagen"><p class="nav-text clickable">Uitslagen</p></router-link>
            <router-link to="/info"><p class="nav-text clickable">Info</p></router-link>
            <router-link to="/forum"><p class="nav-text clickable">Forum</p></router-link>
          </template>
          <svg @click="toggleSearchBar" xmlns="http://www.w3.org/2000/svg" class="magnifying-glass-icon"
               viewBox="0 0 512 512">
            <path
                d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
          </svg>
        </li>
      </ul>
    </div>
  </div>
</template>

<style>
.nav {
  position: fixed;
  width: 100%;
  padding: 0 var(--page-padding);
  margin: v-bind(navbarMargin) 0;
  box-sizing: border-box;
  font-family: var(--font), sans-serif;
  z-index: 1;
  transition-duration: 0.3s;
}

.nav > div {
  background-color: var(--button-important);
  border-radius: 10px;
}

.fixed-top {
  width: 100%;
  margin: 0;
  padding: 0;
}

.fixed-top > div {
  padding: 8px var(--page-padding);
  border-radius: 0;
}

.fixed-top li {
  margin: 0;
  padding: 0;
}

.searchbar {
  display: flex;
  justify-content: left;
}

.navbar-logo * {
  font-size: 36px;
  cursor: pointer;
}

.nav-text {
  font-size: 20px;
  cursor: pointer;
}

.nav-text.clickable::after {
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

.nav-text.clickable:hover::after {
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
  gap: 20px;
}

.buttons > div {
  position: relative;
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