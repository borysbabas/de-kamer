<script setup lang="ts">

import Footer from "@/pages/footer/Footer.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
import AccountList from "@/pages/admin/components/AccountList.vue";
import {useAccount} from "@/composables/useAccount";
import {useRouter} from "vue-router";

const router = useRouter();

const {success} = useAccount();

</script>

<template>
  <Navbar></Navbar>
  <section v-if="success">
    <h1>Beheerder portaal</h1>
    <AccountList></AccountList>
  </section>
  <section v-else>
    <h1>Je hebt geen toegang tot deze pagina</h1>
    <button @click="router.push('/forum/login')">Log in</button>
  </section>
  <Footer></Footer>
</template>

<style scoped>

</style>