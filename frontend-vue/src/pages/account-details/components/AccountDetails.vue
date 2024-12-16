<script setup lang="ts">

import {inject, ref} from "vue";

const sessionService: any = inject("sessionService")
const selectedSubject = ref("email")

</script>

<template>
  <section class="popup">
    <div class="popup-wrapper">
      <div class="popup-container">
        <p>Huidige {{selectedSubject}} : mathijs.langhorst@hva.nl  </p>
      </div>
    </div>
  </section>
  <section class="account-details">
    <div class="wrapper">
      <h1>Account details</h1>
      <div class="container">
        <div class="box">
          email:
          <div class="box-button">
            <button>wijzig</button>
          </div>
        </div>
        <div class="box">
          gebruikersnaam:
          <div class="box-button">
            <button>wijzig</button>
          </div>
        </div>
        <div class="box">
          wachtwoord:
          <div class="box-button">
            <button>wijzig</button>
          </div>
        </div>
        <div class="box">
          geboorte datum:
          <div class="box-button">
            <button>wijzig</button>
          </div>
        </div>
        <div class="box">
          <div class="delete-container">
            <button class="delete-btn">verwijder huidig account</button>
          </div>
        </div>
      </div>


    </div>
  </section>


</template>

<style scoped>
.box-button {
  margin-left: auto;
}

.delete-btn {
  background-color: #E74C3C;
}

.delete-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: auto;
}

.account-details {
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 56px);
  background-color: var(--dark-primary);
  color: var(--light-primary);
}

.wrapper {
  width: 90%;
  max-width: 600px;
  padding: 20px;
  background-color: var(--dark-secondary);
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

h1 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: var(--light-primary);
  border-bottom: 1px solid var(--light-primary);
  padding-bottom: 10px;
}

.container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  background-color: var(--dark-primary);
  border-radius: 5px;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}


button {
  padding: 5px 10px;
  font-size: 14px;
  background-color: var(--light-primary);
  color: var(--dark-secondary);
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  border: 1px solid;

}

button:hover {
  background-color: var(--dark-primary);
  color: var(--light-primary);
  border: 1px solid var(--light-primary);
}

.delete-btn {
  text-align: center;
}

.delete-btn {
  width: 100%;
  background-color: #ff4d4d;
  color: white;
  border: none;
}

.delete-btn:hover {
  background-color: #ff1a1a;
  color: white;
  border: none;
}

</style>