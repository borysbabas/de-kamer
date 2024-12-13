<script setup lang="ts">
import {ref} from "vue"
import {inject} from "vue";

const sessionService = inject("sessionService")

async function submitRegisterForm(){
  const response = await sessionService.asyncSignUp(formData.value)
  console.log(response)
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

// TODO: Add validation for email, username, dateOfBirth, and password
// TODO: Let the user know which field failed validation
</script>

<template>
  <section class="register">
    <form @submit.prevent>
      <div class="wrapper">
        <h2>Enter your information</h2>
        <input v-model="formData.email" placeholder="E-mail">
        <input v-model="formData.username" placeholder="Username">
        <input v-model="formData.dateOfBirth" type="date" placeholder="date of birth.">
        <input v-model="formData.password" type="password" placeholder="Password">
        <button id="submit-btn" type="submit" @click="submitRegisterForm">Submit</button>
        <router-link to="/forum/login"><a>Login</a></router-link>
      </div>
    </form>
  </section>
</template>

<style scoped>
.register {
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