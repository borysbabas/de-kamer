<script setup lang="ts">
import {inject, ref} from "vue";

const sessionService: any = inject("sessionService");

async function submitRegisterForm() {
  if (!formData.value.email || !formData.value.userName || !formData.value.password) {
    return
  }

  const response = await sessionService.asyncSignUp(formData.value)
  let btn = document.getElementById("submit-btn") as HTMLButtonElement;
  if (response) {
    btn.style.backgroundColor = "green";
    btn.innerHTML = "succesfully registered!";
    void sessionService.asyncSignIn(formData.value.email, formData.value.password)
  } else {
    btn.style.backgroundColor = "red";
    btn.innerHTML = "Something went wrong...";
  }

}

const formData = ref({
  email: "",
  userName: "",
  dateOfBirth: "",
  password: "",
})

</script>

<template>
  <section class="login">
    <form @submit.prevent>
      <div class="wrapper">
        <h2>Enter your information</h2>
        <input required v-model="formData.email" type="email" placeholder="E-mail">
        <input required v-model="formData.userName" placeholder="Username">
        <input v-model="formData.dateOfBirth" type="date" placeholder="date of birth.">
        <input required v-model="formData.password" type="password" placeholder="Password">
        <button id="submit-btn" type="submit" @click="submitRegisterForm">Submit</button>
        <router-link to="/forum/log-in"><a>Login</a></router-link>
      </div>
    </form>
  </section>
</template>

<style scoped>

</style>