<script setup lang="ts">

import Footer from "@/pages/footer/Footer.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
import AccountList from "@/pages/admin/components/AccountList.vue";
</script>

<template>
  <Navbar></Navbar>
  <section>
    <h1>Beheerder portaal</h1>
    <AccountList></AccountList>
  </section>
  <Footer></Footer>
</template>

<style scoped>

</style>