<script setup lang="ts">
import {onMounted, onUnmounted, provide, ref} from "vue";
import Searchbar from "@/components/Searchbar.vue";
const userEmail = ref("")
provide("updateEmail",(email:string)=>{
  userEmail.value=email
})
const searchBarState = ref(false);

const toggleSearchBar = () => {
  searchBarState.value = !searchBarState.value;
};

const handleScroll = () => {
  const navbar = document.getElementById("navbar");
  if (window.scrollY > 50) {
    navbar?.classList.add("fixed-top");
  } else {
    navbar?.classList.remove("fixed-top");
  }
};
onMounted(()=>{
  const searchDOM=document.getElementById("searchbar");
  if(searchDOM){
    searchDOM.addEventListener("focusout",()=>this.searchBarState.value=false)
  }
  else{
    console.log("error")
  }
})
onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});

const navbar = ref({
  margin: '50px',
})

</script>

<template>
  <div id="navbar" class="nav">
    <ul>
      <li class="navbar-logo"><router-link to="/">DeKamer.</router-link></li>
      <li class="buttons">
        <Searchbar v-if="searchBarState" id="searchbar" class="searchbar"></Searchbar>
        <router-link to="/results"><p class="nav-text">Uitslagen</p></router-link>
        <router-link to="/information"><p class="nav-text">Info</p></router-link>
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
  width: calc(100vw - (var(--page-padding) * 2));
  margin: v-bind(navbar.margin) var(--page-padding);
  box-sizing: border-box;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-family: var(--font), sans-serif;
  z-index: 1;
  height: 50px;
  transition-duration: 0.3s;
}

.fixed-top{
  width: 100%;
  margin: 0;
  padding: 0 var(--page-padding);
  border-radius: 0;
  height: 70px;
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
  gap: 20px;
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