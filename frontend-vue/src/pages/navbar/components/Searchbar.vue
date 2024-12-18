<script setup lang="ts">
import {computed, onMounted, onUnmounted, ref} from "vue"
import {useRouter} from "vue-router"

const router = useRouter()
const isFixed = ref(false)
const input = ref("");
const debouncedSearch = debounce((value) => {
  input.value = value;
}, 350)
const scrollBarWidth = ref<string>("0px");

const candidateModel = ref({
  candidateId: "",
  firstname: "",
  prefix: "",
  lastname: "",
  affiliationName: "",
})

const candidateList = ref([]);

onMounted(() => {
  fetch(import.meta.env.VITE_API_URL + "/candidates")
      .then(response => response.json())
      .then(json => candidateList.value = json);
  scrollBarWidth.value = getScrollbarWidth();
})

const filteredList = computed(() => {
  const searchQuery = input.value.toLowerCase();

  return candidateList.value.filter(candidate => {
    const fullName = [candidate.firstName, candidate.prefix, candidate.lastName]
        .join(" ")
        .toLowerCase()
        .trim()
        .replace(/\s+/g, " ")

    const affiliationName = candidate.affiliationName.toLowerCase().trim().replace(/\s+/g, " ");
    return fullName.includes(searchQuery) || affiliationName.includes(searchQuery);
  });
})

function debounce(func, delay) {
  let timeout;
  return function (...args) {
    clearTimeout(timeout)
    timeout = setTimeout(() => func.apply(this, args), delay)
  }
}

function routeAssigner(candidate) {
  let candidateId = candidate.candidateId
  let firstName = candidate.firstName
  let prefix = candidate.prefix
  let lastName = candidate.lastName
  let affiliationName = candidate.affiliationName

  candidateModel.value = {
    candidateId: candidateId,
    firstname: firstName,
    prefix: prefix,
    lastname: lastName,
    affiliationName: affiliationName
  }
  router.push({name: "CandidateDetails", params: {candidateId: candidate.candidateId}})
}
const handleScroll = () => {
  if (window.scrollY > 50) {
    isFixed.value = true;
  } else {
    isFixed.value = false;
  }
};

function clearSearch() {
  input.value = "";
  (document.querySelector(".searchbar-container input") as HTMLInputElement).value = "";
}

/**
 * Get the width of the scrollbar
 * @returns The width of the scrollbar
 * @source https://stackoverflow.com/questions/13382516/getting-scroll-bar-width-using-javascript
 */
function getScrollbarWidth(): string {
  // Creating invisible container
  const outer = document.createElement('div');
  outer.style.visibility = 'hidden';
  outer.style.overflow = 'scroll'; // forcing scrollbar to appear
  outer.style.msOverflowStyle = 'scrollbar'; // needed for WinJS apps
  document.body.appendChild(outer);

  // Creating inner element and placing it in the container
  const inner = document.createElement('div');
  outer.appendChild(inner);

  // Calculating difference between container's full width and the child width
  const scrollbarWidth = (outer.offsetWidth - inner.offsetWidth);

  // Removing temporary elements from the DOM
  outer.parentNode.removeChild(outer);

  return `${scrollbarWidth}px`;
}

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});
</script>

<template>
  <div class="searchbar-container">
    <input @input="event=>debouncedSearch(event.target.value)" type="text" placeholder="Zoeken...">
    <img @click="clearSearch" src="@/assets/svg/red-cross.svg" alt="red cross" />
  </div>
  <ul v-if="input.length > 0 && filteredList.length > 0">
    <li v-for="candidate in filteredList.splice(0, 50)" :key="candidate.candidateId" @click="routeAssigner(candidate)">
      {{ candidate.firstName }}
      <span v-if="candidate.prefix">{{ candidate.prefix }}</span>
      {{ candidate.lastName }} - {{ candidate.affiliationName }}
    </li>
  </ul>
</template>

<style scoped>

.searchbar-container {
  max-height: 30px;
  height: 100%;
  position: relative;
}

.searchbar-container img {
  position: absolute;
  right: 0;
  top: 50%;
  width: 17px;
  padding: 5px;
  transform: translateY(-50%);
  cursor: pointer;
}

.searchbar-container input {
  width: 100%;
  height: 100%;
  border-radius: 4px;
  border: none;
  box-sizing: border-box;
  padding: 0 30px 0 20px;
  font-size: 1.05rem;
  background-color: var(--light-primary);
}

ul {
  position: absolute;
  display: flex;
  flex-direction: column;
  top: 56px;
  padding: 5px 0;
  box-sizing: border-box;
  background-color: var(--light-primary);
  gap: 5px;
  max-height: 50vh;
  overflow: auto;
  box-shadow: 0 0 10px #00000040;
}

ul li {
  color: var(--dark-primary);
  padding: 7px 20px;
  transition-duration: 100ms;
  font-size: 1.1rem;
}

ul li:hover {
  background-color: var(--dark-primary);
  color: var(--light-primary);
  cursor: pointer;
  border-radius: 4px;
}

@media only screen and (max-width: 480px) {
  ul {
    left: 0;
    width: calc(100vw - var(--page-side-padding) * 2 - v-bind(scrollBarWidth));
    box-sizing: border-box;
  }
}
</style>