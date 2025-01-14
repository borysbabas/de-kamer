<script>
import "@/assets/main.css";
import "@/assets/reset.css";
import {shallowReactive} from "vue";
import SessionSbService from "/UiServices/SessionSbService.ts"
import {FetchInterceptor} from "/UiServices/FetchInterceptor.ts";
export default {

  name: "App",
  provide(){
    const api = import.meta.env.VITE_API_URL;
    const sessionService = shallowReactive(
        new SessionSbService(api+"/authentication", "JWT_STORAGE_ITEM")
    );
    new FetchInterceptor(sessionService)
    return{
      sessionService
    }
  }
};
</script>

<template>
  <router-view></router-view>
</template>

<style scoped>
</style>
