<script setup lang="ts">
import { ref} from "vue"
import{inject} from "vue";


const sessionService: any = inject("sessionService")
const buttonText=ref("Log in");
const buttonColor=ref("")

const loginCredentials = ref({
  email: "",
  password: "",
})

const submitLogin = async () => {
  if(!loginCredentials.value.email || !loginCredentials.value.password){
    return
  }
  if (!sessionService){
    console.error("session service error")
    return
  }
  const succes = await sessionService.asyncSignIn(
      loginCredentials.value.email,
      loginCredentials.value.password
  );
  if (succes){
    buttonText.value="Succesfully logged in!"
    buttonColor.value="green"
  } else {
    buttonText.value="Login failed"
    buttonColor.value="red"
  }

}
</script>

<template>
  <section class="login">
    <form @submit.prevent>
      <div class="wrapper">
        <h2>Enter your credentials</h2>
        <input required v-model="loginCredentials.email" placeholder="E-mail" type="email" autocomplete="email">
        <input required v-model="loginCredentials.password" placeholder="Password" type="password" autocomplete="current-password">
        <button id="submit-btn" :style="{backgroundColor: buttonColor}" @click="submitLogin">{{buttonText}}</button>
        <router-link to="/forum/registreer"><a>Create account</a></router-link>
      </div>
    </form>
  </section>
</template>

<style scoped>

</style>