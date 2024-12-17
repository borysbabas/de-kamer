<script setup lang="ts">
import {ref} from "vue"
import {inject} from "vue";

const sessionService = inject("sessionService")

async function submitRegisterForm(){
  if(!formData.value.email || !formData.value.username || !formData.value.dateOfBirth|| !formData.value.password){
    return
  }

  const response = await sessionService.asyncSignUp(formData.value)
  if(response){
    let btn = document.getElementById("submit-btn")
    btn.style.backgroundColor="green"
    btn.innerHTML = "succesfully registered!"

  }else{
    let btn = document.getElementById("submit-btn")
    btn.style.backgroundColor="red"
    btn.innerHTML = "Something went wrong..."
  }

}

const formData = ref({
  email: "",
  username: "",
  dateOfBirth: "",
  password: "",
})

</script>

<template>
  <section class="login">
    <form @submit.prevent>
      <div class="wrapper">
        <h2>Enter your information</h2>
        <input required v-model="formData.email" placeholder="E-mail">
        <input required v-model="formData.username" placeholder="Username">
        <input required v-model="formData.dateOfBirth" type="date" placeholder="date of birth.">
        <input required v-model="formData.password" type="password" placeholder="Password">
        <button id="submit-btn" type="submit" @click="submitRegisterForm">Submit</button>
        <router-link to="/forum/login"><a>Login</a></router-link>
      </div>
    </form>
  </section>
</template>

<style scoped>

</style>