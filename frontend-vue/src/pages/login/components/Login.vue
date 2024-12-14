<script setup lang="ts">
import { ref} from "vue"
import{inject} from "vue";


const sessionService = inject("sessionService")
const buttonText=ref("Log in");
const buttonColor=ref("")


const loginCredentials = ref({
  email: "",
  password: "",
})

const submitLogin = async () => {
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
    window.location.href="/forum"
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
        <input v-model="loginCredentials.email" placeholder="E-mail">
        <input v-model="loginCredentials.password" placeholder="Password" type="password">
        <button id="submit-btn" :style="{backgroundColor: buttonColor}" @click="submitLogin">{{buttonText}}</button>
        <router-link to="/forum/register"><a>Create account</a></router-link>
      </div>
    </form>
  </section>
</template>

<style scoped>

form {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
}

.wrapper {
  display: flex;
  flex-direction: column;
  padding: 32px 32px;
  background-color: var(--button);
  border-radius: 10px;
  width: 30%;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-bottom: 20px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: var(--button-important);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: var(--button-important-hover);
}

a {
  margin-top: 8px;
  color: var(--light-primary);
  text-decoration: underline;
}
</style>