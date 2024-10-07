<script>
import Navbar from "./components/Navbar.vue"
import Hero from "@/components/Hero.vue";
import Footer from "@/components/Footer.vue";
import "@/assets/main.css";
import "@/assets/reset.css";

export default {
  name: "App",
  components: {
    Navbar: Navbar,
    Hero: Hero,
    Footer: Footer
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

    }
  }
};
</script>
<template>
  <Navbar @toggle-state="receiveEmit"></Navbar>
  <Hero v-if="!isClicked"></Hero>
  <Footer @toggle-state="receiveEmit"></Footer>
</template>
<style>
body{
  background-color: var(--dark-primary);

}

</style>
