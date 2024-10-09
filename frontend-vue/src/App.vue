<script>
import Navbar from "./components/Navbar.vue";
import Hero from "@/components/Hero.vue";
import Footer from "@/components/Footer.vue";
import "@/assets/main.css";
import "@/assets/reset.css";
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";

export default {
  name: "App",
  components: {
    Navbar,
    Hero,
    Footer,
    Login,
    Register
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
  <router-view></router-view>
</template>

<style scoped>
</style>