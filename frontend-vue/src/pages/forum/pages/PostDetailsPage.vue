<script setup lang="ts">
import PostDetails from "@/pages/forum/components/post-details/PostDetails.vue";
import {useRoute} from "vue-router";
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
import {usePostDetails} from "@/composables/usePostDetails";

const route = useRoute();

const {isLoading, loadedPost} = usePostDetails(route.params.postId);

console.log({isLoading, loadedPost})

</script>

<template>
  <Navbar />
  <section>
    <PostDetails />
  </section>
  <Footer />
</template>

<style scoped>

</style>