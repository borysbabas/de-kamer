<script>
import Navbar from "./components/Navbar.vue"
import Hero from "@/components/Hero.vue";
import "@/assets/main.css";

export default {
  name: "App",
  components: {
    Navbar: Navbar,
    Hero: Hero
  },
  data() {
    return {
      isClicked: false,
    }
  },
  methods: {
    receiveEmit(status) {
      console.log("received emit", status)
      this.isClicked = status

    },
    getData(data) {
      console.log(data)

    }
  }
};
</script>
<template>
  <Navbar @toggle-state="receiveEmit"></Navbar>
  <Hero v-if="!isClicked"></Hero>
</template>
