<script setup lang="ts">
import { ref} from "vue"
const loginCredentials = ref({
  email: "",
  password: "",
})

/**
 * Injects the email into the navbar
 * @param email The email to inject
 */
const submitLogin = async () => {
  const response = await fetch("http://localhost:3001/login", {
    method: "POST",
    headers: {"Content-type": "application/json"},
    body: JSON.stringify(loginCredentials.value),
  })
  if (response.ok) {
    const result = await response.json()
    console.log("Succesvol POST request.", result)
    injectEmail(result.email.value)
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
        <button @click="submitLogin">Log in</button>
        <router-link to="/register"><a>Create account</a></router-link>
      </div>
    </form>
  </section>
</template>

<style scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 56px);
  background-color: var(--dark-primary);
}

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