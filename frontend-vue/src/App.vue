<script>
import Navbar from "./components/Navbar.vue";
import Hero from "@/components/Hero.vue";
import Footer from "@/components/Footer.vue";
import "@/assets/main.css";
import "@/assets/reset.css";
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";

import ChartTemplate from "@/components/tools/Chart-Template.vue";

export default {
  name: "App",
  components: {
    Navbar,
    Hero,
    Footer,
    Login,
    Register,
    ChartTemplate,
  },
  data() {
    return {
      isClicked: "", // Use a string to represent the current state
    };
  },
  methods: {
    receiveEmit(status) {
      console.log("received emit", status);
      this.isClicked = status;
    }
  }
};
</script>

<template>
  <Navbar @toggle-state="receiveEmit"></Navbar>
  <Hero v-if="isClicked === ''"></Hero>
  <Login v-if="isClicked === 'login'"></Login>
  <Register v-if="isClicked === 'register'"></Register>
  <Footer @toggle-state="receiveEmit"></Footer>
  <ChartTemplate chartType="bar" :chartData="chartData" />c
</template>

<style scoped>
</style>